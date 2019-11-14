package com.wolfsoft.firebasenotification.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.wolfsoft.firebasenotification.MySharedPref;

public class FirebaseMessaging extends FirebaseMessagingService {

    private String user, icon, title, body;
    private String TAG = "FirebaseMessaging ";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String savedCurrentUser = MySharedPref.getInstance(this).getData("Current_USERID");

        user = remoteMessage.getData().get("user");
        icon = remoteMessage.getData().get("icon");
        title = remoteMessage.getData().get("title");
        body = remoteMessage.getData().get("body");

        if (!savedCurrentUser.equals(user)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                receivedOAndAboveNotification();
            } else {
                receivedNormalNotification();
            }
        }
    }

    private void receivedNormalNotification() {
        int i = Integer.parseInt(user.replaceAll("[\\D]", ""));
        Intent intent = new Intent(this, ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("hisUid", user);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(this, i, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defDoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "")
                .setSmallIcon(Integer.parseInt(icon))
                .setContentText(body)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setSound(defDoundUri)
                .setContentIntent(pIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Log.w(TAG, "i value " + i);
        int j = 0;
        if (i > 0) {
            j = i;
        }
        notificationManager.notify(j, builder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void receivedOAndAboveNotification() {
        int i = Integer.parseInt(user.replaceAll("[\\D]", ""));
        Intent intent = new Intent(this, ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("hisUid", user);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(this, i, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        OreoAndroidAboveNotification notification1 = new OreoAndroidAboveNotification(this);
        Notification.Builder builder = notification1.getNotifications(title, body, pIntent, defSoundUri, icon);

        int j = 0;
        if (i > 0) {
            j = i;
        }
        notification1.getManager().notify(j, builder.build());
    }
}
