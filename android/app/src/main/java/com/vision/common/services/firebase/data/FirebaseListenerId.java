package com.vision.common.services.firebase.data;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FirebaseListenerId {
    private List<String> mFieldPaths;
    private ValueEventListener mListener;

    public FirebaseListenerId(List<String> fieldPaths, ValueEventListener listener) {
        mFieldPaths = fieldPaths;
        mListener = listener;
    }

    public List<String> getFieldPaths() {
        return mFieldPaths;
    }

    public ValueEventListener getListener() {
        return mListener;
    }
}
