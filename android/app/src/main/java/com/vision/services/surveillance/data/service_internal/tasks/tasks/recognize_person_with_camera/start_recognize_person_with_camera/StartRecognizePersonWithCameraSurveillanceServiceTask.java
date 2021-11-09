package com.vision.services.surveillance.data.service_internal.tasks.tasks.recognize_person_with_camera.start_recognize_person_with_camera;

import android.content.Context;
import android.util.Log;

import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.services.camera.CameraService;
import com.vision.services.surveillance.data.service_errors.SurveillanceServiceErrors;
import com.vision.services.surveillance.data.service_internal.interfaces.internal_task.InternalTask;
import com.vision.services.surveillance.data.service_internal.tasks.tasks.SurveillanceServiceInternalTasks;

import java.util.Map;

public class StartRecognizePersonWithCameraSurveillanceServiceTask implements InternalTask {
    private Context mContext;
    private String mCameraType;
    private int mImageRotationDegrees;
    private boolean mNeedUpdateDeviceMode;
    private String mCurrentGroupName;
    private String mCurrentGroupPassword;
    private String mCurrentDeviceName;
    private String mCurrentServiceMode;
    private OnTaskSuccess<Void> mOnSuccess;
    private OnTaskError<ServiceError> mOnError;

    public StartRecognizePersonWithCameraSurveillanceServiceTask(Context context,
                                                                 String cameraType,
                                                                 int imageRotationDegrees,
                                                                 boolean needUpdateDeviceMode,
                                                                 String currentGroupName,
                                                                 String currentGroupPassword,
                                                                 String currentDeviceName,
                                                                 String currentServiceMode,
                                                                 OnTaskSuccess<Void> onSuccess,
                                                                 OnTaskError<ServiceError> onError) {
        mContext = context;
        mCameraType = cameraType;
        mImageRotationDegrees = imageRotationDegrees;
        mNeedUpdateDeviceMode = needUpdateDeviceMode;
        mCurrentGroupName = currentGroupName;
        mCurrentGroupPassword = currentGroupPassword;
        mCurrentDeviceName = currentDeviceName;
        mCurrentServiceMode = currentServiceMode;
        mOnSuccess = onSuccess;
        mOnError = onError;
    }

    @Override
    public Object run(Map<String, Object> params) {
        CameraService cameraService = CameraService.get();
        if (mCameraType.equalsIgnoreCase("front")) {
            cameraService.startRecognizePersonWithFrontCamera(mContext, mImageRotationDegrees);

            SurveillanceServiceInternalTasks.updateAndPublishDeviceInfoSurveillanceServiceTask(
                    mContext,
                    mNeedUpdateDeviceMode,
                    mCurrentGroupName,
                    mCurrentGroupPassword,
                    mCurrentDeviceName,
                    mCurrentServiceMode,
                    mOnSuccess,
                    mOnError
            ).run(null);
        } else if (mCameraType.equalsIgnoreCase("back")) {
            cameraService.startRecognizePersonWithBackCamera(mContext, mImageRotationDegrees);

            SurveillanceServiceInternalTasks.updateAndPublishDeviceInfoSurveillanceServiceTask(
                    mContext,
                    mNeedUpdateDeviceMode,
                    mCurrentGroupName,
                    mCurrentGroupPassword,
                    mCurrentDeviceName,
                    mCurrentServiceMode,
                    mOnSuccess,
                    mOnError
            ).run(null);
        } else {
            Log.d("tag", "StartRecognizePersonWithCameraSurveillanceServiceTask->run()->UNKNOWN_CAMERA_TYPE: " + mCameraType);
            mOnError.onError(SurveillanceServiceErrors.commonServiceError());
        }

        return null;
    }
}
