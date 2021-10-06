package com.vision.common.services.auth;


import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.vision.common.constants.AppConstants;
import com.vision.common.data.hybrid_service_objects.device_info.DeviceInfo;
import com.vision.common.data.service_error.ServiceError;
import com.vision.common.data.service_generic_callbacks.OnTaskError;
import com.vision.common.data.service_generic_callbacks.OnTaskSuccess;
import com.vision.common.services.auth.data.service_errors.AuthServiceErrors;
import com.vision.common.services.device_info.DeviceInfoService;
import com.vision.common.services.firebase_communication.FBSCommunicationService;
import com.vision.common.services.firebase_paths.FBSPathsService;

import java.util.List;

public class AuthService {
    private static AuthService sInstance;

    private String mCurrentGroupName;
    private String mCurrentGroupPassword;
    private String mCurrentDeviceName;

    private AuthService() {

    }

    public static synchronized AuthService get() {
        if (sInstance == null) {
            sInstance = new AuthService();
        }

        return sInstance;
    }

    public String currentGroupName() {
        return mCurrentGroupName;
    }

    public String currentGroupPassword() {
        return mCurrentGroupPassword;
    }

    public String currentDeviceName() {
        return mCurrentDeviceName;
    }

    public boolean isLoggedIn() {
        return mCurrentGroupName != null && mCurrentGroupPassword != null && mCurrentDeviceName != null;
    }

