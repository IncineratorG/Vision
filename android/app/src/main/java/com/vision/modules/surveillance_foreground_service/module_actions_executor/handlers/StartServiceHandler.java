package com.vision.modules.surveillance_foreground_service.module_actions_executor.handlers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.vision.common.services.firebase.FirebaseService;
import com.vision.common.services.surveillance.SurveillanceService;
import com.vision.modules.modules_common.interfaces.js_action_handler.JSActionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StartServiceHandler implements JSActionHandler {
    @Override
    public void handle(ReactApplicationContext context, ReadableMap action, Promise result) {
        Log.d("tag", "StartServiceHandler");

        SurveillanceService.get().startForegroundService(context);

//        List<String> requestPathFields = Arrays.asList("deviceTestField", "testSubfield", "REQUEST");
//        ValueEventListener listener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d("tag", "StartServiceHandler->Value is: " + value);
//
//                List<String> responsePathFields = Arrays.asList("deviceTestField", "testSubfield", "RESPONSE");
//
//                FirebaseService.get().setStringValue(responsePathFields, value + "+");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // Failed to read value
//                Log.w("tag", "StartServiceHandler->Failed to read value.", error.toException());
//            }
//        };
//
//        FirebaseService.get().addListener(requestPathFields, listener);

        result.resolve(true);
    }
}
