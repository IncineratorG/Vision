package com.vision.common.services.surveillance.data.communication_manager.firebase;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.vision.common.constants.AppConstants;
import com.vision.common.data.service_request.ServiceRequest;
import com.vision.common.data.service_request_callbacks.ServiceRequestCallbacks;
import com.vision.common.data.service_response.ServiceResponse;
import com.vision.common.interfaces.service_communication_manager.ServiceCommunicationManager;
import com.vision.common.interfaces.service_request_sender.ServiceRequestSender;
import com.vision.common.interfaces.service_request_sender.callbacks.OnDeliveredCallback;
import com.vision.common.interfaces.service_request_sender.callbacks.OnErrorCallback;
import com.vision.common.interfaces.service_request_sender.callbacks.OnResponseCallback;
import com.vision.common.interfaces.service_requests_handler.ServiceRequestsExecutor;
import com.vision.common.interfaces.service_response_sender.ServiceResponseSender;
import com.vision.common.interfaces.service_responses_handler.ServiceResponsesExecutor;
import com.vision.common.services.firebase.FBSService;
import com.vision.common.services.firebase.data.FBSListenerId;
import com.vision.common.services.surveillance.data.service_errors.SurveillanceServiceErrors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FBSCommunicationManager implements ServiceCommunicationManager {
    private FBSListenerId mRequestListenerId;
    private FBSListenerId mResponseListenerId;
    private List<String> mRequestsPath;
    private List<String> mResponsesPath;
    private List<String> mUpdateInfoPath;
    private ServiceRequestsExecutor mRequestsExecutor;
    private ServiceResponsesExecutor mResponsesExecutor;
    private ServiceRequestSender mRequestSender;
    private ServiceResponseSender mResponseSender;
    private Map<String, ServiceRequestCallbacks> mRequestCallbacksMap;
    private Map<String, Timer> mRequestTimeoutsMap;

    private Timer mIsAliveSignalingTimer;
    private Thread mIsAliveSignalingThread;

    private ScheduledThreadPoolExecutor mIsAliveSignalingExecutor;

    public FBSCommunicationManager(ServiceRequestsExecutor requestsExecutor,
                                   ServiceResponsesExecutor responsesExecutor,
                                   ServiceRequestSender requestSender,
                                   ServiceResponseSender responseSender,
                                   List<String> requestsPath,
                                   List<String> responsesPath,
                                   List<String> updateInfoPath) {
        mRequestsExecutor = requestsExecutor;
        mResponsesExecutor = responsesExecutor;
        mRequestSender = requestSender;
        mResponseSender = responseSender;
        mRequestsPath = requestsPath;
        mResponsesPath = responsesPath;
        mUpdateInfoPath = updateInfoPath;

        mRequestCallbacksMap = new ConcurrentHashMap<>();
        mRequestTimeoutsMap = new ConcurrentHashMap<>();

        mIsAliveSignalingExecutor = new ScheduledThreadPoolExecutor(1);
    }

    @Override
    public void startIsAliveSignaling(Context context) {
//        mIsAliveSignalingExecutor.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                Log.d("tag", "IS_ALIVE_TASK_RUNNING");
//            }
//        }, 0, 2000, TimeUnit.MILLISECONDS);

        // ======================================
        // ======================================

//        mIsAliveSignalingThread = new Thread(() -> {
//            while (!Thread.currentThread().isInterrupted()) {
//                Log.d("tag", "IS_ALIVE_THREAD_RUNNING");
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                    break;
//                }
//            }
//            Log.d("tag", "THREAD_INTERRUPTED");
//
////            Log.d("tag", "IS_ALIVE_THREAD_RUNNING");
////            try {
////                Thread.sleep(1000);
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
//        });
//        mIsAliveSignalingThread.setPriority(Thread.MAX_PRIORITY);
//        mIsAliveSignalingThread.start();

        // ======================================
        // ======================================

        mIsAliveSignalingTimer = new Timer();
        mIsAliveSignalingTimer.schedule(new TimerTask() {
            @Override
            public void run() {
//                Log.d("tag", "IS_ALIVE_TIMER_RUNNING");
                setServiceAliveStatus();
            }
        }, 1000, AppConstants.IS_ALIVE_SIGNALING_PERIOD /*2000*/);
    }

    @Override
    public void stopIsAliveSignaling(Context context) {
//        mIsAliveSignalingExecutor.shutdown();

        // ======================================
        // ======================================

//        if (mIsAliveSignalingThread != null) {
//            mIsAliveSignalingThread.interrupt();
//            try {
//                mIsAliveSignalingThread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            mIsAliveSignalingThread = null;
//        }

        // ======================================
        // ======================================

        if (mIsAliveSignalingTimer != null) {
            mIsAliveSignalingTimer.cancel();
            mIsAliveSignalingTimer = null;
        }
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
                    Log.d("tag", "FBSCommunicationManager->startRequestsListener()->KEY_IS_NULL");
                    return;
                }

                if (value == null) {
                    Log.d("tag", "FBSCommunicationManager->startRequestsListener()->VALUE_IS_NULL");
                    return;
                }

                Map<String, Object> requestParams = new HashMap<>();
                requestParams.put("requestKey", key);

                mRequestsExecutor.execute(context, value, requestParams);
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
        FBSService.get().setStringValue(mResponsesPath, null);

        ChildEventListener listener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String key = snapshot.getKey();
                String value = snapshot.getValue(String.class);

                if (key == null) {
                    Log.d("tag", "FBSCommunicationManager->startResponsesListener()->KEY_IS_NULL");
                    return;
                }

                if (value == null) {
                    Log.d("tag", "FBSCommunicationManager->startResponsesListener()->VALUE_IS_NULL");
                    return;
                }

                Map<String, Object> responseParams = new HashMap<>();
                responseParams.put("responseKey", key);

                mResponsesExecutor.execute(
                        context,
                        value,
                        mRequestCallbacksMap,
                        mRequestTimeoutsMap,
                        responseParams
                );
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

        if (mResponseListenerId != null) {
            FBSService.get().removeListener(mResponseListenerId);
        }

        mResponseListenerId = FBSService.get().addListener(mResponsesPath, listener);
    }

    @Override
    public void stopResponsesListener(Context context) {
        if (mResponseListenerId != null) {
            FBSService.get().removeListener(mResponseListenerId);
            mResponseListenerId = null;
        }
    }

    @Override
    public void sendRequest(String groupName,
                            String groupPassword,
                            String receiverDeviceName,
                            ServiceRequest request,
                            OnDeliveredCallback onDeliveredCallback,
                            OnResponseCallback onResponseCallback,
                            OnErrorCallback onErrorCallback) {
        mRequestCallbacksMap.put(
                request.id(),
                new ServiceRequestCallbacks(
                        onDeliveredCallback,
                        onResponseCallback,
                        onErrorCallback
                )
        );

        Timer requestTimeout = new Timer();
        requestTimeout.schedule(new TimerTask() {
            @Override
            public void run() {
                ServiceRequestCallbacks callbacks = mRequestCallbacksMap.get(request.id());
                if (callbacks != null) {
                    callbacks.errorCallback().handle(
                            SurveillanceServiceErrors.requestTimeout()
                    );
                }

                mRequestCallbacksMap.remove(request.id());
                mRequestTimeoutsMap.remove(request.id());
            }
        }, AppConstants.REQUEST_TIMEOUT_PERIOD);

        mRequestTimeoutsMap.put(
                request.id(),
                requestTimeout
        );

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
    public boolean cancelRequest(String requestId) {
        mRequestCallbacksMap.remove(requestId);

        Timer requestTimeout = mRequestTimeoutsMap.get(requestId);
        if (requestTimeout != null) {
            requestTimeout.cancel();
        }
        mRequestTimeoutsMap.remove(requestId);

        return true;
    }

    @Override
    public void sendResponse(String groupName,
                             String groupPassword,
                             String receiverDeviceName,
                             ServiceResponse response) {
        mResponseSender.sendResponse(groupName, groupPassword, receiverDeviceName, response);
    }

    private void setServiceAliveStatus() {
        FBSService.get().setStringValue(
                mUpdateInfoPath,
                String.valueOf(System.currentTimeMillis())
        );
    }
}
