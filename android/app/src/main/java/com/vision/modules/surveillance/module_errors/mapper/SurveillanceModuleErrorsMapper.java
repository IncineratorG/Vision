package com.vision.modules.surveillance.module_errors.mapper;


import android.util.Log;

import com.vision.common.data.service_error.ServiceError;
import com.vision.modules.modules_common.data.error.ModuleError;
import com.vision.modules.surveillance.module_errors.SurveillanceModuleErrors;

public class SurveillanceModuleErrorsMapper {
    public static final String SURVEILLANCE_SERVICE_TYPE = "surveillanceService";

    public static ModuleError mapToModuleError(String serviceType,
                                               ServiceError serviceError) {
        switch (serviceType) {
            case (SURVEILLANCE_SERVICE_TYPE): {
                return mapFromSurveillanceService(serviceError);
            }

            default: {
                Log.d("tag", "SurveillanceModuleErrorsMapper->mapToAuthModuleError()->UNKNOWN_ERROR_TYPE: " + serviceType);
                return SurveillanceModuleErrors.commonModuleServiceError();
            }
        }
    }

    private static ModuleError mapFromSurveillanceService(ServiceError serviceError) {
        switch (serviceError.code()) {
            case ("0"): {
                return SurveillanceModuleErrors.commonModuleServiceError();
            }

            case ("3"): {
                return SurveillanceModuleErrors.firebaseFailure();
            }

            default: {
                Log.d("tag", "SurveillanceModuleErrorsMapper->mapFromSurveillanceService()->UNKNOWN_SERVICE_ERROR_CODE: " + serviceError.code() + " - " + serviceError.message());
                return SurveillanceModuleErrors.commonModuleServiceError();
            }
        }
    }
}
