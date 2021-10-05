package com.vision.android_services.firebase_messaging;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.vision.common.constants.AppConstants;
import com.vision.common.services.surveillance.SurveillanceService;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String token) {
        Log.d("tag", "MyFirebaseMessagingService->onNewToken(): " + token);
        AppConstants.FIREBASE_TOKEN = token;
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Log.d("tag", "MyFirebaseMessagingService->onMessageReceived()");
        SurveillanceService.get().notificationsExecutor().execute(this, remoteMessage, null);
    }
}
