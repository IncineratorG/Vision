package com.vision.modules.auth.module_errors;


import android.util.Log;

import com.vision.common.data.service_error.ServiceError;
import com.vision.modules.modules_common.data.error.ModuleError;

public class AuthModuleErrorsMapper {
    public static final String SURVEILLANCE_SERVICE_TYPE = "surveillanceService";

    public static ModuleError mapToModuleError(String serviceType,
                                               ServiceError serviceError) {
        switch (serviceType) {
            case (SURVEILLANCE_SERVICE_TYPE): {
                return mapFromSurveillanceService(serviceError);
            }

            default: {
                Log.d("tag", "AuthModuleErrorsMapper->mapToAuthModuleError()->UNKNOWN_ERROR_TYPE: " + serviceType);
                return AuthModuleErrors.commonModuleServiceError();
            }
        }
    }

    private static ModuleError mapFromSurveillanceService(ServiceError serviceError) {
        switch (serviceError.code()) {
            case ("0"): {
                return AuthModuleErrors.commonModuleServiceError();
            }

            case ("3"): {
                return AuthModuleErrors.firebaseFailure();
            }

            case ("4"): {
                return AuthModuleErrors.groupAlreadyExist();
            }

            case ("5"): {
                return AuthModuleErrors.groupNotExist();
            }

            case ("6"): {
                return AuthModuleErrors.incorrectGroupPassword();
            }

            case ("7"): {
                return AuthModuleErrors.deviceNameAlreadyExist();
            }

            case ("8"): {
                return AuthModuleErrors.deviceNameNotExist();
            }

            case ("9"): {
                return AuthModuleErrors.deviceAlreadyLoggedIn();
            }

            default: {
                Log.d("tag", "AuthModuleErrorsMapper->mapFromSurveillanceService()->UNKNOWN_SERVICE_ERROR_CODE: " + serviceError.code() + " - " + serviceError.message());
                return AuthModuleErrors.commonModuleServiceError();
            }
        }
    }
}
