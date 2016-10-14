package com.radioactivegeek.jatin.agent_spy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.net.URLConnection;
import java.util.ArrayList;

public class ViewFiles extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {


    static ViewFiles viewFiles;
    ListView listView;
    String newVideoPath;
    String pos;
    public static ViewFiles getInstance(){
        return viewFiles;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_files);

        try {
            viewFiles=this;
            String root = Environment.getExternalStorageDirectory().toString();
            String myDir = root + "/A_spy";
            File dir = new File(myDir);

            File[] filelist = dir.listFiles();
            String[] theNamesOfFiles = new String[filelist.length];

            for (int i = 0; i < theNamesOfFiles.length; i++) {
                theNamesOfFiles[i] = filelist[i].getName();
            }

            listView = (ListView)findViewById(R.id.lv_VideoFiles);
            ArrayAdapter<String> videoAdapter =  new ArrayAdapter<String>(this,  android.R.layout.simple_expandable_list_item_1, theNamesOfFiles);
            listView.setAdapter(videoAdapter);

            listView.setOnItemClickListener(this);
            listView.setOnItemLongClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        String root = Environment.getExternalStorageDirectory().toString();
        newVideoPath = root + "/A_spy";
       pos = (String)   listView.getItemAtPosition(position);

        File videoFile = new File(newVideoPath);
        if(videoFile.exists()){

            VideoActivity videoActivity = new VideoActivity();
            Intent intent  = new Intent(this,VideoActivity.class);
            startActivity(intent);
        }


    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }
}
