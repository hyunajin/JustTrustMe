package com.example.asus.justtrustme;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.kakao.auth.ErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;

public class KakaoSignupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       requestMe();
    }

    private void requestMe() {
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg="+ errorResult;
                Logger.d(message);

                ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                if (result == ErrorCode.CLIENT_ERROR_CODE){
                    finish();
                }else{
                    redirectLoginActivity();
                }
            }
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                redirectLoginActivity();
            }


            private void redirectLoginActivity() {
            }

            @Override
            public void onNotSignedUp() {

            }

            @Override
            public void onSuccess(UserProfile userProfile) {
                String kakaoID = String.valueOf(userProfile.getId());
                String kakaoNickname = userProfile.getNickname();
                String url = String.valueOf(userProfile.getProfileImagePath());

                Logger.d("UserProfile :" + userProfile);
                Log.d("kakao", "==========================");
                Log.d("kakao", ""+userProfile);
                Log.d("kakao", kakaoID);
                Log.d("kakao", kakaoNickname);
                Log.d("kakao", "==========================");
                redirectMainActivity(url, kakaoNickname); // 로그인 성공시 MainActivity로
            }
        });
    }

    private void redirectMainActivity(String url, String nickname) {
        Intent intent = new Intent(KakaoSignupActivity.this, Home.class);
        intent.putExtra("url", url);
        intent.putExtra("nickname", nickname);
        startActivity(intent);
        finish();
    }
    protected void redirectLoginActivity() {
        final Intent intent = new Intent(this, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

}



