package com.vision.modules.surveillance.module_actions_executor.handlers;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.vision.MainActivity;
import com.vision.R;
import com.vision.android_services.firebase_messaging.MySingleton;
import com.vision.common.constants.AppConstants;
import com.vision.modules.modules_common.interfaces.js_action_handler.JSActionHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GetAppPermissionsHandler implements JSActionHandler {
    private int REQUEST_PERMISSIONS_CODE = 111;

    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = AppConstants.FIREBASE_SERVER_KEY;
    final private String contentType = "application/json";

    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;

    public GetAppPermissionsHandler() {

    }

    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        Log.d("tag", "GetAppPermissionsHandler->handle(): " + AppConstants.FIREBASE_TOKEN);

        // ===
        String topic = "MyTopic";

        FirebaseMessaging.getInstance().subscribeToTopic(topic).
                addOnSuccessListener(unused -> {
                    Log.d("tag", "SUBSCRIBED_TO_TOPIC");

                    RequestQueue mRequestQue = Volley.newRequestQueue(context);

                    JSONObject json = new JSONObject();
                    try {
                        json.put("to", "/topics/" + topic);

//                        JSONObject notificationObj = new JSONObject();
//                        notificationObj.put("title", "new Order");
//                        notificationObj.put("message", "My Message");
//                        notificationObj.put("body", "This is body");
//                        json.put("notification", notificationObj);

                        JSONObject data = new JSONObject();
                        data.put("title", "Data Title");
                        data.put("message", "Data Message");
                        data.put("Custom Id", "My Custom Id");
                        json.put("data", data);

                        String URL = "https://fcm.googleapis.com/fcm/send";

                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                                Request.Method.POST,
                                URL,
                                json,
                                response -> Log.d("tag", "onResponse: "),
                                error -> Log.d("tag", "onError: " + error.networkResponse)
                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> header = new HashMap<>();
                                header.put("content-type", "application/json");
                                header.put("authorization", "key=" + serverKey);
                                return header;
                            }
                        };

                        mRequestQue.add(jsonObjectRequest);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d("tag", "SUBSCRIBE_TO_TOPIC_ERROR: " + e.toString());
                });
        // ===

//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(task -> {
//                    if (!task.isSuccessful()) {
//                        Log.w("tag", "Fetching FCM registration token failed", task.getException());
//                        return;
//                    }
//
//                    // Get new FCM registration token
//                    String token = task.getResult();
//
//                    // Log and toast
//                    String msg = "This is message: " + token;
//                    Log.d("tag", msg);
//                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
//                });

        // ===
        // =====
//        TOPIC = "/topics/userABC";
//        NOTIFICATION_TITLE = "NotificationTitle";
//        NOTIFICATION_MESSAGE = "NotificationMessage";
//
//        JSONObject notification = new JSONObject();
//        JSONObject notifcationBody = new JSONObject();
//        try {
//            notifcationBody.put("title", NOTIFICATION_TITLE);
//            notifcationBody.put("message", NOTIFICATION_MESSAGE);
//            notification.put("to", TOPIC);
//            notification.put("data", notifcationBody);
//        } catch (JSONException e) {
//            Log.e("tag", "GetAppPermissionsHandler->handle(): " + e.getMessage() );
//        }
//
//        sendNotification(context, notification);
        // =====
        // ===

        result.resolve(true);
    }

    private void sendNotification(Context context, JSONObject notification) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                (Response.Listener) response -> Log.i("tag", "onResponse: " + response.toString()),
                (Response.ErrorListener) error -> {
                    Toast.makeText(context, "Request error", Toast.LENGTH_LONG).show();
                    Log.i("tag", "onErrorResponse: Didn't work: " + error.toString());
                    error.printStackTrace();
                }){
            @Override
            public Map getHeaders() {
                Map params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}

//package com.vision.modules.surveillance.module_actions_executor.handlers;
//
//
//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.util.Log;
//
//import com.facebook.react.bridge.Promise;
//import com.facebook.react.bridge.ReactApplicationContext;
//import com.facebook.react.bridge.ReadableMap;
//import com.facebook.react.modules.core.PermissionAwareActivity;
//import com.vision.modules.modules_common.interfaces.js_action_handler.JSActionHandler;
//
//public class GetAppPermissionsHandler implements JSActionHandler {
//    private int REQUEST_PERMISSIONS_CODE = 111;
//
//    public GetAppPermissionsHandler() {
//
//    }
//
//    @Override
//    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
//        Log.d("tag", "GetAppPermissionsHandler->handle()");
//
//        PermissionAwareActivity activity = (PermissionAwareActivity) context.getCurrentActivity();
//        if (activity == null) {
//            Log.d("tag", "GetAppPermissionsHandler->handle(): ACTIVITY_IS_NULL");
//            result.resolve(true);
//            return;
//        }
//
//        try {
//            activity.requestPermissions(
//                    new String[]{
//                            Manifest.permission.CAMERA,
//                            Manifest.permission.WAKE_LOCK,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                            Manifest.permission.READ_EXTERNAL_STORAGE,
//                    },
//                    REQUEST_PERMISSIONS_CODE,
//                    (requestCode, permissions, grantResults) -> {
//                        if (requestCode == REQUEST_PERMISSIONS_CODE) {
//                            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                                Log.d("tag", "GetAppPermissionsHandler->handle()->PERMISSIONS_GRANTED");
//                                result.resolve(true);
//                            } else {
//                                Log.d("tag", "GetAppPermissionsHandler->handle()->PERMISSIONS_NOT_GRANTED");
//                                result.resolve(false);
//                            }
//                        } else {
//                            Log.d("tag", "GetAppPermissionsHandler->handle()->UNKNOWN_REQUEST_CODE: " + requestCode);
//                            result.resolve(false);
//                        }
//
//                        return true;
//                    }
//            );
//        } catch (NoSuchMethodError e) {
//            Log.d("tag", "GetAppPermissionsHandler->handle(): NO_SUCH_METHOD_ERROR");
//            result.resolve(true);
//            return;
//        }
//    }
//}