package com.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import com.e.salestracker.Activity.HomeActivity;
import com.e.salestracker.R;
import com.e.salestracker.Utility.ShareprefreancesUtility;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import androidx.core.app.NotificationCompat;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class FirebaseMessageService extends FirebaseMessagingService {
public static  int NOTIFICATION_ID=1;

@Override
public void onMessageReceived(RemoteMessage remoteMessage) {

    // TODO(developer): Handle FCM messages here.
    // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
    Log.d(TAG, "From: " + remoteMessage.getFrom());
    // Check if message contains a data payload.
    if (remoteMessage.getData().size() > 0) {
        Log.d(TAG, "Message data payload: " + remoteMessage.getData());

       String body=remoteMessage.getNotification().getBody();
       String title=remoteMessage.getNotification().getTitle();
       generateNotification(body,title);
    }

    // Check if message contains a notification payload.
    if (remoteMessage.getNotification() != null) {
        Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        String body=remoteMessage.getNotification().getBody();
        String title=remoteMessage.getNotification().getTitle();
        generateNotification(body,title);
    }

    // Also if you intend on generating your own notifications as a result of a received FCM
    // message, here is where that should be initiated. See sendNotification method below.
}
    private void generateNotification(String body, String title) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri sounduri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        String channelId = "Default";
        NotificationCompat.Builder builder = new  NotificationCompat.Builder(this, channelId)
                  .setSmallIcon(R.drawable.icon)
                  .setContentTitle(title)
                  .setContentText(body)
                  .setAutoCancel(true)
                  .setSound(sounduri)
                  .setContentIntent(pendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());
    }

    @Override
    public void onNewToken(String token) {
    try {
        ShareprefreancesUtility.getInstance().saveString("token", token);
        Log.d(TAG, "Refreshed token: " + token);
    }catch (Exception e){}
}

}
