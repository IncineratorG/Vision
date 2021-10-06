package com.vision.common.data.service_generic_callbacks;


public interface OnTaskError<T> {
    void onError(T data);
}
