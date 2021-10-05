package com.vision.common.data.hybrid_objects.app_settings;


import android.util.Log;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.vision.modules.modules_common.interfaces.hybrid_object.HybridObject;

import org.json.JSONException;
import org.json.JSONObject;

public class AppSettings implements HybridObject {
    private final String SURVEILLANCE_SETTINGS_FIELD = "surveillance";
    private final String BACK_CAMERA_IMAGE_SETTINGS_FIELD = "backCameraImage";
    private final String BACK_CAMERA_IMAGE_QUALITY_FIELD = "quality";
    private final String FRONT_CAMERA_IMAGE_QUALITY_SETTINGS_FIELD = "frontCameraImage";
    private final String FRONT_CAMERA_IMAGE_QUALITY_FIELD = "quality";
    private final String NOTIFICATIONS_SETTINGS_FIELD = "notifications";
    private final String NOTIFICATIONS_SETTINGS_RECEIVE_NOTIFICATIONS_FROM_CURRENT_GROUP_FIELD = "receiveNotificationsFromCurrentGroup";

    private WritableMap mWritableMap;
    private JSONObject mJSONObject;

    private boolean mIsEmpty;
    private boolean mInitializedFromReadableMap;
    private boolean mInitializedFromStringifiedObject;

    public AppSettings(ReadableMap readableMap) {
        if (readableMap == null) {
            mIsEmpty = true;
            return;
        }

        mWritableMap = new WritableNativeMap();
        mWritableMap.merge(readableMap);

        mInitializedFromReadableMap = true;
        mIsEmpty = false;
    }

    public AppSettings(String stringifiedObject) {
        if (stringifiedObject == null || stringifiedObject.isEmpty()) {
            mIsEmpty = true;
            return;
        }

        try {
            mJSONObject = new JSONObject(stringifiedObject);

            mInitializedFromStringifiedObject = true;
            mIsEmpty = false;
        } catch (JSONException e) {
            e.printStackTrace();
            mIsEmpty = true;
        }
    }

