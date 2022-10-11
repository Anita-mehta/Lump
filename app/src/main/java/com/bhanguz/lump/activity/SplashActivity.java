package com.bhanguz.lump.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import com.bhanguz.lump.MainActivity;
import com.bhanguz.lump.R;
import com.bhanguz.lump.utilities.SavedSharedPreference;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends BaseActivity {
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        printHashKey(getApplicationContext());
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (SavedSharedPreference.getKey(SplashActivity.this, "IsLogin").equals("")) {
                    Intent intent;
                    intent = new Intent(SplashActivity.this, LoginMainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, 1800);
    }

    protected void initListener() {

    }

    public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.d("TAG", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("TAG", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("TAG", "printHashKey()", e);
        }
    }

}


