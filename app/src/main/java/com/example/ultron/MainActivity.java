package com.example.ultron;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import static com.example.ultron.App.CHANNEL_1_ID;
import static com.example.ultron.App.CHANNEL_2_ID;
import static com.example.ultron.App.CHANNEL_3_ID;

public class MainActivity extends AppCompatActivity {

    public  String loc;
    public  String los;
    public  String lpps;
    Button btn,on,off;
    Switch mode,alarm;
    public int m=0,k=0;
    public  int nco=0, nct=0;
    private NotificationManagerCompat notificationManager;

    private static final int MY_PERMISSION_REQUEST_RECIVE_SMS=0;
    private static final String SMS_RECEIVED ="android.provider.Telephony.SMS_RECEIVED";

///my receiver parts---------------------------------------------------------------------------------------------------------------------------------------------

    MyReceiver receiver = new MyReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);


            if(m==1)
            {

                if(p==1)
                {
                    sendOnChannel1();
                    p=0;
                }

                else {
                    sendOnChannel2();
                }
                if(eg==1)
                {
                    sendOnChannel3();
                    eg=0;
                }
            }
            loc=lat;
            los=lang;
            // Toast.makeText(MainActivity.this,loc+","+los,Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onPostResume() {
        super.onPostResume();
        registerReceiver(receiver,new IntentFilter(SMS_RECEIVED));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
///main activity----------------------------------------------------------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationManager = NotificationManagerCompat.from(MainActivity.this);


        //sms sending permission----------------------------------------------------------------------------------------------------------------------------------
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);

        off = findViewById(R.id.offbutton);
        mode = findViewById(R.id.switch1);
        btn = findViewById(R.id.map);
        alarm = findViewById(R.id.switch2);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED)//EITHER CHANGE

        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.RECEIVE_SMS))
            {

            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS},MY_PERMISSION_REQUEST_RECIVE_SMS);
            }
        }

        //for mode change-----------------------------------------------------------------------------------------------------------------------------------------
        mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){

                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage("7384190892",null,"pkon",null,null);
                    Toast.makeText(MainActivity.this,"System is activated !!",Toast.LENGTH_LONG).show();
                    m=1;


                }
                else{

                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage("7384190892",null,"pkoff",null,null);
                    m=0;
                }

            }
        });

        //for showing the location
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String url = "https://www.google.com/maps/search/?api=1&query="+los+","+loc;

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        //for engine off
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage("7384190892",null,"stop",null,null);
                Toast.makeText(MainActivity.this,"Engine has become off",Toast.LENGTH_LONG).show();



            }
        });


        //for engine on

        //-----//

        //for switching the alarm
        alarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){

                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage("7384190892",null,"buzon",null,null);
                    Toast.makeText(MainActivity.this,"Alarm is activated, try to follow the sound",Toast.LENGTH_LONG).show();


                }
                else{

                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage("7384190892",null,"buzstop",null,null);
                    Toast.makeText(MainActivity.this,"Alarm has become off",Toast.LENGTH_LONG).show();
                }
            }
        });

        //permission parts-------------------------------------------------------------------------------------------------------------------------------------------

    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case MY_PERMISSION_REQUEST_RECIVE_SMS:
            {
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {

                    Toast.makeText(this,"Thank you for permitting",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this,"well I can't do anything :(",Toast.LENGTH_LONG).show();
                }
            }
        }
    }



    //notification function part-----------------------------------------------------------------------------------------------------------------------------------


    public void sendOnChannel1()
    {

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_ps)
                .setContentTitle("Fuel risk !")
                .setContentText("Someone is trying to steal your fuel,try to find your vehicle")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(2,notification);

        Toast.makeText(this, "Someone is trying to steal your fuel", Toast.LENGTH_LONG).show();


    }
    public void sendOnChannel2()
    {

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_bike)
                .setContentTitle("Vehicle risk ! ")
                .setContentText("Someone is trying to steal your vehicle, try to find your vehicle")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1,notification);

        Toast.makeText(this, "Someone is trying to steal your vehicle", Toast.LENGTH_LONG).show();



    }
    public void sendOnChannel3()
    {

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_3_ID)
                .setSmallIcon(R.drawable.ic_eng)
                .setContentTitle("Engine has started ! ")
                .setContentText("Press the 'Engine off' button and find for your vehicle ")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(3,notification);

        Toast.makeText(this, "Engine has started !", Toast.LENGTH_LONG).show();



    }
}
