package com.vision.rn_modules.modules_common.interfaces.activity_request_permissions_result_callback;


import androidx.annotation.NonNull;

public interface ActivityRequestPermissionsResultCallback {
    void handle(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
}
