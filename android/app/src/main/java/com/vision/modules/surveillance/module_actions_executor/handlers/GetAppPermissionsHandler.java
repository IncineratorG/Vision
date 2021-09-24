package com.vision.modules.surveillance.module_actions_executor.handlers;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.PowerManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;
import com.vision.modules.modules_common.interfaces.activity_request_permissions_result_callback.ActivityRequestPermissionsResultCallback;
import com.vision.modules.modules_common.interfaces.activity_request_permissions_result_callback.ModuleActivityRequestPermissionsResultCallbacks;
import com.vision.modules.modules_common.interfaces.activity_result_callback.ActivityResultCallback;
import com.vision.modules.modules_common.interfaces.activity_result_callback.ModuleActivityResultCallbacks;
import com.vision.modules.modules_common.interfaces.js_action_handler.JSActionHandler;

import static android.content.Context.POWER_SERVICE;

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

//        result.resolve(true);

//        Activity currentActivity = context.getCurrentActivity();
//        if (currentActivity == null) {
//            Log.d("tag", "GetAppPermissionsHandler->handle(): CURRENT_ACTIVITY_IS_NULL");
//            result.resolve(true);
//            return;
//        }
//
//        ActivityRequestPermissionsResultCallback callback = new ActivityRequestPermissionsResultCallback() {
//            @Override
//            public void handle(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//                Log.d("tag", "GetAppPermissionsHandler->handle(): CURRENT_ACTIVITY_IS_NULL");
//                if (requestCode == REQUEST_PERMISSIONS_CODE) {
//                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                        Log.d("tag", "GetAppPermissionsHandler->handle()->PERMISSIONS_GRANTED");
//                    } else {
//                        Log.d("tag", "GetAppPermissionsHandler->handle()->PERMISSIONS_NOT_GRANTED");
//                    }
//                } else {
//                    Log.d("tag", "GetAppPermissionsHandler->handle()->UNKNOWN_REQUEST_CODE: " + requestCode);
//                }
//            }
//        };
//
//        mRequestPermissionsResultCallbacks.set(REQUEST_PERMISSIONS_CODE, callback);
//
//        ActivityCompat.requestPermissions(
//                currentActivity,
//                new String[]{
//                        Manifest.permission.CAMERA,
//                        Manifest.permission.WAKE_LOCK,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_EXTERNAL_STORAGE,
//                },
//                REQUEST_PERMISSIONS_CODE
//        );

//        result.resolve(true);
    }
}

//        ActivityResultCallback activityResultCallback = new ActivityResultCallback() {
//            @Override
//            public void handle(Activity activity, int requestCode, int resultCode, Intent data) {
//                if (requestCode == REQUEST_PERMISSIONS_CODE) {
//
//                } else {
//                    Log.d("tag", "GetAppPermissionsHandler->handle()->UNKNOWN_REQUEST_CODE: " + requestCode);
//                }
//            }
//        };

//        ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);

//        int perm = PermissionChecker.checkPermission(context, Manifest.permission.CAMERA)

//        context.checkCallingOrSelfPermission()

//        PowerManager powerManager = (PowerManager) context.getSystemService(POWER_SERVICE);
//        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
//                "MyApp::MyWakelockTag");
//        wakeLock.acquire();
