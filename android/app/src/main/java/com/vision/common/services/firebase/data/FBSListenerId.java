package com.vision.common.services.firebase.data;


import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FBSListenerId {
    private List<String> mFieldPaths;
    private ValueEventListener mListener;

    public FBSListenerId(List<String> fieldPaths, ValueEventListener listener) {
        mFieldPaths = fieldPaths;
        mListener = listener;
    }

    public List<String> fieldPaths() {
        return mFieldPaths;
    }

    public ValueEventListener listener() {
        return mListener;
    }
}
