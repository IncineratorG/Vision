package com.vision.common.services.surveillance.data.communication_manager.firebase;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.vision.common.data.service_request.ServiceRequest;
import com.vision.common.data.service_response.ServiceResponse;
import com.vision.common.interfaces.service_communication_manager.ServiceCommunicationManager;
import com.vision.common.interfaces.service_request_sender.ServiceRequestSender;
import com.vision.common.interfaces.service_request_sender.callbacks.OnDeliveredCallback;
import com.vision.common.interfaces.service_request_sender.callbacks.OnErrorCallback;
import com.vision.common.interfaces.service_request_sender.callbacks.OnResponseCallback;
import com.vision.common.interfaces.service_requests_handler.ServiceRequestsHandler;
import com.vision.common.interfaces.service_response_sender.ServiceResponseSender;
import com.vision.common.interfaces.service_responses_handler.ServiceResponsesHandler;
import com.vision.common.services.firebase.FBSService;
import com.vision.common.services.firebase.data.FBSListenerId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FBSCommunicationManager implements ServiceCommunicationManager {
    private FBSListenerId mRequestListenerId;
    private FBSListenerId mResponseListenerId;
    private List<String> mRequestsPath;
    private List<String> mResponsesPath;
    private ServiceRequestsHandler mRequestsHandler;
    private ServiceResponsesHandler mResponsesHandler;
    private ServiceRequestSender mRequestSender;
    private ServiceResponseSender mResponseSender;

    public FBSCommunicationManager(ServiceRequestsHandler requestsHandler,
                                   ServiceResponsesHandler responsesHandler,
                                   ServiceRequestSender requestSender,
                                   ServiceResponseSender responseSender,
                                   List<String> requestsPath,
                                   List<String> responsesPath) {
        mRequestsHandler = requestsHandler;
        mResponsesHandler = responsesHandler;
        mRequestSender = requestSender;
        mResponseSender = responseSender;
        mRequestsPath = requestsPath;
        mResponsesPath = responsesPath;
    }

    @Override
    public void startRequestsListener(Context context) {
        FBSService.get().setStringValue(mRequestsPath, null);

        ChildEventListener listener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String key = snapshot.getKey();
                String value = snapshot.getValue(String.class);

                if (key == null) {
                    Log.d("tag", "FBSForegroundServiceWork->KEY_IS_NULL");
                    return;
                }

                if (value == null) {
                    Log.d("tag", "FBSForegroundServiceWork->VALUE_IS_NULL");
                    return;
                }

                Map<String, Object> requestParams = new HashMap<>();
                requestParams.put("requestKey", key);

                mRequestsHandler.handle(context, value, requestParams);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        if (mRequestListenerId != null) {
            FBSService.get().removeListener(mRequestListenerId);
        }

        mRequestListenerId = FBSService.get().addListener(mRequestsPath, listener);
    }

    @Override
    public void stopRequestsListener(Context context) {
        if (mRequestListenerId != null) {
            FBSService.get().removeListener(mRequestListenerId);
            mRequestListenerId = null;
        }
    }

    @Override
    public void startResponsesListener(Context context) {

    }

    @Override
    public void stopResponsesListener(Context context) {

    }

    @Override
    public void sendRequest(String groupName,
                            String groupPassword,
                            String receiverDeviceName,
                            ServiceRequest request,
                            OnDeliveredCallback onDeliveredCallback,
                            OnResponseCallback onResponseCallback,
                            OnErrorCallback onErrorCallback) {
        mRequestSender.sendRequest(
                groupName,
                groupPassword,
                receiverDeviceName,
                request,
                onDeliveredCallback,
                onResponseCallback,
                onErrorCallback
        );
    }

    @Override
    public void sendResponse(String groupName,
                             String groupPassword,
                             String receiverDeviceName,
                             ServiceResponse response) {
        mResponseSender.sendResponse(groupName, groupPassword, receiverDeviceName, response);
    }
}
