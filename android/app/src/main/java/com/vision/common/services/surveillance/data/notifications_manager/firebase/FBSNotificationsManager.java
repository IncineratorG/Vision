package com.vision.common.services.surveillance.data.notifications_manager.firebase;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.RemoteMessage;
import com.vision.MainActivity;
import com.vision.R;
import com.vision.common.constants.AppConstants;
import com.vision.common.data.hybrid_objects.app_settings.AppSettings;
import com.vision.common.data.service_notification.ServiceNotification;
import com.vision.common.interfaces.service_notification_handler.ServiceNotificationHandler;
import com.vision.common.interfaces.service_notifications_manager.ServiceNotificationsManager;
import com.vision.common.services.app_settings.AppSettingsService;
import com.vision.common.services.firebase_messaging.FBSMessagingService;
import com.vision.common.services.surveillance.SurveillanceService;
import com.vision.common.services.surveillance.data.notifications.handlers.device_movement_start.DeviceMovementStartNotificationHandler;
import com.vision.common.services.surveillance.data.notifications.handlers.device_movement_stop.DeviceMovementStopNotificationHandler;
import com.vision.common.services.surveillance.data.notifications.handlers.test_notification.TestNotificationHandler;
import com.vision.common.services.surveillance.data.notifications.handlers.unknown_notification.UnknownNotificationHandler;
import com.vision.common.services.surveillance.data.notifications.types.SurveillanceServiceNotificationTypes;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FBSNotificationsManager implements ServiceNotificationsManager {
    public static final String CHANNEL_ID = "MyFirebaseMessagingServiceChannel";
    private final String UNKNOWN_NOTIFICATION_HANDLER_KEY = "unknown";
    private final String JSON_NOTIFICATION_OBJECT_FIELD = "notification";
    private final String URL = "https://fcm.googleapis.com/fcm/send";
    private final int METHOD = Request.Method.POST;
    private final String CONTENT_TYPE = "application/json";
    private final String FIREBASE_SERVER_KEY = "AAAANMMmzyI:APA91bGYvLIW2NzCYruRbejEwT5IzQe5n6bLXUSwTz42qu5lRiqu71zPDIw129OiNuG1knwMLyGsMQpNaeWGRihkSXKw6iJMBWcasJ8ark1K5oCmRvgxIir1PCPcp_fpfPueU6zmcQKZ";

    private Map<String, ServiceNotificationHandler> mHandlers;

    public FBSNotificationsManager() {
        mHandlers = new HashMap<>();
        mHandlers.put(UNKNOWN_NOTIFICATION_HANDLER_KEY, new UnknownNotificationHandler());
        mHandlers.put(SurveillanceServiceNotificationTypes.TEST_NOTIFICATION, new TestNotificationHandler());
        mHandlers.put(SurveillanceServiceNotificationTypes.DEVICE_MOVEMENT_START, new DeviceMovementStartNotificationHandler());
        mHandlers.put(SurveillanceServiceNotificationTypes.DEVICE_MOVEMENT_STOP, new DeviceMovementStopNotificationHandler());
    }

    @Override
    public void sendNotificationToAll(Context context, ServiceNotification notification) {
        Log.d("tag", "FBSNotificationsManager->sendNotificationToAll()");

        String currentGroupName = SurveillanceService.get().currentGroupName();
        String currentGroupPassword = SurveillanceService.get().currentGroupPassword();

        String globalTopic = FBSMessagingService.get().globalTopic(currentGroupName, currentGroupPassword);

        JSONObject notificationJson = new JSONObject();
        try {
            notificationJson.put("to", "/topics/" + globalTopic);

            JSONObject data = new JSONObject();
            data.put(JSON_NOTIFICATION_OBJECT_FIELD, notification.stringify());
            notificationJson.put("data", data);

            JSONObject androidPriority = new JSONObject();
            androidPriority.put("priority", "high");
            notificationJson.put("android", androidPriority);

            JsonObjectRequest request = new JsonObjectRequest(
                    METHOD,
                    URL,
                    notificationJson,
                    response -> {
                        Log.d("tag", "FBSNotificationsManager->sendNotificationToAll()->onResponse");
                    },
                    error -> {
                        Log.d("tag", "FBSNotificationsManager->sendNotificationToAll()->onError: " + error.networkResponse);
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> requestHeaders = new HashMap<>();
                    requestHeaders.put("content-type", CONTENT_TYPE);
                    requestHeaders.put("authorization", "key=" + FIREBASE_SERVER_KEY);

                    return requestHeaders;
                }
            };

            Volley.newRequestQueue(context).add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute(Context context, RemoteMessage notificationMessage, Map<String, Object> params) {
        Log.d("tag", "FBSNotificationsManager->execute()");

        String stringifiedNotification = notificationMessage.getData().get(JSON_NOTIFICATION_OBJECT_FIELD);

        ServiceNotification notification = new ServiceNotification(stringifiedNotification);
        if (notification.empty()) {
            Log.d("tag", "FBSNotificationsManager->execute(): NOTIFICATION_IS_EMPTY");
            return;
        }

        if (needHandleNotification(context, notification)) {
            handleNotification(context, notification);
            showSystemNotification(context, notification);
        }
    }

    private boolean needHandleNotification(Context context, ServiceNotification notification) {
        String currentDeviceMode = SurveillanceService.get().currentServiceMode();
        String currentGroupName = SurveillanceService.get().currentGroupName();
        String currentDeviceName = SurveillanceService.get().currentDeviceName();

        String notificationGroupName = notification.senderGroupName();
        String notificationDeviceName = notification.senderDeviceName();

        if (currentGroupName != null
                && currentDeviceName != null
                && currentGroupName.equals(notificationGroupName)
                && currentDeviceName.equals(notificationDeviceName)) {
            Log.d("tag", "FBSNotificationsManager->needHandleNotification(): NOTIFICATION_SENT_FROM_CURRENT_DEVICE->WILL_NOT_PROCESS");
            return false;
        }

        if (currentDeviceMode.equals(AppConstants.DEVICE_MODE_SERVICE)) {
            Log.d("tag", "FBSNotificationsManager->needHandleNotification(): CURRENT_DEVICE_IN_SERVICE_MODE->WILL_NOT_PROCESS");
            return false;
        }

        AppSettings settings = AppSettingsService.get().getAppSettingsForGroup(context, notificationGroupName);
        if (!settings.receiveNotificationsFromCurrentGroup()) {
            Log.d("tag", "FBSNotificationsManager->needHandleNotification(): SETTINGS_PREVENT_RECEIVING_NOTIFICATIONS->WILL_NOT_PROCESS");
            return false;
        }

        return true;
    }

    private void handleNotification(Context context, ServiceNotification notification) {
        if (mHandlers.containsKey(notification.type())) {
            ServiceNotificationHandler handler = mHandlers.get(notification.type());
            if (handler != null) {
                handler.handle(context, notification);
            } else {
                Log.d("tag", "FBSNotificationsManager->handleNotification(): NOTIFICATION_HANDLER_IS_NULL");
            }
        } else {
            ServiceNotificationHandler handler = mHandlers.get(UNKNOWN_NOTIFICATION_HANDLER_KEY);
            if (handler != null) {
                handler.handle(context, notification);
            } else {
                Log.d("tag", "FBSNotificationsManager->handleNotification(): UNKNOWN_NOTIFICATION_HANDLER_IS_NULL");
            }
        }
    }

    private void showSystemNotification(Context context, ServiceNotification notification) {
        final Intent intent = new Intent(context, MainActivity.class);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationID = new Random().nextInt(3000);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            setupChannels(notificationManager);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.common_google_signin_btn_icon_dark_normal);
        Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark_normal)
                .setLargeIcon(largeIcon)
                .setContentTitle(notificationTitle(notification))
                .setContentText(notificationMessage(notification))
                .setAutoCancel(true)
                .setSound(notificationSoundUri)
                .setContentIntent(pendingIntent);

        // Set notification color to match your app color template
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setColor(context.getResources().getColor(R.color.catalyst_redbox_background));
        }
        notificationManager.notify(notificationID, notificationBuilder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels(NotificationManager notificationManager) {
        CharSequence adminChannelName = "New notification";
        String adminChannelDescription = "Device to device notification ";

        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(
                CHANNEL_ID,
                adminChannelName,
                NotificationManager.IMPORTANCE_HIGH
        );

        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.RED);
        adminChannel.enableVibration(true);

        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }

    private String notificationTitle(ServiceNotification notification) {
        String notificationGroupName = notification.senderGroupName();
        String notificationDeviceName = notification.senderDeviceName();

        return notificationGroupName + "." + notificationDeviceName;
    }

    private String notificationMessage(ServiceNotification notification) {
        switch (notification.type()) {
            case (SurveillanceServiceNotificationTypes.TEST_NOTIFICATION): {
                return "Тестовое уведомление";
            }

            default: {
                return "Неизвестное уведомление: " + notification.type();
            }
        }
    }
}
