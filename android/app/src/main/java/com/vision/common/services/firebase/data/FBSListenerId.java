package com.vision.common.services.firebase.data;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FBSListenerId {
    private List<String> mFieldPaths;
    private ValueEventListener mValueEventListenerListener;
    private ChildEventListener mChildEventListener;

    public FBSListenerId(List<String> fieldPaths, ValueEventListener listener) {
        mFieldPaths = fieldPaths;
        mValueEventListenerListener = listener;
    }

    public FBSListenerId(List<String> fieldPaths, ChildEventListener listener) {
        mFieldPaths = fieldPaths;
        mChildEventListener = listener;
    }

    public List<String> fieldPaths() {
        return mFieldPaths;
    }

    public ValueEventListener valueEventListener() {
        return mValueEventListenerListener;
    }

    public ChildEventListener childEventListener() {
        return mChildEventListener;
    }
}
