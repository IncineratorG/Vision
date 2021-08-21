package com.vision.modules.modules_common.interfaces.js_action_handler;


import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;

public interface JSActionHandler {
    void handle(ReactApplicationContext context, ReadableMap action, Promise result);
}
