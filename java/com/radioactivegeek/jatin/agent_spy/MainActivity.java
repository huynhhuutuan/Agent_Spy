package com.radioactivegeek.jatin.agent_spy;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private String instructions[] = {"Step 1 :  \nOpen Dialer.\n", "Step 2 :  \nUse code *#*#5#*#* to start recording video secretly.\n", "Step 3 :  \nUse the same code again to stop spying.\n", "Step 4 :  \nOpen File Manager to play recorded videos.\n"};
    private boolean showDisclaimer;
    private final static String DISCLAIMER = "Agent-Spy comes with absolutely no warranty." +
            "\n" +
            "You can use this software at your own risk. \nThis developer is not responsible for any illegal usage of this software." +
            "\n" +
            "Always think about what you are doing !" +
            "\n\n" +
            "BY USING THIS , YOU AGREED TO UNDERSTAND AND ACCEPT THE DISCLAIMER.";
    public volatile boolean recording = true;
    public static MainActivity mainActivity;
    private int REQUEST_CAMERA = 1001;
    boolean permisssionGranted = false;

    public static MainActivity getInstance() {
        return mainActivity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mainActivity = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = mainActivity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(mainActivity.getResources().getColor(android.R.color.black));
        }
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        showDisclaimer = pref.getBoolean("disclaimer", false);
        if (!showDisclaimer) {
            editor.putBoolean("disclaimer", true);
            editor.commit();
            final Dialog dialog = new Dialog(this);
            dialog.setTitle("Disclaimer");

            dialog.setContentView(R.layout.dialog_layout);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            TextView tvDisclaimer = (TextView) dialog.findViewById(R.id.tvDialog);

            tvDisclaimer.setText(DISCLAIMER);
            Button btnDialog = (Button) dialog.findViewById(R.id.bDialog);
            btnDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.hide();

                }
            });
            dialog.show();


        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, ViewFiles.class);
        startActivity(intent);
    }
}
