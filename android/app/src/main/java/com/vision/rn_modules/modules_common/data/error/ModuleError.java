package com.vision.rn_modules.modules_common.data.error;


public class ModuleError {
    private String mCode;
    private String mMessage;

    public ModuleError(String code, String message) {
        mCode = code;
        mMessage = message;
    }

    public String code() {
        return mCode;
    }

    public String message() {
        return mMessage;
    }
}