package kr.sofac.goodtns.util.googleFirebaseService;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import kr.sofac.goodtns.R;
import kr.sofac.goodtns.dto.PushMessage;
import kr.sofac.goodtns.view.SplashActivity;
import timber.log.Timber;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

import kr.sofac.goodtns.Constants;


public class MyFirebaseMessagingService extends FirebaseMessagingService {


    NotificationCompat.Builder builder;
    NotificationManager mNotificationManager;
    String pushMessageType = "";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        pushMessageType = remoteMessage.getData().get("type");

        buildNotificationToShow(remoteMessage.getData().get("message"), remoteMessage.getData().get("date"), remoteMessage.getData().get("title"));

        PushMessage newPushMessage = new PushMessage(remoteMessage.getData().get("title"), remoteMessage.getData().get("message"), remoteMessage.getData().get("date"));
        newPushMessage.save();
    }

    private void buildNotificationToShow(String messageText, String date, String title) {

        Intent notificationIntent = null;
        notificationIntent = new Intent(this, SplashActivity.class);

        mNotificationManager = (NotificationManager) this
                .getSystemService(this.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(title);
        builder.setContentText(Html.fromHtml(messageText).toString())
                .setSmallIcon(R.drawable.account)
                //.setStyle(bigPictureStyle)
                .setAutoCancel(true)
                .setContentIntent(
                        PendingIntent.getActivity(this, 10,
                                notificationIntent, 0));

        Uri alarmSound = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);

        mNotificationManager.notify((int) (Math.random() * 100000), builder.build());

    }

}