    public void createGroupWithDevice(Context context,
                                      String groupName,
                                      String groupPassword,
                                      String deviceName,
                                      OnTaskSuccess<Void> onSuccess,
                                      OnTaskError<ServiceError> onError) {
        List<String> groupNamePath = FBSPathsService.get().groupNamePath(groupName);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.d("tag", "AuthService->createGroupWithDevice(): SNAPSHOT_NOT_EXIST");

                    ServiceError serviceError = AuthServiceErrors.groupAlreadyExist();
                    onError.onError(serviceError);
                } else {
                    DeviceInfo deviceInfo = DeviceInfoService.get().currentDeviceInfo(
                            context,
                            deviceName,
                            AppConstants.DEVICE_MODE_USER
                    );

                    mCurrentGroupName = groupName;
                    mCurrentGroupPassword = groupPassword;
                    mCurrentDeviceName = deviceName;

                    updateDeviceInfo(
                            groupName,
                            groupPassword,
                            deviceName,
                            deviceInfo,
                            onSuccess,
                            onError
                    );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("tag", "AuthService->createGroupWithDevice(): ON_CANCELLED");

                ServiceError serviceError = AuthServiceErrors.firebaseFailure();
                onError.onError(serviceError);
            }
        };

        FBSCommunicationService.get().getValue(groupNamePath, listener);
    }

    public void registerDeviceInGroup(Context context,
                                      String groupName,
                                      String groupPassword,
                                      String deviceName,
                                      OnTaskSuccess<Void> onSuccess,
                                      OnTaskError<ServiceError> onError) {
        List<String> groupNamePath = FBSPathsService.get().groupNamePath(groupName);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    Log.d("tag", "AuthService->registerDeviceInGroup(): SNAPSHOT_NOT_EXIST");

                    ServiceError serviceError = AuthServiceErrors.groupNotExist();
                    onError.onError(serviceError);
                    return;
                }

                DataSnapshot passwordSnapshot = snapshot.child(groupPassword);
                if (!passwordSnapshot.exists()) {
                    Log.d("tag", "AuthService->registerDeviceInGroup(): PASSWORD_SNAPSHOT_NOT_EXIST");

                    ServiceError serviceError = AuthServiceErrors.incorrectGroupPassword();
                    onError.onError(serviceError);
                    return;
                }

                DataSnapshot deviceNameSnapshot = passwordSnapshot.child(deviceName);
                if (deviceNameSnapshot.exists()) {
                    Log.d("tag", "AuthService->registerDeviceInGroup(): DEVICE_NAME_SNAPSHOT_NOT_EXIST");

                    ServiceError serviceError = AuthServiceErrors.deviceNameAlreadyExist();
                    onError.onError(serviceError);
                    return;
                }

                DeviceInfo deviceInfo = DeviceInfoService.get().currentDeviceInfo(
                        context,
                        deviceName,
                        AppConstants.DEVICE_MODE_USER
                );

                mCurrentGroupName = groupName;
                mCurrentGroupPassword = groupPassword;
                mCurrentDeviceName = deviceName;

                updateDeviceInfo(
                        groupName,
                        groupPassword,
                        deviceName,
                        deviceInfo,
                        onSuccess,
                        onError
                );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("tag", "AuthService->registerDeviceInGroup(): ON_CANCELLED");

                ServiceError serviceError = AuthServiceErrors.firebaseFailure();
                onError.onError(serviceError);
            }
        };

        FBSCommunicationService.get().getValue(groupNamePath, listener);
    }

    public void loginDeviceInGroup(Context context,
                                   String groupName,
                                   String groupPassword,
                                   String deviceName,
                                   OnTaskSuccess<Void> onSuccess,
                                   OnTaskError<ServiceError> onError) {
        List<String> groupNamePath = FBSPathsService.get().groupNamePath(groupName);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    Log.d("tag", "AuthService->loginDeviceInGroup(): SNAPSHOT_NOT_EXIST");

                    ServiceError serviceError = AuthServiceErrors.groupNotExist();
                    onError.onError(serviceError);
                    return;
                }

                DataSnapshot passwordSnapshot = snapshot.child(groupPassword);
                if (!passwordSnapshot.exists()) {
                    Log.d("tag", "AuthService->loginDeviceInGroup(): PASSWORD_SNAPSHOT_NOT_EXIST");

                    ServiceError serviceError = AuthServiceErrors.incorrectGroupPassword();
                    onError.onError(serviceError);
                    return;
                }

                DataSnapshot deviceNameSnapshot = passwordSnapshot.child(deviceName);
                if (!deviceNameSnapshot.exists()) {
                    Log.d("tag", "AuthService->loginDeviceInGroup(): DEVICE_NAME_SNAPSHOT_NOT_EXIST");

                    ServiceError serviceError = AuthServiceErrors.deviceNameNotExist();
                    onError.onError(serviceError);
                    return;
                }

                DataSnapshot deviceInfoSnapshot = deviceNameSnapshot.child(FBSPathsService.get().DEVICE_INFO_PATH);
                if (deviceInfoSnapshot.exists()) {
                    Object deviceInfoObject = deviceInfoSnapshot.getValue();
                    if (deviceInfoObject != null) {
                        DeviceInfo deviceInfo = new DeviceInfo(deviceInfoObject);
                        DeviceInfo updatedDeviceInfo = DeviceInfoService.get().updateDeviceInfo(context, deviceInfo);
                        updatedDeviceInfo = DeviceInfoService.get().changeDeviceMode(AppConstants.DEVICE_MODE_USER, updatedDeviceInfo);

                        long isAliveDelta = updatedDeviceInfo.lastUpdateTimestamp() - deviceInfo.lastUpdateTimestamp();
                        if (isAliveDelta < AppConstants.IS_ALIVE_SIGNALING_PERIOD + 5000) {
                            Log.d("tag", "AuthService->loginDeviceInGroup(): DEVICE_ALREADY_LOGGED_IN");

                            ServiceError serviceError = AuthServiceErrors.deviceAlreadyLoggedIn();
                            onError.onError(serviceError);
                            return;
                        }

                        mCurrentGroupName = groupName;
                        mCurrentGroupPassword = groupPassword;
                        mCurrentDeviceName = deviceName;

                        updateDeviceInfo(
                                groupName,
                                groupPassword,
                                deviceName,
                                updatedDeviceInfo,
                                onSuccess,
                                onError
                        );
                    } else {
                        DeviceInfo updatedDeviceInfo = DeviceInfoService.get().currentDeviceInfo(
                                context,
                                deviceName,
                                AppConstants.DEVICE_MODE_USER
                        );

                        mCurrentGroupName = groupName;
                        mCurrentGroupPassword = groupPassword;
                        mCurrentDeviceName = deviceName;

                        updateDeviceInfo(
                                groupName,
                                groupPassword,
                                deviceName,
                                updatedDeviceInfo,
                                onSuccess,
                                onError
                        );
                    }
                } else {
                    Log.d("tag", "AuthService->loginDeviceInGroup(): DEVICE_INFO_SNAPSHOT_NOT_EXIST");

                    ServiceError serviceError = AuthServiceErrors.firebaseFailure();
                    onError.onError(serviceError);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("tag", "AuthService->loginDeviceInGroup(): ON_CANCELLED");

                ServiceError serviceError = AuthServiceErrors.firebaseFailure();
                onError.onError(serviceError);
            }
        };

        FBSCommunicationService.get().getValue(groupNamePath, listener);
    }

    public void logoutDeviceFromGroup(OnTaskSuccess<Void> onSuccess,
                                      OnTaskError<ServiceError> onError) {
        mCurrentGroupName = null;
        mCurrentGroupPassword = null;
        mCurrentDeviceName = null;

        onSuccess.onSuccess(null);
    }

    private void updateDeviceInfo(String groupName,
                                  String groupPassword,
                                  String deviceName,
                                  DeviceInfo updatedDeviceInfoData,
                                  OnTaskSuccess<Void> onSuccess,
                                  OnTaskError<ServiceError> onError) {
        OnCompleteListener<Void> onCompleteListener = task -> {
            onSuccess.onSuccess(null);
        };

        OnFailureListener onFailureListener = e -> {
            Log.d("tag", "AuthService->updateDeviceInfo()->ON_FAILURE: " + e.toString());

            ServiceError serviceError = AuthServiceErrors.firebaseFailure();
            onError.onError(serviceError);
        };

        List<String> deviceInfoPath = FBSPathsService.get().deviceInfoPath(groupName, groupPassword, deviceName);

        FBSCommunicationService.get().setMapValue(
                deviceInfoPath,
                updatedDeviceInfoData.toServiceObject(),
                onCompleteListener,
                onFailureListener
        );
    }
}
