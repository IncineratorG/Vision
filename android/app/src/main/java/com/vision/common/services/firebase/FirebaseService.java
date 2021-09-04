package com.vision.common.services.firebase;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FirebaseService {
    private static FirebaseService sInstance;

    private FirebaseService() {

    }

    public static synchronized FirebaseService get() {
        if (sInstance == null) {
            sInstance = new FirebaseService();
        }

        return sInstance;
    }

    public void test() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference requestFieldRef = database.getReference("REQUEST_FIELD");
        DatabaseReference responseFieldRef = database.getReference("RESPONSE_FIELD");

        requestFieldRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("tag", "Value is: " + value);

                responseFieldRef.setValue(value + "+");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("tag", "Failed to read value.", error.toException());
            }
        });
    }

    public void test2() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("field").child("subfield");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("tag", "Value is: " + value);

                ref.setValue(value + "+");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("tag", "Failed to read value.", error.toException());
            }
        });


    }

    public void addListener(@NonNull List<String> fields, @NonNull ValueEventListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference ref = database.getReference();
        for (int i = 0; i < fields.size(); ++i) {
            ref = ref.child(fields.get(i));
        }

        ref.addListenerForSingleValueEvent(listener);
    }
}
