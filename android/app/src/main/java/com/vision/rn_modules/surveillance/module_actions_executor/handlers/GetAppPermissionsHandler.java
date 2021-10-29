package com.vision.rn_modules.surveillance.module_actions_executor.handlers;


import android.Manifest;
import android.content.pm.PackageManager;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.vision.rn_modules.modules_common.interfaces.js_action_handler.JSActionHandler;

public class GetAppPermissionsHandler implements JSActionHandler {
    private int REQUEST_PERMISSIONS_CODE = 111;

    public GetAppPermissionsHandler() {

    }

    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        Log.d("tag", "GetAppPermissionsHandler->handle()");

        PermissionAwareActivity activity = (PermissionAwareActivity) context.getCurrentActivity();
        if (activity == null) {
            Log.d("tag", "GetAppPermissionsHandler->handle(): ACTIVITY_IS_NULL");
            result.resolve(true);
            return;
        }

        try {
            activity.requestPermissions(
                    new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.WAKE_LOCK,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                    },
                    REQUEST_PERMISSIONS_CODE,
                    (requestCode, permissions, grantResults) -> {
                        if (requestCode == REQUEST_PERMISSIONS_CODE) {
                            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                                Log.d("tag", "GetAppPermissionsHandler->handle()->PERMISSIONS_GRANTED");
                                result.resolve(true);
                            } else {
                                Log.d("tag", "GetAppPermissionsHandler->handle()->PERMISSIONS_NOT_GRANTED");
                                result.resolve(false);
                            }
                        } else {
                            Log.d("tag", "GetAppPermissionsHandler->handle()->UNKNOWN_REQUEST_CODE: " + requestCode);
                            result.resolve(false);
                        }

                        return true;
                    }
            );
        } catch (NoSuchMethodError e) {
            Log.d("tag", "GetAppPermissionsHandler->handle(): NO_SUCH_METHOD_ERROR");
            result.resolve(true);
            return;
        }
    }
}