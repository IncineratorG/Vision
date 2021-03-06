package com.vision.services.surveillance.responses.executor.firebase;


import android.content.Context;
import android.util.Log;

import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_request_callbacks.ServiceRequestCallbacks;
import com.vision.common.data.service_response.ServiceResponse;
import com.vision.common.interfaces.service_request_sender.callbacks.OnRequestDeliveredCallback;
import com.vision.common.interfaces.service_request_sender.callbacks.OnRequestErrorCallback;
import com.vision.common.interfaces.service_request_sender.callbacks.OnRequestResponseCallback;
import com.vision.common.interfaces.service_responses_executor.ServiceResponsesExecutor;
import com.vision.services.firebase_communication.FBSCommunicationService;
import com.vision.services.firebase_paths.FBSPathsService;
import com.vision.services.surveillance.SurveillanceService;
import com.vision.services.surveillance.responses.payloads.SurveillanceServiceResponsePayloads;
import com.vision.services.surveillance.responses.payloads.payloads.ErrorResponsePayload;

import java.util.List;
import java.util.Map;
import java.util.Timer;

public class FBSResponsesExecutor implements ServiceResponsesExecutor {
    public FBSResponsesExecutor() {

    }

    @Override
    public void execute(Context context,
                        String stringifiedResponse,
                        Map<String, ServiceRequestCallbacks> requestCallbacksMap,
                        Map<String, Timer> requestTimeoutsMap,
                        Map<String, Object> params) {
        Log.d("tag", "FBSResponsesExecutor->execute(): " + stringifiedResponse + " - " + (context == null));

        if (!isSafeToProcessResponse(
                context,
                stringifiedResponse,
                requestCallbacksMap,
                requestTimeoutsMap,
                params)
        ) {
            Log.d("tag", "FBSResponsesExecutor->execute(): WILL_NOT_PROCESS_RESPONSE");
            return;
        }

        ServiceResponse response = getResponseFromString(stringifiedResponse, params);
        handleResponse(response, requestCallbacksMap, requestTimeoutsMap);
    }

    private boolean isSafeToProcessResponse(Context context,
                                            String stringifiedResponse,
                                            Map<String, ServiceRequestCallbacks> requestCallbacksMap,
                                            Map<String, Timer> requestTimeoutsMap,
                                            Map<String, Object> params) {
        if (context == null) {
            Log.d("tag", "FBSResponsesExecutor->isSafeToProcessResponse(): CONTEXT_IS_NULL");
            return false;
        }

        if (stringifiedResponse == null) {
            Log.d("tag", "FBSResponsesExecutor->isSafeToProcessResponse(): STRINGIFIED_RESPONSE_IS_NULL");
            return false;
        }

        if (params == null) {
            Log.d("tag", "FBSResponsesExecutor->isSafeToProcessResponse(): PARAMS_IS_NULL");
            return false;
        }

        if (!params.containsKey("responseKey")) {
            Log.d("tag", "FBSResponsesExecutor->isSafeToProcessResponse(): PARAMS_HAS_NO_KEY");
        }

        return true;
    }

    private ServiceResponse getResponseFromString(String stringifiedResponse, Map<String, Object> params) {
        ServiceResponse response = new ServiceResponse(stringifiedResponse);

        String responseKey = (String) params.get("responseKey");
        if (responseKey != null) {
            response.setKey(responseKey);
        } else {
            Log.d("tag", "FBSResponsesExecutor->getResponseFromString(): BAD_RESPONSE_KEY");
        }

        return response;
    }

    private void handleResponse(ServiceResponse response,
                                Map<String, ServiceRequestCallbacks> requestCallbacksMap,
                                Map<String, Timer> requestTimeoutsMap) {
        Log.d("tag", "=====> FBSResponsesExecutor->handleResponse(): " + response.type() + " <=======");

        SurveillanceService surveillanceService = SurveillanceService.get();

        String currentGroupName = surveillanceService.currentGroupName();
        String currentGroupPassword = surveillanceService.currentGroupPassword();
        String currentDeviceName = surveillanceService.currentDeviceName();

        List<String> responsesPath = FBSPathsService.get().responsesPath(
                currentGroupName,
                currentGroupPassword,
                currentDeviceName
        );

        String requestId = response.requestId();
        String responseType = response.type();
        if (responseType == null) {
            Log.d("tag", "FBSResponsesExecutor->handleResponse()->RESPONSE_TYPE_IS_NULL");
            responseType = ServiceResponse.TYPE_RESULT;
        }

        ServiceRequestCallbacks requestCallbacks = requestCallbacksMap.get(requestId);

        switch (responseType) {
            case (ServiceResponse.TYPE_RECEIVED): {
                if (requestCallbacks != null) {
                    OnRequestDeliveredCallback deliveredCallback = requestCallbacks.deliveredCallback();
                    if (deliveredCallback != null) {
                        deliveredCallback.handle();
                    } else {
                        Log.d("tag", "FBSResponsesExecutor->handleResponse(): ON_DELIVERED_CALLBACK_IS_NULL");
                    }
                } else {
                    Log.d("tag", "FBSResponsesExecutor->handleResponse(): REQUEST_CALLBACKS_IS_NULL");
                }
                break;
            }

            case (ServiceResponse.TYPE_RESULT): {
                if (requestCallbacks != null) {
                    OnRequestResponseCallback responseCallback = requestCallbacks.responseCallback();
                    if (responseCallback != null) {
                        responseCallback.handle(response);
                    } else {
                        Log.d("tag", "FBSResponsesExecutor->handleResponse(): RESPONSE_CALLBACK_IS_NULL");
                    }
                } else {
                    Log.d("tag", "FBSResponsesExecutor->handleResponse(): REQUEST_CALLBACKS_IS_NULL");
                }
                Timer requestTimeout = requestTimeoutsMap.get(requestId);
                if (requestTimeout != null) {
                    requestTimeout.cancel();
                }
                requestTimeoutsMap.remove(requestId);
                break;
            }

            case (ServiceResponse.TYPE_ERROR): {
                if (requestCallbacks != null) {
                    OnRequestErrorCallback errorCallback = requestCallbacks.errorCallback();
                    if (errorCallback != null) {
                        ErrorResponsePayload errorResponsePayload = SurveillanceServiceResponsePayloads.errorResponsePayload(response.payload());
                        errorCallback.handle(new ServiceError(errorResponsePayload.serviceErrorCode(), errorResponsePayload.serviceErrorMessage()));
                    } else {
                        Log.d("tag", "FBSResponsesExecutor->handleResponse(): RESPONSE_ERROR_CALLBACK_IS_NULL");
                    }
                } else {
                    Log.d("tag", "FBSResponsesExecutor->handleResponse(): REQUEST_CALLBACKS_IS_NULL");
                }
                Timer requestTimeout = requestTimeoutsMap.get(requestId);
                if (requestTimeout != null) {
                    requestTimeout.cancel();
                }
                requestTimeoutsMap.remove(requestId);
                break;
            }

            default: {
                Log.d("tag", "FBSResponsesExecutor->handleResponse()->BAD_RESPONSE_TYPE: " + responseType);
            }
        }

//        Timer requestTimeout = requestTimeoutsMap.get(requestId);
//        if (requestTimeout != null) {
//            requestTimeout.cancel();
//        }
//        requestTimeoutsMap.remove(requestId);

        if (response.key() != null) {
            FBSCommunicationService.get().removeValueFromList(responsesPath, response.key());
        } else {
            Log.d("tag", "FBSResponsesExecutor->handleResponse()->BAD_RESPONSE_KEY: " + response.stringify());
        }
    }
}


