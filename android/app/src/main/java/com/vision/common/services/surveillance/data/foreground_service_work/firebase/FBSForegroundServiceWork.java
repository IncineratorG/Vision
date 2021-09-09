package com.vision.common.services.surveillance.data.foreground_service_work.firebase;


import android.content.Context;
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
import com.vision.common.services.surveillance.data.requests_executor.RequestsExecutor;

import java.util.Arrays;
import java.util.List;

public class FBSForegroundServiceWork implements ForegroundServiceWork {
    private FBSListenerId mRequestListenerId;
    private RequestsExecutor mRequestsExecutor;
    private List<String> mRequestPath;

    public FBSForegroundServiceWork(RequestsExecutor requestsExecutor,
                                    List<String> requestPath) {
        mRequestsExecutor = requestsExecutor;
        mRequestPath = requestPath;
    }

    @Override
    public void start(Context context) {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mRequestsExecutor.execute(context, dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("tag", "FBSForegroundServiceWork->Failed to read value.", error.toException());
            }
        };

        if (mRequestListenerId != null) {
            FBSService.get().removeListener(mRequestListenerId);
        }

        mRequestListenerId = FBSService.get().addListener(mRequestPath, listener);
    }

    @Override
    public void stop(Context context) {
        if (mRequestListenerId != null) {
            FBSService.get().removeListener(mRequestListenerId);
            mRequestListenerId = null;
        }
    }
}
