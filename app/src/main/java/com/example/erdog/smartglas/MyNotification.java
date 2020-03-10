package com.example.erdog.smartglas;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.annotation.RequiresApi;
import android.util.Log;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
@SuppressLint("OverrideAbstract")
public class MyNotification extends NotificationListenerService {
    public String title1="";
    public String text1="";
    public String package_name1="";
    Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
    @SuppressLint("LongLogTag")
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        // We can read notification while posted.
        for (StatusBarNotification sbm : MyNotification.this.getActiveNotifications()) {
            String title = sbm.getNotification().extras.getString("android.title");
            String text = sbm.getNotification().extras.getString("android.text");
            String package_name = sbm.getPackageName();
            title1=title;
            text1=text1;
            package_name1=package_name;
            Log.v("Notification title is:", title);
            Log.v("Notification text is:", text);
            Log.v("Notification Package Name is:", package_name);
        }
    }
}