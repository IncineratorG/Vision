package com.vision.common.data.hybrid_objects.authentication_data;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.vision.rn_modules.modules_common.interfaces.hybrid_object.HybridObject;

import org.json.JSONException;
import org.json.JSONObject;

public class AuthenticationData implements HybridObject {
    public static final String GROUP_NAME_FIELD = "groupName";
    public static final String GROUP_PASSWORD_FIELD = "groupPassword";
    public static final String DEVICE_NAME_FIELD = "deviceName";

    private WritableMap mWritableMap;
    private JSONObject mJSONObject;

    private boolean mIsEmpty;
    private boolean mInitializedFromReadableMap;
    private boolean mInitializedFromStringifiedObject;

    public AuthenticationData(String groupName, String groupPassword, String deviceName) {
        mJSONObject = new JSONObject();
        try {
            mJSONObject.put(GROUP_NAME_FIELD, groupName);
            mJSONObject.put(GROUP_PASSWORD_FIELD, groupPassword);
            mJSONObject.put(DEVICE_NAME_FIELD, deviceName);

            mInitializedFromStringifiedObject = true;
            mIsEmpty = false;
        } catch (JSONException e) {
            e.printStackTrace();
            mIsEmpty = true;
        }
    }

    public AuthenticationData(ReadableMap readableMap) {
        if (readableMap == null) {
            mIsEmpty = true;
            return;
        }

        mWritableMap = new WritableNativeMap();
        mWritableMap.merge(readableMap);

        mInitializedFromReadableMap = true;
        mIsEmpty = false;
    }

    public AuthenticationData(String stringifiedObject) {
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

    public AuthenticationData(AuthenticationData other) {
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

            try {
                String groupName = mJSONObject.getString(GROUP_NAME_FIELD);
                String groupPassword = mJSONObject.getString(GROUP_PASSWORD_FIELD);
                String deviceName = mJSONObject.getString(DEVICE_NAME_FIELD);

                mWritableMap.putString(GROUP_NAME_FIELD, groupName);
                mWritableMap.putString(GROUP_PASSWORD_FIELD, groupPassword);
                mWritableMap.putString(DEVICE_NAME_FIELD, deviceName);
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
                String groupName = mWritableMap.getString(GROUP_NAME_FIELD);
                String groupPassword = mWritableMap.getString(GROUP_PASSWORD_FIELD);
                String deviceName = mWritableMap.getString(DEVICE_NAME_FIELD);

                mJSONObject.put(GROUP_NAME_FIELD, groupName);
                mJSONObject.put(GROUP_PASSWORD_FIELD, groupPassword);
                mJSONObject.put(DEVICE_NAME_FIELD, deviceName);
            } catch (JSONException e) {
                e.printStackTrace();
                mJSONObject = null;
            } catch (NullPointerException e) {
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

    public String groupName() {
        String groupName = "";
        try {
            if (mInitializedFromReadableMap) {
                groupName = mWritableMap.getString(GROUP_NAME_FIELD);
            } else {
                try {
                    groupName = mJSONObject.getString(GROUP_NAME_FIELD);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return groupName;
    }

    public String groupPassword() {
        String groupPassword = "";
        try {
            if (mInitializedFromReadableMap) {
                groupPassword = mWritableMap.getString(GROUP_PASSWORD_FIELD);
            } else {
                try {
                    groupPassword = mJSONObject.getString(GROUP_PASSWORD_FIELD);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return groupPassword;
    }

    public String deviceName() {
        String deviceName = "";
        try {
            if (mInitializedFromReadableMap) {
                deviceName = mWritableMap.getString(DEVICE_NAME_FIELD);
            } else {
                try {
                    deviceName = mJSONObject.getString(DEVICE_NAME_FIELD);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return deviceName;
    }
}
