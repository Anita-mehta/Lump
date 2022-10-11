package com.bhanguz.lump.firebaseMessagingNotification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.bhanguz.lump.MainActivity;
import com.bhanguz.lump.R;
import com.bhanguz.lump.activity.ChatActivity;
import com.bhanguz.lump.utilities.SavedSharedPreference;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class MyFirebaseMessagingService  extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";


    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.e(TAG, "Refreshed token: " + s);
        callstatus();
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        //  callstatus();
        String title = "";
        if (remoteMessage.getNotification().getTitle() != null) {
            title = remoteMessage.getNotification().getTitle();
        }

        String message = "";
        if (remoteMessage.getNotification().getBody() != null) {
            message = remoteMessage.getNotification().getBody();
        }

        Log.e("value of fcm","DAta###########################"+remoteMessage.getData());
        //get activity
        if (remoteMessage.getData().containsKey("activity")) {
            String activity = remoteMessage.getData().get("activity");
            if (activity.equalsIgnoreCase("ChatActivity")) {

                String room_id = remoteMessage.getData().get("room_id");

                String video_id = remoteMessage.getData().get("video_id");
                //String client_name = remoteMessage.getData().get("image_user");


                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                intent.putExtra("room_id", room_id);
                intent.putExtra("video_id", video_id);
               // intent.putExtra("client_name", client_name);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }

        }

//       // get room id=12345 random
//       if(remoteMessage.getData().containsKey("room_id")){
//            String room_id = remoteMessage.getData().get("room_id");
//
//        }
//        //get who start call
//        if(remoteMessage.getData().containsKey("client_id")){
//            String client_id = remoteMessage.getData().get("client_id");
//        }


        Log.e("notification", "recieved");


        sendNotification(title, message);


    }


    private void sendNotification(String title, String message) {

        Intent intent = new Intent(this, MainActivity.class);


        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_group_1)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                        R.drawable.applogo1))

                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(getRequestCode(), notificationBuilder.build());
    }


    private static int getRequestCode() {
        Random rnd = new Random();
        return 100 + rnd.nextInt(900000);
    }

    public void callstatus(){
        String call = SavedSharedPreference.putKey(getApplicationContext(), "IsOnCall", "1");

        if(call.equalsIgnoreCase("1")){
            Toast.makeText(this, "callstart", Toast.LENGTH_LONG).show();

        }else {


        }
    }

}



