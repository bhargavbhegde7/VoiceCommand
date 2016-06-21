package com.socket.net.voicecommand;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

public class Main2Activity extends AppCompatActivity {

    public static int SENDING = 1;
    public static int CONNECTING = 2;
    public static int ERROR = 3;
    public static int SENT = 4;
    public static int SHUTDOWN = 5;

    public String TAG = "Main2Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Handler handler = new Handler(){
            public void handleMessage(Message msg){
//                switch(msg.what){
//                    case 1 :
//                        Log.d(TAG, "In Handler's shutdown");
//
//                        System.out.println("Main2Activity : handling SHUTDOWN");
//
//                        break;
//                }//switch
                System.out.println(msg);
            }//handleMessage
        };

        new AsynchConnectionTask(handler, "Hakuna Matata...!!").execute();
    }
}
