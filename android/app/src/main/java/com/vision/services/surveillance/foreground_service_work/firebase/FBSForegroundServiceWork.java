package com.vision.services.surveillance.foreground_service_work.firebase;


import android.content.Context;

import com.vision.common.interfaces.service_communication_manager.ServiceCommunicationManager;
import com.vision.common.interfaces.foreground_service_work.ForegroundServiceWork;

public class FBSForegroundServiceWork implements ForegroundServiceWork {
    private ServiceCommunicationManager mCommunicationManager;

    public FBSForegroundServiceWork(ServiceCommunicationManager communicationManager) {
        mCommunicationManager = communicationManager;
    }

    @Override
    public void start(Context context) {
        mCommunicationManager.startRequestsListener(context);
    }

    @Override
    public void stop(Context context) {
        if (mCommunicationManager != null) {
            mCommunicationManager.stopRequestsListener(context);
        }
    }
}


//package com.vision.services.surveillance.foreground_service_work.firebase;
//
//
//import android.content.Context;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import com.google.firebase.database.ChildEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.vision.services.firebase.FBSService;
//import com.vision.services.firebase.data.FBSListenerId;
//import com.vision.common.interfaces.foregroun_service_work.ForegroundServiceWork;
//import com.vision.common.interfaces.service_requests_handler.ServiceRequestsHandler;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class FBSForegroundServiceWork implements ForegroundServiceWork {
//    private FBSListenerId mRequestListenerId;
//    private ServiceRequestsHandler mRequestsHandler;
//    private List<String> mRequestPath;
//    private Map<String, Object> mRequestParams;
//
//    public FBSForegroundServiceWork(ServiceRequestsHandler requestsHandler,
//                                    List<String> requestPath) {
//        mRequestsHandler = requestsHandler;
//        mRequestPath = requestPath;
//
//        mRequestParams = new HashMap<>();
//    }
//
//    @Override
//    public void start(Context context) {
//        FBSService.get().setStringValue(mRequestPath, null);
//
//        ChildEventListener listener = new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                String key = snapshot.getKey();
//                String value = snapshot.getValue(String.class);
//
//                if (key == null) {
//                    Log.d("tag", "FBSForegroundServiceWork->KEY_IS_NULL");
//                    return;
//                }
//
//                if (value == null) {
//                    Log.d("tag", "FBSForegroundServiceWork->VALUE_IS_NULL");
//                    return;
//                }
//
//                mRequestParams.clear();
//                mRequestParams.put("requestKey", key);
//
//                mRequestsHandler.handle(context, value, mRequestParams);
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        };
//
//        if (mRequestListenerId != null) {
//            FBSService.get().removeListener(mRequestListenerId);
//        }
//
//        mRequestListenerId = FBSService.get().addListener(mRequestPath, listener);
//    }
//
////    @Override
////    public void start(Context context) {
////        ValueEventListener listener = new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                Log.d("tag", "FBSForegroundServiceWork->onDataChanged(): " + dataSnapshot.getChildrenCount());
////
////                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
////                    String value = childSnapshot.getValue(String.class);
////                    Log.d("tag", "VALUE: " + value);
////                }
////
//////                mRequestsExecutor.execute(context, dataSnapshot.getValue(String.class));
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError error) {
////                Log.w("tag", "FBSForegroundServiceWork->Failed to read value.", error.toException());
////            }
////        };
////
////        if (mRequestListenerId != null) {
////            FBSService.get().removeListener(mRequestListenerId);
////        }
////
////        mRequestListenerId = FBSService.get().addListener(mRequestPath, listener);
////    }
//
//    @Override
//    public void stop(Context context) {
//        if (mRequestListenerId != null) {
//            FBSService.get().removeListener(mRequestListenerId);
//            mRequestListenerId = null;
//        }
//    }
//}
