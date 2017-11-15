package com.citymanage.push;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.citymanage.MainActivity;
import com.citymanage.R;
import com.citymanage.gm.GmInfoActivity;
import com.citymanage.sm.SmInfoActivity;
import com.citymanage.tm.TmInfoActivity;
import com.citymanage.wm.WmInfoActivity;
import com.google.firebase.messaging.RemoteMessage;

import static com.citymanage.push.PushHistoryActivity.SENSORID;

/**
 * Created by minjeongkim on 2017-10-15.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static final String TAG = "FirebaseMessagingService";

    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        sendNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("contents"));

    }

    private void sendNotification(String title, String contents) {

        Log.e("DEBUG ", title);
        Log.e("DEBUG ", contents);

        Intent intent = new Intent(this, MainActivity.class);

        Log.e("DEBUG " , String.valueOf(TextUtils.indexOf(contents,"T")));

        if(TextUtils.indexOf(contents,"T") ==0) {
            intent = new Intent(this, TmInfoActivity.class);
        }
        else if(TextUtils.indexOf(contents,"G") ==0) {
            intent = new Intent(this, GmInfoActivity.class);
        }
        else if(TextUtils.indexOf(contents,"W") ==0) {
            intent = new Intent(this, WmInfoActivity.class);
        }
        else if(TextUtils.indexOf(contents,"S") ==0) {
            intent = new Intent(this, SmInfoActivity.class);
        }

        intent.putExtra(SENSORID,contents);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.citymanage_img)
                //.setColor(00000000)
                .setContentTitle(title)
                .setContentText(contents)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }

}
