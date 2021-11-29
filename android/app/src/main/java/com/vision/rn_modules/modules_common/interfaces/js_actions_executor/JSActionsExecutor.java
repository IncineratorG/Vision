package com.vision.rn_modules.modules_common.interfaces.js_actions_executor;


import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;

public interface JSActionsExecutor {
    void execute(ReactApplicationContext context, ReadableMap action, Promise result);
}

