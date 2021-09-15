package com.vision.modules.surveillance_service.module_actions_executor.handlers;


import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.vision.common.data.hybrid_service_objects.device_info.DeviceInfo;
import com.vision.common.services.firebase.FBSService;
import com.vision.common.services.firebase_paths.FBSPathsService;
import com.vision.modules.modules_common.interfaces.js_action_handler.JSActionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestRequestHandler implements JSActionHandler {
    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        Log.d("tag", "TestRequestHandler->handle()");

        send();
//        receive();

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

    private void send() {
        Log.d("tag", "TestRequestHandler->send()");

        DeviceInfo deviceInfo = new DeviceInfo();

        // ===
//        DeviceInfo deviceInfo = new DeviceInfo();
//
//        FBSPathsService fbsPathsService = FBSPathsService.get();
//        List<String> testPath = fbsPathsService.test();
//
//        FBSService fbsService = FBSService.get();
//        fbsService.test(testPath, deviceInfo.toServiceObject());
        // ===

//        FBSPathsService fbsPathsService = FBSPathsService.get();
//        List<String> testPath = fbsPathsService.test();
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("k1", "v1");
//        map.put("k2", "v2");
//        map.put("k3", "v3");
//
//        // =
//        Map<String, Object> innerMap = new HashMap<>();
//        innerMap.put("k1", "v1");
//        innerMap.put("k2", "v2");
//        innerMap.put("k3", "v3");
//
//        Map<String, Object> listObjectMap = new HashMap<>();
//        listObjectMap.put("lom_k1", "l1");
//        listObjectMap.put("lom_k2", "l2");
//
//        List<Map<String, Object>> list = new ArrayList<>();
//        list.add(listObjectMap);
//        list.add(listObjectMap);
//        list.add(listObjectMap);
//
//        innerMap.put("k5", list);
//        // =
//
//        map.put("k4", innerMap);
//
//        FBSService fbsService = FBSService.get();
//        fbsService.test(testPath, map);
    }

    private void receive() {
        Log.d("tag", "TestRequestHandler->receive()");

        FBSPathsService fbsPathsService = FBSPathsService.get();
        List<String> testPath = fbsPathsService.test();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
//                    Object value = snapshot.getValue();
                    Map<String, Object> value = (Map<String, Object>) snapshot.getValue();
                    if (value != null) {
                        Log.d("tag", "TestRequestHandler->receive(): " + value.toString());
                    } else {
                        Log.d("tag", "TestRequestHandler->receive()->OBJECT_IS_NULL");
                    }
                } else {
                    Log.d("tag", "TestRequestHandler->receive()->SNAPSHOT_NOT_EXIST");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        FBSService fbsService = FBSService.get();
        fbsService.test2(testPath, listener);
    }
}