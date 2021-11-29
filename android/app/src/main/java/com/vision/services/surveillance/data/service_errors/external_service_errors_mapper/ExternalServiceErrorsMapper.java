package com.vision.services.surveillance.data.service_errors.external_service_errors_mapper;


import android.util.Log;

import com.vision.common.data.service_error.ServiceError;
import com.vision.services.surveillance.data.service_errors.SurveillanceServiceErrors;

public class ExternalServiceErrorsMapper {
    public static final String AUTH_SERVICE_TYPE = "authService";
    public static final String DEVICE_GROUP_SERVICE_TYPE = "deviceGroupService";
    public static final String FBS_MESSAGING_SERVICE_TYPE = "messagingService";
    public static final String CAMERA_SERVICE_TYPE = "cameraService";

    public ExternalServiceErrorsMapper() {

    }

    public ServiceError mapToSurveillanceServiceError(String serviceType,
                                                      ServiceError externalServiceError) {
        switch (serviceType) {
            case (AUTH_SERVICE_TYPE): {
                return mapFromAuthService(externalServiceError);
            }

            case (DEVICE_GROUP_SERVICE_TYPE): {
                return mapFromDeviceGroupService(externalServiceError);
            }

            case (FBS_MESSAGING_SERVICE_TYPE): {
                return mapFromFbsMessagingService(externalServiceError);
            }

            default: {
                Log.d("tag", "ExternalServiceErrorsMapping->mapToSurveillanceServiceError()->UNKNOWN_SERVICE_TYPE: " + serviceType);
                return SurveillanceServiceErrors.commonServiceError();
            }
        }
    }

    private ServiceError mapFromAuthService(ServiceError error) {
        switch (error.code()) {
            case ("0"): {
                return SurveillanceServiceErrors.firebaseFailure();
            }

            case ("1"): {
                return SurveillanceServiceErrors.groupAlreadyExist();
            }

            case ("2"): {
                return SurveillanceServiceErrors.groupNotExist();
            }

            case ("3"): {
                return SurveillanceServiceErrors.incorrectGroupPassword();
            }

            case ("4"): {
                return SurveillanceServiceErrors.deviceNameAlreadyExist();
            }

            case ("5"): {
                return SurveillanceServiceErrors.deviceNameNotExist();
            }

            case ("6"): {
                return SurveillanceServiceErrors.deviceAlreadyLoggedIn();
            }

            default: {
                Log.d("tag", "ExternalServiceErrorsMapping->mapFromAuthService()->UNKNOWN_ERROR_CODE: " + error.code() + " - " + error.message());
                return SurveillanceServiceErrors.commonServiceError();
            }
        }
    }

    private ServiceError mapFromDeviceGroupService(ServiceError error) {
        switch (error.code()) {
            case ("0"): {
                return SurveillanceServiceErrors.firebaseFailure();
            }

            default: {
                Log.d("tag", "ExternalServiceErrorsMapping->mapFromDeviceGroupService()->UNKNOWN_ERROR_CODE: " + error.code() + " - " + error.message());
                return SurveillanceServiceErrors.commonServiceError();
            }
        }
    }

    private ServiceError mapFromFbsMessagingService(ServiceError error) {
        switch (error.code()) {
            case ("0"): {
                return SurveillanceServiceErrors.firebaseFailure();
            }

            default: {
                Log.d("tag", "ExternalServiceErrorsMapping->mapFromFbsMessagingService()->UNKNOWN_ERROR_CODE: " + error.code() + " - " + error.message());
                return SurveillanceServiceErrors.commonServiceError();
            }
        }
    }

    private ServiceError mapFromCameraService(ServiceError error) {
        switch (error.code()) {
            case ("1"): {
                return SurveillanceServiceErrors.badCameraPreviewImageData();
            }

            default: {
                Log.d("tag", "ExternalServiceErrorsMapping->mapFromCameraService()->UNKNOWN_ERROR_CODE: " + error.code() + " - " + error.message());
                return SurveillanceServiceErrors.commonServiceError();
            }
        }
    }
}
