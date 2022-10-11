package com.bhanguz.lump;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bhanguz.lump.fragment.ChatFragment;
import com.bhanguz.lump.fragment.FanFragment;
import com.bhanguz.lump.fragment.HomeFragment;
import com.bhanguz.lump.fragment.ProfileFragment;
import com.bhanguz.lump.utilities.SavedSharedPreference;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView navView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, instanceIdResult -> {
            String newToken = instanceIdResult.getToken();
            Log.e("newToken", newToken);
        });

        requestCameraPermission();
        SavedSharedPreference.putKey(getApplicationContext(), "IsLogin", "true");

        navView=findViewById(R.id.bottom_bar);
        navView.setOnNavigationItemSelectedListener(this);
        loadFragment(new HomeFragment());
    }
    private void loadFragment(Fragment fragment) {
//// load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout, fragment,"frag 1");
        //  transaction.addToBackStack("frag 1");
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_home:

                loadFragment(new HomeFragment());
                break;
            case R.id.menu_favorites:
                loadFragment(new FanFragment());
                break;
            case R.id.menu_chat :
                loadFragment(new ChatFragment());

                break;

            case R.id.menu_profile:
               loadFragment(new ProfileFragment());
//                //loadFragment(new SettingFragment());
//                Fragment fragment = new SettingFragment();
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.container,fragment,"");
//                transaction.addToBackStack("frag 1");
//                transaction.commit();
        }


        return true;

    }

    private void requestCameraPermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // permission is granted

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


}