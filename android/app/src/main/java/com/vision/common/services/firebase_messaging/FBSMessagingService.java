package com.vision.common.services.firebase_messaging;


import com.google.firebase.messaging.FirebaseMessaging;
import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.common.services.firebase_messaging.data.FBSMessagingServiceErrors;

public class FBSMessagingService {
    private static FBSMessagingService sInstance;

    private final String DELIMITER = "/";

    public FBSMessagingService() {

    }

    public static FBSMessagingService get() {
        if (sInstance == null) {
            sInstance = new FBSMessagingService();
        }

        return sInstance;
    }

    public void subscribeToTopic(String topic,
                                 OnTaskSuccess<Void> onSuccess,
                                 OnTaskError<ServiceError> onError) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnFailureListener((data) -> onSuccess.onSuccess(null))
                .addOnFailureListener((error) -> onError.onError(FBSMessagingServiceErrors.firebaseFailure()));
    }

    public void unsubscribeFromTopic(String topic,
                                     OnTaskSuccess<Void> onSuccess,
                                     OnTaskError<ServiceError> onError) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
                .addOnFailureListener((data) -> onSuccess.onSuccess(null))
                .addOnFailureListener((error) -> onError.onError(FBSMessagingServiceErrors.firebaseFailure()));
    }

    public String globalTopic(String groupName, String groupPassword) {
        return groupName + DELIMITER + groupPassword;
    }
}
