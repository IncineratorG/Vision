package com.vision.common.services.firebase;


import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vision.common.services.firebase.data.FBSListenerId;

import java.util.ArrayList;
import java.util.List;

public class FBSService {
    private static FBSService sInstance;

    private List<FBSListenerId> mListenerIds;

    private FBSService() {
        mListenerIds = new ArrayList<>();
    }

    public static synchronized FBSService get() {
        if (sInstance == null) {
            sInstance = new FBSService();
        }

        return sInstance;
    }

    public FBSListenerId addListener(@NonNull List<String> fields, @NonNull ValueEventListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference ref = database.getReference();
        for (int i = 0; i < fields.size(); ++i) {
            ref = ref.child(fields.get(i));
        }
        ref.addValueEventListener(listener);

        FBSListenerId id = new FBSListenerId(fields, listener);
        mListenerIds.add(id);

        return id;
    }

    public void removeListener(@NonNull FBSListenerId id) {
        List<String> fields = id.fieldPaths();
        ValueEventListener listener = id.listener();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference ref = database.getReference();
        for (int i = 0; i < fields.size(); ++i) {
            ref = ref.child(fields.get(i));
        }
        ref.removeEventListener(listener);
    }

    public void removeAllListeners() {
        for (int i = 0; i < mListenerIds.size(); ++i) {
            FBSListenerId id = mListenerIds.get(i);
            removeListener(id);
        }
    }

    public void setStringValue(@NonNull List<String> fields, @NonNull String value) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference ref = database.getReference();
        for (int i = 0; i < fields.size(); ++i) {
            ref = ref.child(fields.get(i));
        }

        ref.setValue(value);
    }
}

//    public void test() {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference requestFieldRef = database.getReference("REQUEST_FIELD");
//        DatabaseReference responseFieldRef = database.getReference("RESPONSE_FIELD");
//
//        requestFieldRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d("tag", "Value is: " + value);
//
//                responseFieldRef.setValue(value + "+");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // Failed to read value
//                Log.w("tag", "Failed to read value.", error.toException());
//            }
//        });
//    }
//
//    public void test2() {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference().child("field").child("subfield");
//
//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d("tag", "Value is: " + value);
//
//                ref.setValue(value + "+");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // Failed to read value
//                Log.w("tag", "Failed to read value.", error.toException());
//            }
//        });
//
//
//    }
