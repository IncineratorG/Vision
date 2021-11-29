package com.vision.rn_modules.modules_common.interfaces.hybrid_object;


import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

public interface HybridObject {
    WritableMap toWritableMap();
    WritableArray toWritableArray();
    String stringify();
    boolean isEmpty();
}
