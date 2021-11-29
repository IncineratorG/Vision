package com.vision.rn_modules.auth.module_actions_executor.handlers;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.vision.common.data.hybrid_objects.authentication_data.AuthenticationData;
import com.vision.rn_modules.modules_common.interfaces.js_action_handler.JSActionHandler;
import com.vision.services.app_storages.AppStorages;
import com.vision.services.auth.AuthService;

public class GetCurrentAuthenticationDataHandler implements JSActionHandler {
    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        AuthenticationData lastSavedAuthenticationData =
                AppStorages.get().surveillanceStorage().getLastAuthenticationData(context);

        String groupName = lastSavedAuthenticationData.groupName();
        String groupPassword = lastSavedAuthenticationData.groupPassword();
        String deviceName = lastSavedAuthenticationData.deviceName();
        boolean isLoggedIn = AuthService.get().isLoggedIn();

        WritableMap resultData = new WritableNativeMap();
        resultData.putString("groupName", groupName);
        resultData.putString("groupPassword", groupPassword);
        resultData.putString("deviceName", deviceName);
        resultData.putBoolean("loggedIn", isLoggedIn);

        result.resolve(lastSavedAuthenticationData.toWritableMap());
    }
}
