package com.radioactivegeek.jatin.agent_spy;

import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {

  String fileName="aj";

    public VideoActivity(){

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        VideoView video  = (VideoView)findViewById(R.id.vid_view);
        fileName = ViewFiles.getInstance().newVideoPath;
        fileName+="/"+ViewFiles.getInstance().pos;
        video.setVideoPath(fileName);
        video.requestFocus();
        video.start();
    }
}
