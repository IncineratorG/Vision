package com.vision.common.services.firebase_messaging;


import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
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

    public void subscribeToGlobalNotificationTopic(String groupName,
                                                   String groupPassword,
                                                   OnTaskSuccess<Void> onSuccess,
                                                   OnTaskError<ServiceError> onError) {
        String globalTopic = generateGlobalTopic(groupName, groupPassword);

        FirebaseMessaging.getInstance().subscribeToTopic(globalTopic)
                .addOnFailureListener((data) -> onSuccess.onSuccess(null))
                .addOnFailureListener((error) -> onError.onError(FBSMessagingServiceErrors.firebaseFailure()));
    }

    public void unsubscribeFromGlobalNotificationTopic(String groupName,
                                                       String groupPassword,
                                                       OnTaskSuccess<Void> onSuccess,
                                                       OnTaskError<ServiceError> onError) {
        String globalTopic = generateGlobalTopic(groupName, groupPassword);

        FirebaseMessaging.getInstance().unsubscribeFromTopic(globalTopic)
                .addOnFailureListener((data) -> onSuccess.onSuccess(null))
                .addOnFailureListener((error) -> onError.onError(FBSMessagingServiceErrors.firebaseFailure()));
    }

    private String generateGlobalTopic(String groupName, String groupPassword) {
        return groupName + DELIMITER + groupPassword;
    }
}
