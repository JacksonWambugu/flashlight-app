package com.example.flashlightapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {
ImageButton imageButton;
boolean state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout linearLayout=(LinearLayout) findViewById(R.id.idll1);
        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        imageButton = findViewById(R.id.torchbutton);

        Dexter.withContext(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        runflashlight();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(MainActivity.this,"camera permission is required",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

            }
        }).check();
    }

    private void runflashlight() {
        imageButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if(state==false){
                    CameraManager cameraManager=(CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    try {
                        String cameraId= cameraManager.getCameraIdList()[0];
                        cameraManager.setTorchMode(cameraId,true);
                        state=true;
                        imageButton.setImageResource(R.drawable.poweroff);


                    }
                    catch (CameraAccessException e)
                    {

                    }
                }else {
                    CameraManager cameraManager=(CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    try {
                        String cameraid= cameraManager.getCameraIdList()[0];
                        cameraManager.setTorchMode(cameraid,false);
                        state=false
                        ;
                        imageButton.setImageResource(R.drawable.poweron);

                    }
                    catch (CameraAccessException e)
                    {

                    }
                }
            }
        });

    }
}