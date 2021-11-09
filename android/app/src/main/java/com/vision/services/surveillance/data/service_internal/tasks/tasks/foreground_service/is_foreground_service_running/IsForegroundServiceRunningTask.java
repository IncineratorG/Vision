package com.vision.services.surveillance.data.service_internal.tasks.tasks.foreground_service.is_foreground_service_running;

import android.app.ActivityManager;
import android.content.Context;

import com.vision.android_services.foreground.surveillance.SurveillanceForegroundService;
import com.vision.common.interfaces.service_sync_task.ServiceSyncTask;

import java.util.Map;

public class IsForegroundServiceRunningTask implements ServiceSyncTask {
    private Context mContext;

    public IsForegroundServiceRunningTask(Context context) {
        mContext = context;
    }

    @Override
    public Object run(Map<String, Object> params) {
        ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (SurveillanceForegroundService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
