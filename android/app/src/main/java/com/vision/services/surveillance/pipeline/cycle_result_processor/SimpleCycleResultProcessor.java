package com.vision.services.surveillance.pipeline.cycle_result_processor;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.services.firebase_communication.FBSCommunicationService;
import com.vision.services.firebase_paths.FBSPathsService;
import com.vision.services.surveillance.SurveillanceService;
import com.vision.services.surveillance.pipeline.commons.data.pipeline_operation_state.PipelineOperationState;
import com.vision.services.surveillance.pipeline.commons.interfaces.pipeline_cycle_result_processor.PipelineCycleResultProcessor;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleCycleResultProcessor implements PipelineCycleResultProcessor {
    public SimpleCycleResultProcessor() {

    }

    @Override
    public void process(Context context,
                        JSONObject cycleResult,
                        List<PipelineOperationState> states,
                        OnTaskSuccess<Void> onSuccess,
                        OnTaskError<Object> onError) {
        Log.d("TAG", "SimpleCycleResultProcessor->process(): " + cycleResult.toString());

        Map<String, Object> deviceStatusInfo = new HashMap<>();
        for (int i = 0; i < states.size(); ++i) {
            PipelineOperationState state = states.get(i);
            deviceStatusInfo.putAll(state.description().toServiceObject());
        }

        Log.d("TAG", "SimpleCycleResultProcessor->process(): " + deviceStatusInfo.toString());

        publishDeviceStatus(deviceStatusInfo, onSuccess, onError);
    }

    private void publishDeviceStatus(Map<String, Object> deviceStatusInfo,
                                     OnTaskSuccess<Void> onSuccess,
                                     OnTaskError<Object> onError) {
        SurveillanceService surveillanceService = SurveillanceService.get();

        String currentDeviceName = surveillanceService.currentDeviceName();
        String currentGroupName = surveillanceService.currentGroupName();
        String currentGroupPassword = surveillanceService.currentGroupPassword();

        List<String> deviceStatusInfoPath = FBSPathsService.get().deviceStatusInfoPath(
                currentGroupName, currentGroupPassword, currentDeviceName
        );

        OnCompleteListener<Void> onCompleteListener = task -> {
            Log.d(
                    "TAG",
                    "===> SimpleCycleResultProcessor->publishDeviceStatusOnce()->DEVICE_STATUS_INFO_PUBLISHED <==="
            );

            onSuccess.onSuccess(null);
        };

        OnFailureListener onFailureListener = e -> {
            Log.d(
                    "TAG",
                    "===> SimpleCycleResultProcessor->publishDeviceStatusOnce()->DEVICE_STATUS_INFO_PUBLISHED_ERROR <==="
            );

            onError.onError(e);
        };

        FBSCommunicationService.get().setMapValue(
                deviceStatusInfoPath,
                deviceStatusInfo,
                onCompleteListener,
                onFailureListener
        );
    }
}