    public AppSettings(AppSettings other) {
        mIsEmpty = other.mIsEmpty;
        mInitializedFromReadableMap = other.mInitializedFromReadableMap;
        mInitializedFromStringifiedObject = other.mInitializedFromStringifiedObject;

        if (other.mWritableMap != null) {
            mWritableMap = new WritableNativeMap();
            mWritableMap.merge(other.mWritableMap);
        }

        if (other.mJSONObject != null) {
            try {
                mJSONObject = new JSONObject(other.mJSONObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public WritableMap toWritableMap() {
        if (mIsEmpty) {
            return null;
        }

        if (mInitializedFromReadableMap) {
            return mWritableMap;
        }

        if (mInitializedFromStringifiedObject) {
            mWritableMap = new WritableNativeMap();

            mWritableMap = new WritableNativeMap();
            try {
                // ===
                JSONObject surveillanceSettingsObject = mJSONObject.getJSONObject(SURVEILLANCE_SETTINGS_FIELD);

                JSONObject backCameraImageSettingsObject = surveillanceSettingsObject.getJSONObject(BACK_CAMERA_IMAGE_SETTINGS_FIELD);
                String backCameraImageQuality = backCameraImageSettingsObject.getString(BACK_CAMERA_IMAGE_QUALITY_FIELD);

                JSONObject frontCameraImageSettingsObject = surveillanceSettingsObject.getJSONObject(FRONT_CAMERA_IMAGE_QUALITY_SETTINGS_FIELD);
                String frontCameraImageQuality = frontCameraImageSettingsObject.getString(FRONT_CAMERA_IMAGE_QUALITY_FIELD);

                JSONObject notificationSettingsObject = surveillanceSettingsObject.getJSONObject(NOTIFICATIONS_SETTINGS_FIELD);
                boolean receiveNotificationsFromCurrentGroup = notificationSettingsObject.getBoolean(NOTIFICATIONS_SETTINGS_RECEIVE_NOTIFICATIONS_FROM_CURRENT_GROUP_FIELD);
                // ===

                // ===
                WritableMap surveillanceSettingsMap = new WritableNativeMap();

                WritableMap backCameraImageSettingsMap = new WritableNativeMap();
                backCameraImageSettingsMap.putString(BACK_CAMERA_IMAGE_QUALITY_FIELD, backCameraImageQuality);

                surveillanceSettingsMap.putMap(BACK_CAMERA_IMAGE_SETTINGS_FIELD, backCameraImageSettingsMap);

                WritableMap frontCameraImageSettingsMap = new WritableNativeMap();
                frontCameraImageSettingsMap.putString(FRONT_CAMERA_IMAGE_QUALITY_FIELD, frontCameraImageQuality);

                surveillanceSettingsMap.putMap(FRONT_CAMERA_IMAGE_QUALITY_SETTINGS_FIELD, frontCameraImageSettingsMap);

                WritableMap notificationSettingsMap = new WritableNativeMap();
                notificationSettingsMap.putBoolean(NOTIFICATIONS_SETTINGS_RECEIVE_NOTIFICATIONS_FROM_CURRENT_GROUP_FIELD, receiveNotificationsFromCurrentGroup);

                surveillanceSettingsMap.putMap(NOTIFICATIONS_SETTINGS_FIELD, notificationSettingsMap);

                mWritableMap.putMap(SURVEILLANCE_SETTINGS_FIELD, surveillanceSettingsMap);
                // ===
            } catch (JSONException e) {
                e.printStackTrace();
                mWritableMap = null;
            }

            return mWritableMap;
        }

        return null;
    }

    @Override
    public WritableArray toWritableArray() {
        return null;
    }

    @Override
    public String stringify() {
        if (mIsEmpty) {
            return "";
        }

        if (mInitializedFromStringifiedObject) {
            return mJSONObject.toString();
        }

        if (mInitializedFromReadableMap) {
            mJSONObject = new JSONObject();

            try {
                // ===
                ReadableMap surveillanceSettingsMap = mWritableMap.getMap(SURVEILLANCE_SETTINGS_FIELD);

                ReadableMap backCameraImageSettingsMap = surveillanceSettingsMap.getMap(BACK_CAMERA_IMAGE_SETTINGS_FIELD);
                String backCameraImageQuality = backCameraImageSettingsMap.getString(BACK_CAMERA_IMAGE_QUALITY_FIELD);;

                ReadableMap frontCameraImageSettingsMap = surveillanceSettingsMap.getMap(FRONT_CAMERA_IMAGE_QUALITY_SETTINGS_FIELD);
                String frontCameraImageQuality = frontCameraImageSettingsMap.getString(FRONT_CAMERA_IMAGE_QUALITY_FIELD);

                ReadableMap notificationSettingsMap = surveillanceSettingsMap.getMap(NOTIFICATIONS_SETTINGS_FIELD);
                boolean receiveNotificationsFromCurrentGroup = notificationSettingsMap.getBoolean(NOTIFICATIONS_SETTINGS_RECEIVE_NOTIFICATIONS_FROM_CURRENT_GROUP_FIELD);
                // ===

                // ===
                JSONObject surveillanceSettingsObject = new JSONObject();

                JSONObject backCameraImageSettingsObject = new JSONObject();
                backCameraImageSettingsObject.put(BACK_CAMERA_IMAGE_QUALITY_FIELD, backCameraImageQuality);

                surveillanceSettingsObject.put(BACK_CAMERA_IMAGE_SETTINGS_FIELD, backCameraImageSettingsObject);

                JSONObject frontCameraImageSettingsObject = new JSONObject();
                frontCameraImageSettingsObject.put(FRONT_CAMERA_IMAGE_QUALITY_FIELD, frontCameraImageQuality);

                surveillanceSettingsObject.put(FRONT_CAMERA_IMAGE_QUALITY_SETTINGS_FIELD, frontCameraImageSettingsObject);

                JSONObject notificationSettingsObject = new JSONObject();
                notificationSettingsObject.put(NOTIFICATIONS_SETTINGS_RECEIVE_NOTIFICATIONS_FROM_CURRENT_GROUP_FIELD, receiveNotificationsFromCurrentGroup);

                surveillanceSettingsObject.put(NOTIFICATIONS_SETTINGS_FIELD, notificationSettingsObject);

                mJSONObject.put(SURVEILLANCE_SETTINGS_FIELD, surveillanceSettingsObject);
                // ===
            } catch (JSONException e) {
                e.printStackTrace();
                mJSONObject = null;
            }

            if (mJSONObject != null) {
                return mJSONObject.toString();
            } else {
                return "";
            }
        }

        return "";
    }

    @Override
    public boolean isEmpty() {
        return mIsEmpty;
    }
}