//package com.vision.services.surveillance.responses.executor.firebase;
//
//
//import android.content.Context;
//import android.util.Log;
//
//import com.vision.common.data.service_request_callbacks.ServiceRequestCallbacks;
//import com.vision.common.data.service_response.ServiceResponse;
//import com.vision.common.interfaces.service_request_sender.callbacks.OnResponseCallback;
//import com.vision.common.interfaces.service_responses_handler.ServiceResponsesExecutor;
//import com.vision.services.firebase.FBSService;
//import com.vision.services.firebase_paths.FBSPathsService;
//import com.vision.services.surveillance.SurveillanceService;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Timer;
//
//public class FBSResponsesExecutor implements ServiceResponsesExecutor {
//    public FBSResponsesExecutor() {
//
//    }
//
//    @Override
//    public void execute(Context context,
//                        String stringifiedResponse,
//                        Map<String, ServiceRequestCallbacks> requestCallbacksMap,
//                        Map<String, Timer> requestTimeoutsMap,
//                        Map<String, Object> params) {
//        Log.d("tag", "FBSResponsesHandler->handle(): " + stringifiedResponse + " - " + (context == null));
//
//        if (context == null) {
//            Log.d("tag", "FBSResponsesHandler->handle(): CONTEXT_IS_NULL");
//            return;
//        }
//
//        if (stringifiedResponse == null) {
//            Log.d("tag", "FBSResponsesHandler->handle(): STRINGIFIED_RESPONSE_IS_NULL");
//            return;
//        }
//
//        if (params == null) {
//            Log.d("tag", "FBSResponsesHandler->handle(): PARAMS_IS_NULL");
//            return;
//        }
//
//        if (!params.containsKey("responseKey")) {
//            Log.d("tag", "FBSResponsesHandler->handle(): PARAMS_HAS_NO_KEY");
//        }
//
//        ServiceResponse response = new ServiceResponse(stringifiedResponse);
//
//        String responseKey = (String) params.get("responseKey");
//        if (responseKey != null) {
//            response.setKey(responseKey);
//        } else {
//            Log.d("tag", "FBSResponsesHandler->handle(): BAD_RESPONSE_KEY");
//        }
//
//        // ===
//        String requestId = response.requestId();
//
//        ServiceRequestCallbacks requestCallbacks = requestCallbacksMap.get(requestId);
//        if (requestCallbacks != null) {
//            OnResponseCallback responseCallback = requestCallbacks.responseCallback();
//            if (responseCallback != null) {
//                responseCallback.handle(response);
//            } else {
//                Log.d("tag", "FBSResponsesHandler->handle(): RESPONSE_CALLBACK_IS_NULL");
//            }
//        } else {
//            Log.d("tag", "FBSResponsesHandler->handle(): REQUEST_CALLBACKS_IS_NULL");
//        }
//        requestCallbacksMap.remove(requestId);
//
//        Timer requestTimeout = requestTimeoutsMap.get(requestId);
//        if (requestTimeout != null) {
//            requestTimeout.cancel();
//        }
//        requestTimeoutsMap.remove(requestId);
//        // ===
//
//        SurveillanceService surveillanceService = SurveillanceService.get();
//
//        String currentGroupName = surveillanceService.currentGroupName();
//        String currentGroupPassword = surveillanceService.currentGroupPassword();
//        String currentDeviceName = surveillanceService.currentDeviceName();
//
//        List<String> responsesPath = FBSPathsService.get().responsesPath(currentGroupName, currentGroupPassword, currentDeviceName);
//        if (response.key() != null) {
//            FBSService.get().removeValueFromList(responsesPath, response.key());
//        } else {
//            Log.d("tag", "FBSResponsesHandler->handle()->BAD_RESPONSE_KEY: " + response.stringify());
//        }
//    }
//}
