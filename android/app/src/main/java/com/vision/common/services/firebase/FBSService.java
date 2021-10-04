package com.vision.common.services.firebase;


import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vision.common.services.firebase.data.FBSListenerId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public FBSListenerId addListener(List<String> fields, ValueEventListener listener) {
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

    public FBSListenerId addListener(List<String> fields, ChildEventListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference ref = database.getReference();
        for (int i = 0; i < fields.size(); ++i) {
            ref = ref.child(fields.get(i));
        }
        ref.addChildEventListener(listener);

        FBSListenerId id = new FBSListenerId(fields, listener);
        mListenerIds.add(id);

        return id;
    }

    public void getValue(List<String> fields, ValueEventListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference ref = database.getReference();
        for (int i = 0; i < fields.size(); ++i) {
            ref = ref.child(fields.get(i));
        }
        ref.addListenerForSingleValueEvent(listener);
    }

    public void removeListener(FBSListenerId id) {
        List<String> fields = id.fieldPaths();
        ValueEventListener valueEventListener = id.valueEventListener();
        ChildEventListener childEventListener = id.childEventListener();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference ref = database.getReference();
        for (int i = 0; i < fields.size(); ++i) {
            ref = ref.child(fields.get(i));
        }

        if (valueEventListener != null) {
            ref.removeEventListener(valueEventListener);
        }
        if (childEventListener != null) {
            ref.removeEventListener(childEventListener);
        }
    }

    public void removeAllListeners() {
        for (int i = 0; i < mListenerIds.size(); ++i) {
            FBSListenerId id = mListenerIds.get(i);
            removeListener(id);
        }
    }

    public void setStringValue(List<String> fields, String value) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference ref = database.getReference();
        for (int i = 0; i < fields.size(); ++i) {
            ref = ref.child(fields.get(i));
        }

        ref.setValue(value);
    }

    public void setStringValue(List<String> fields,
                               String value,
                               OnCompleteListener<Void> onCompleteListener,
                               OnFailureListener onFailureListener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference ref = database.getReference();
        for (int i = 0; i < fields.size(); ++i) {
            ref = ref.child(fields.get(i));
        }

        ref.setValue(value)
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(onFailureListener);
    }

    public void setMapValue(List<String> fields, Map<String, Object> value) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference ref = database.getReference();
        for (int i = 0; i < fields.size(); ++i) {
            ref = ref.child(fields.get(i));
        }

        ref.updateChildren(value);
    }

    public void setMapValue(List<String> fields,
                            Map<String, Object> value,
                            OnCompleteListener<Void> onCompleteListener,
                            OnFailureListener onFailureListener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference ref = database.getReference();
        for (int i = 0; i < fields.size(); ++i) {
            ref = ref.child(fields.get(i));
        }

        ref.updateChildren(value)
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(onFailureListener);
    }

    public void addValueToList(List<String> fields,
                               String value) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference ref = database.getReference();
        for (int i = 0; i < fields.size(); ++i) {
            ref = ref.child(fields.get(i));
        }

        String key = ref.push().getKey();
        Log.d("tag", "KEY: " + key);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key, value);

        ref.updateChildren(childUpdates);
    }

    public void addValueToList(List<String> fields,
                               String value,
                               OnCompleteListener<Void> onCompleteListener,
                               OnFailureListener onFailureListener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference ref = database.getReference();
        for (int i = 0; i < fields.size(); ++i) {
            ref = ref.child(fields.get(i));
        }

        String key = ref.push().getKey();
        Log.d("tag", "KEY: " + key);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key, value);

        ref.updateChildren(childUpdates)
                .addOnCompleteListener(onCompleteListener)
                .addOnFailureListener(onFailureListener);
    }

    public void removeValueFromList(List<String> fields, String key) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference ref = database.getReference();
        for (int i = 0; i < fields.size(); ++i) {
            ref = ref.child(fields.get(i));
        }

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key, null);

        ref.updateChildren(childUpdates);
    }

    // ===
//    public void test(List<String> fields, Map<String, Object> map) {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//
//        DatabaseReference ref = database.getReference();
//        for (int i = 0; i < fields.size(); ++i) {
//            ref = ref.child(fields.get(i));
//        }
//
//        ref.updateChildren(map);
//    }
//
//    public void test2(List<String> fields, ValueEventListener listener) {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//
//        DatabaseReference ref = database.getReference();
//        for (int i = 0; i < fields.size(); ++i) {
//            ref = ref.child(fields.get(i));
//        }
//        ref.addValueEventListener(listener);
//    }
    // ===
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
