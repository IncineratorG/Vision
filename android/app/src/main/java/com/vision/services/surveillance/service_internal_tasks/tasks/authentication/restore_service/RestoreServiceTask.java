package com.vision.services.surveillance.service_internal_tasks.tasks.authentication.restore_service;

import android.content.Context;

import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.common.interfaces.service_sync_task.ServiceSyncTask;
import com.vision.services.surveillance.restore_service_manager.RestoreServiceManager;

import java.util.Map;

public class RestoreServiceTask implements ServiceSyncTask {
    private Context mContext;
    private OnTaskSuccess<Void> mOnSuccess;
    private OnTaskError<ServiceError> mOnError;
    private RestoreServiceManager mRestoreServiceManager;

    public RestoreServiceTask(Context context,
                              OnTaskSuccess<Void> onSuccess,
                              OnTaskError<ServiceError> onError) {
        mContext = context;
        mOnSuccess = onSuccess;
        mOnError = onError;

        mRestoreServiceManager = new RestoreServiceManager();
    }

    @Override
    public Object run(Map<String, Object> params) {
        mRestoreServiceManager.restore(mContext, mOnSuccess, mOnError);
        return null;
    }
}
