package com.vision.android_services.foreground.surveillance;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.vision.MainActivity;
import com.vision.R;
import com.vision.services.surveillance.SurveillanceService;

public class SurveillanceForegroundService extends Service {
    public static final String CHANNEL_ID = "ForegroundServiceChannel";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        if (intent.getAction().contains("start")) {
//            h = new Handler();
//            r = new Runnable() {
//                @Override
//                public void run() {
//                    startForeground(101, updateNotification());
//                    h.postDelayed(this, 1000);
//                }
//            };
//
//            h.post(r);
//        } else {
//            h.removeCallbacks(r);
//            stopForeground(true);
//            stopSelf();
//        }

        // ===
        // =====
//        if (intent.getAction().contains("start")) {
//            List<String> requestPathFields = Arrays.asList("emulatorTestField", "testSubfield", "REQUEST");
//            ValueEventListener listener = new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    // This method is called once with the initial value and again
//                    // whenever data at this location is updated.
//                    String value = dataSnapshot.getValue(String.class);
//                    Log.d("tag", "StartServiceHandler->Value is: " + value);
//
//                    List<String> responsePathFields = Arrays.asList("emulatorTestField", "testSubfield", "RESPONSE");
//
//                    FBSService.get().setStringValue(responsePathFields, value + "+");
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    // Failed to read value
//                    Log.w("tag", "SurveillanceForegroundService->Failed to read value.", error.toException());
//                }
//            };
//
//            FBSService.get().addListener(requestPathFields, listener);
//
//            startForeground(101, updateNotification());
//        } else {
//            FBSService.get().removeAllListeners();
//
//            stopForeground(true);
//            stopSelf();
//        }
        // =====
        // ===
        if (intent == null || intent.getAction() == null) {
//            SurveillanceService.get().foregroundServiceWork().stop(this);

            stopForeground(true);
            stopSelf();
        } else if (intent.getAction().contains("start")) {
            SurveillanceService.get().foregroundServiceWork().start(this);

            startForeground(101, updateNotification());
        } else {
            SurveillanceService.get().foregroundServiceWork().stop(this);

            stopForeground(true);
            stopSelf();
        }

        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    private Handler h;
    private Runnable r;
    int counter = 0;

    private Notification updateNotification() {
//        counter++;
//        String info = counter + "";
        String info = "Service running";

        Context context = getApplicationContext();

        PendingIntent action = PendingIntent.getActivity(context,
                0, new Intent(context, MainActivity.class),
                PendingIntent.FLAG_CANCEL_CURRENT); // Flag indicating that if the described PendingIntent already exists, the current one should be canceled before generating a new one.

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            String CHANNEL_ID = "alex_channel";

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "AlexChannel",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Alex channel description");
            manager.createNotificationChannel(channel);

            builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        }
        else {
            builder = new NotificationCompat.Builder(context);
        }

        return builder.setContentIntent(action)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentTitle(info)
                .setTicker(info)
                .setContentText(info)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark_normal)
                .setContentIntent(action)
                .setOngoing(true).build();
    }
}

//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        String input = intent.getStringExtra("inputExtra");
//        createNotificationChannel();
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,
//                0, notificationIntent, 0);
//        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setContentTitle("Foreground Service")
//                .setContentText("input")
//                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
//                .setContentIntent(pendingIntent)
//                .build();
//        startForeground(1, notification);
//        //do heavy work on a background thread
//        //stopSelf();
//        return START_STICKY;
//    }
