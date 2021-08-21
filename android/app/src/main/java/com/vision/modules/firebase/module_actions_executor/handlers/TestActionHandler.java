package com.vision.modules.firebase.module_actions_executor.handlers;


import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.vision.common.services.firebase.FirebaseService;
import com.vision.modules.modules_common.interfaces.js_action_handler.JSActionHandler;

public class TestActionHandler implements JSActionHandler {
    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        FirebaseService.get().test();

        result.resolve(true);
    }
}
