package com.vision.rn_modules.surveillance.module_actions_executor;


import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.vision.rn_modules.modules_common.data.error.ModuleError;
import com.vision.rn_modules.modules_common.interfaces.js_action_handler.JSActionHandler;
import com.vision.rn_modules.modules_common.interfaces.js_actions_executor.JSActionsExecutor;
import com.vision.rn_modules.surveillance.module_actions.types.SurveillanceJSActionTypes;
import com.vision.rn_modules.surveillance.module_actions_executor.handlers.CancelRequestHandler;
import com.vision.rn_modules.surveillance.module_actions_executor.handlers.GetAppPermissionsHandler;
import com.vision.rn_modules.surveillance.module_actions_executor.handlers.GetDevicesInGroupHandler;
import com.vision.rn_modules.surveillance.module_actions_executor.handlers.IsServiceRunningHandler;
import com.vision.rn_modules.surveillance.module_actions_executor.handlers.Test_SentTestNotificationHandler;
import com.vision.rn_modules.surveillance.module_actions_executor.handlers.Test_TestCameraMotionDetectionHandler;
import com.vision.rn_modules.surveillance.module_actions_executor.handlers.Test_TestMotionSensorHandler;
import com.vision.rn_modules.surveillance.module_actions_executor.handlers.Test_TestStartBackCameraHandler;
import com.vision.rn_modules.surveillance.module_actions_executor.handlers.Test_TestStartCameraPreviewHandler;
import com.vision.rn_modules.surveillance.module_actions_executor.handlers.Test_TestStartFrontCameraHandler;
import com.vision.rn_modules.surveillance.module_actions_executor.handlers.Test_TestStopBackCameraHandler;
import com.vision.rn_modules.surveillance.module_actions_executor.handlers.Test_TestStopCameraPreviewHandler;
import com.vision.rn_modules.surveillance.module_actions_executor.handlers.Test_TestStopFrontCameraHandler;
import com.vision.rn_modules.surveillance.module_actions_executor.handlers.Test_TestTakeBackCameraImageHandler;
import com.vision.rn_modules.surveillance.module_actions_executor.handlers.Test_TestTakeCameraPreviewPictureHandler;
import com.vision.rn_modules.surveillance.module_actions_executor.handlers.Test_TestTakeFrontCameraImageHandler;
import com.vision.rn_modules.surveillance.module_actions_executor.handlers.send_request.SendRequestHandler;
import com.vision.rn_modules.surveillance.module_actions_executor.handlers.StartServiceHandler;
import com.vision.rn_modules.surveillance.module_actions_executor.handlers.StopServiceHandler;
import com.vision.rn_modules.surveillance.module_actions_executor.handlers.Test_TestRequestHandler;
import com.vision.rn_modules.surveillance.module_errors.SurveillanceModuleErrors;

import java.util.HashMap;
import java.util.Map;

public class SurveillanceJSActionsExecutor implements JSActionsExecutor {
    private static final String ACTION_TYPE = "type";
    private Map<String, JSActionHandler> mHandlers;

    public SurveillanceJSActionsExecutor() {
        mHandlers = new HashMap<>();
        // ===
        mHandlers.put(SurveillanceJSActionTypes.TEST_REQUEST, new Test_TestRequestHandler());
        mHandlers.put(SurveillanceJSActionTypes.SEND_TEST_NOTIFICATION, new Test_SentTestNotificationHandler());
        mHandlers.put(SurveillanceJSActionTypes.TEST_MOTION_SENSOR, new Test_TestMotionSensorHandler());
//        mHandlers.put(SurveillanceJSActionTypes.TEST_CAMERA_MOTION_DETECTION, new Test_TestCameraMotionDetectionHandler_V2());
        mHandlers.put(SurveillanceJSActionTypes.TEST_CAMERA_MOTION_DETECTION, new Test_TestCameraMotionDetectionHandler());

        mHandlers.put(SurveillanceJSActionTypes.TEST_START_CAMERA_PREVIEW, new Test_TestStartCameraPreviewHandler());
        mHandlers.put(SurveillanceJSActionTypes.TEST_STOP_CAMERA_PREVIEW, new Test_TestStopCameraPreviewHandler());
        mHandlers.put(SurveillanceJSActionTypes.TEST_TAKE_CAMERA_PREVIEW_PICTURE, new Test_TestTakeCameraPreviewPictureHandler());

        mHandlers.put(SurveillanceJSActionTypes.TEST_START_BACK_CAMERA, new Test_TestStartBackCameraHandler());
        mHandlers.put(SurveillanceJSActionTypes.TEST_START_FRONT_CAMERA, new Test_TestStartFrontCameraHandler());
        mHandlers.put(SurveillanceJSActionTypes.TEST_STOP_BACK_CAMERA, new Test_TestStopBackCameraHandler());
        mHandlers.put(SurveillanceJSActionTypes.TEST_STOP_FRONT_CAMERA, new Test_TestStopFrontCameraHandler());
        mHandlers.put(SurveillanceJSActionTypes.TEST_TAKE_BACK_CAMERA_PICTURE, new Test_TestTakeBackCameraImageHandler());
        mHandlers.put(SurveillanceJSActionTypes.TEST_TAKE_FRONT_CAMERA_PICTURE, new Test_TestTakeFrontCameraImageHandler());
        // ===
        mHandlers.put(SurveillanceJSActionTypes.GET_APP_PERMISSIONS, new GetAppPermissionsHandler());
        mHandlers.put(SurveillanceJSActionTypes.IS_RUNNING, new IsServiceRunningHandler());
        mHandlers.put(SurveillanceJSActionTypes.START_SERVICE, new StartServiceHandler());
        mHandlers.put(SurveillanceJSActionTypes.STOP_SERVICE, new StopServiceHandler());
        mHandlers.put(SurveillanceJSActionTypes.GET_DEVICES_IN_GROUP, new GetDevicesInGroupHandler());
        mHandlers.put(SurveillanceJSActionTypes.SEND_REQUEST, new SendRequestHandler());
        mHandlers.put(SurveillanceJSActionTypes.CANCEL_REQUEST, new CancelRequestHandler());
    }

    @Override
    public void execute(ReactApplicationContext context, ReadableMap action, Promise result) {
        if (action == null) {
            ModuleError error = SurveillanceModuleErrors.badAction();
            result.reject(error.code(), error.message());
            return;
        }

        final String type = action.getString(ACTION_TYPE);
        if (type == null) {
            ModuleError error = SurveillanceModuleErrors.badActionType();
            result.reject(error.code(), error.message());
            return;
        }

        JSActionHandler actionHandler = mHandlers.get(type);
        if (actionHandler != null) {
            actionHandler.handle(context, action, result);
        } else {
            ModuleError error = SurveillanceModuleErrors.unknownActionType();
            result.reject(error.code(), error.message());
        }
    }
}
