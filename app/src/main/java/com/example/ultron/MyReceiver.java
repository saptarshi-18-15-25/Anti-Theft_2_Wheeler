package com.example.ultron;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

//import static com.example.sooooo.App.CHANNEL_2_ID;

public class MyReceiver extends BroadcastReceiver {

    private static final String SMS_RECEIVED ="android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SmsBroadcastReceiver";
    String msg,lat,lang,vat,vang;
    public int p=0,q=0,eg=0;
    private NotificationManagerCompat notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i(TAG,"Intent Received:  "+intent.getAction());
        notificationManager = NotificationManagerCompat.from(context);
        if(intent.getAction()== SMS_RECEIVED)
        {

            Bundle dataBundle = intent.getExtras();
            if (dataBundle != null) {

                Object[] mypdu = (Object[]) dataBundle.get("pdus");
                final SmsMessage[] message = new SmsMessage[mypdu.length];

                for (int i = 0; i < mypdu.length; i++) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        String format = dataBundle.getString("format");
                        message[i] = SmsMessage.createFromPdu((byte[]) mypdu[i], format);
                    } else {
                        message[i] = SmsMessage.createFromPdu((byte[]) mypdu[i]);
                    }
                    msg = message[i].getMessageBody();

                }
                String[] separated = msg.split(",");
                lat = separated[0];
                lang = separated[1];

                vang=lang;

                if(vang.equals("a"))
                {
                    p=1;
                }
                if(vang.equals("b"))
                {
                    eg=1;
                }




              Uri.parse("geo:" + lat + "" + lang);

            }

        }

    }
}
