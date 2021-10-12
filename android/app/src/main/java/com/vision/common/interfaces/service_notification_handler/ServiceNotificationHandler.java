package com.vision.common.interfaces.service_notification_handler;


import android.app.Notification;
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

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.vision.MainActivity;
import com.vision.R;
import com.vision.common.data.service_notification.ServiceNotification;

import java.util.Random;

public abstract class ServiceNotificationHandler {
    public static final String NOTIFICATION_CHANNEL_ID = "MyFirebaseMessagingServiceChannel";

    public void handle(Context context, ServiceNotification notification) {
        handleServiceNotification(context, notification);

        Notification systemNotification = createSystemNotification(context, notification);
        if (systemNotification!= null) {
            showSystemNotification(context, systemNotification);
            return;
        }

        String systemNotificationTitle = systemNotificationTitle(context, notification);
        String systemNotificationMessage = systemNotificationMessage(context, notification);
        if (systemNotificationTitle != null && systemNotificationMessage != null) {
            createAndShowSystemNotification(context, systemNotificationTitle, systemNotificationMessage);
        }
    }

    protected abstract void handleServiceNotification(Context context, ServiceNotification notification);
    protected abstract Notification createSystemNotification(Context context, ServiceNotification notification);
    protected abstract String systemNotificationTitle(Context context, ServiceNotification notification);
    protected abstract String systemNotificationMessage(Context context, ServiceNotification notification);

    private void showSystemNotification(Context context, Notification systemNotification) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationID = new Random().nextInt(3000);

        notificationManager.notify(notificationID, systemNotification);
    }

    private void createAndShowSystemNotification(Context context,
                                                 String notificationTitle,
                                                 String notificationMessage) {
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

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark_normal)
                .setLargeIcon(largeIcon)
                .setContentTitle(notificationTitle)
                .setContentText(notificationMessage)
                .setAutoCancel(true)
                .setSound(notificationSoundUri)
                .setContentIntent(pendingIntent);

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
                NOTIFICATION_CHANNEL_ID,
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
}
