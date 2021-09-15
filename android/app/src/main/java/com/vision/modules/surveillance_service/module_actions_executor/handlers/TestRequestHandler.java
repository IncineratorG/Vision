package com.vision.modules.surveillance_service.module_actions_executor.handlers;


import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.vision.common.services.firebase.FBSService;
import com.vision.common.services.firebase_paths.FBSPathsService;
import com.vision.common.services.surveillance.SurveillanceService;
import com.vision.common.services.surveillance.data.request.Request;
import com.vision.modules.modules_common.interfaces.js_action_handler.JSActionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestRequestHandler implements JSActionHandler {
    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        Log.d("tag", "TestRequestHandler");

        FBSPathsService fbsPathsService = FBSPathsService.get();
        List<String> testPath = fbsPathsService.test();

        Map<String, Object> map = new HashMap<>();
        map.put("k1", "v1");
        map.put("k2", "v2");
        map.put("k3", "v3");

        FBSService fbsService = FBSService.get();
        fbsService.test(testPath, map);

//        SurveillanceService service = SurveillanceService.get();
//
//        Request testRequest = service.requests().testRequest();
//
//        String receiverLogin = "testReceiver";
//
//        service.sendRequest(
//                receiverLogin,
//                testRequest,
//                () -> {
//                    Log.d("tag", "TestRequestHandler->handle(): REQUEST_DELIVERED_CALLBACK");
//                },
//                response -> {
//                    Log.d("tag", "TestRequestHandler->handle()->RESPONSE_RECEIVED_CALLBACK)");
//                },
//                error -> {
//                    Log.d("tag", "TestRequestHandler->handle()->ERROR: " + error.code() + " - " + error.message());
//                }
//        );

        result.resolve(true);
    }
}
