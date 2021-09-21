package com.vision.modules.surveillance.module_actions_executor.handlers;


import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.vision.common.services.surveillance.SurveillanceService;
import com.vision.modules.modules_common.data.error.ModuleError;
import com.vision.modules.modules_common.interfaces.js_action_handler.JSActionHandler;
import com.vision.modules.surveillance.module_actions.payloads.SurveillanceJSActionsPayloads;
import com.vision.modules.surveillance.module_actions.payloads.payloads.CancelRequestPayload;
import com.vision.modules.surveillance.module_errors.SurveillanceModuleErrors;

public class CancelRequestHandler implements JSActionHandler {
    private final String ACTION_PAYLOAD = "payload";

    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        Log.d("tag", "CancelRequestHandler->handle()");

        ReadableMap payloadMap = action.getMap(ACTION_PAYLOAD);
        if (payloadMap == null) {
            ModuleError error = SurveillanceModuleErrors.badPayload();
            result.reject(error.code(), error.message());
            return;
        }

        CancelRequestPayload payload = SurveillanceJSActionsPayloads
                .cancelRequestPayload(payloadMap);
        if (!payload.isValid()) {
            ModuleError error = SurveillanceModuleErrors.badPayload();
            result.reject(error.code(), error.message());
            return;
        }

        boolean cancelResult = SurveillanceService.get().cancelRequest(payload.requestId());

        WritableMap resultPayload = new WritableNativeMap();
        resultPayload.putString("requestId", payload.requestId());
        resultPayload.putBoolean("successful", cancelResult);

        result.resolve(resultPayload);
    }
}
