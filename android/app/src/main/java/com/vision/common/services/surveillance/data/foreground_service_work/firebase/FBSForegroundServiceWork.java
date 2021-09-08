package com.vision.common.services.surveillance.data.foreground_service_work.firebase;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.vision.common.services.firebase.FBSService;
import com.vision.common.services.firebase.data.FBSListenerId;
import com.vision.common.services.surveillance.data.foreground_service_work.ForegroundServiceWork;
import com.vision.common.services.surveillance.data.request.Request;
import com.vision.common.services.surveillance.data.request_handler.RequestHandler;

import java.util.Arrays;
import java.util.List;

public class FBSForegroundServiceWork implements ForegroundServiceWork {
    private FBSListenerId mRequestListenerId;
    private RequestHandler mRequestHandler;
    private List<String> mRequestPath;

    public FBSForegroundServiceWork(RequestHandler requestHandler, List<String> requestPath) {
        mRequestHandler = requestHandler;
        mRequestPath = requestPath;
    }

    @Override
    public void start() {
//        List<String> requestPathFields = Arrays.asList("emulatorTestField", "testSubfield", "REQUEST");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d("tag", "FBSForegroundServiceWork->Value is: " + value);
//
//                List<String> responsePathFields = Arrays.asList("emulatorTestField", "testSubfield", "RESPONSE");
//
//                FBSService.get().setStringValue(responsePathFields, value + "+");

                String stringifiedRequest = dataSnapshot.getValue(String.class);
                if (stringifiedRequest != null) {
                    Request request = new Request(stringifiedRequest);
                    mRequestHandler.handle(request);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("tag", "FBSForegroundServiceWork->Failed to read value.", error.toException());
            }
        };

        if (mRequestListenerId != null) {
            FBSService.get().removeListener(mRequestListenerId);
        }

        mRequestListenerId = FBSService.get().addListener(mRequestPath, listener);
    }

    @Override
    public void stop() {
        if (mRequestListenerId != null) {
            FBSService.get().removeListener(mRequestListenerId);
            mRequestListenerId = null;
        }
    }
}
