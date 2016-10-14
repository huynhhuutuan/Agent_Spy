package com.radioactivegeek.jatin.agent_spy;

/**
 * Created by Jatin on 28-Sep-16.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

/**
 * Created by Jatin on 05-Aug-16.
 */

public class OutgoingCallReceiver extends BroadcastReceiver {
    public MainActivity mainActivity;

    private void myProcessing(Context context, Intent intent) {


        if (mainActivity.getInstance().recording) {
            mainActivity.getInstance().recording = false;
            // start recording
            if (intent.getAction().equals("android.provider.Telephony.SECRET_CODE")) {
                Intent i = new Intent(context, BackgroundVideoRecorder.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startService(i);
                Toast.makeText(context, "Spying...", Toast.LENGTH_LONG).show();
            }
        } else {
            mainActivity.getInstance().recording = true;
            // stop recording
            Intent i = new Intent(context, BackgroundVideoRecorder.class);
            context.stopService(i);
            Toast.makeText(context, "Mission Accomplished !", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (intent.getAction().equals("android.provider.Telephony.SECRET_CODE"))) {
//                myProcessing(context, intent);
                Toast.makeText(context, "Currently Unavailabe for Marshmallow", Toast.LENGTH_SHORT).show();
            } else
                myProcessing(context, intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}



