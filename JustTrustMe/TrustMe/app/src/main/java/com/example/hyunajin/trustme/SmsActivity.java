package com.example.hyunajin.trustme;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SmsActivity extends AppCompatActivity {

    Button btnSend;
    EditText txtPhoneNo, txtMessage;
    String phoneNo, message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        btnSend=(Button)findViewById(R.id.btnSend);
        txtPhoneNo = (EditText)findViewById(R.id.txtPhoneNo);
        txtMessage = (EditText)findViewById(R.id.txtMessage);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNo = txtPhoneNo.getText().toString();
                message = txtMessage.getText().toString();
                if (phoneNo.length() >0 && message.length() >0){
                    sendSMS(phoneNo, message);
                }else {
                    Toast.makeText(getApplicationContext(),"전화번호와 메시지를 입력하세요.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendSMS(String phoneNo, String message) {
        String SENT = "SMS_SENT";
        String DELiVERED = "SMS_DELIVERED";
        PendingIntent sendPI = PendingIntent.getBroadcast(this,0,new Intent(SENT),0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELiVERED),0);

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(),"문자 발송이 되었습니다.",Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager
                            .RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(),"일반적인 오류가 발생",Toast.LENGTH_SHORT).show();
                    break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(),"서비스가 제공 안됨",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(),"문자가 발송됨",Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(),"문자 발송이 취소되었습니다.",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        },new IntentFilter(DELiVERED));
        SmsManager sms = SmsManager.getDefault();

        int check = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

        if (check != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
        }else sms.sendTextMessage(phoneNo, null, message, sendPI, deliveredPI);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if ((grantResults.length >0)&& (grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                    sendSMS(phoneNo,message);
                }
                break;

              default:
                  break;
        }
    }
}
