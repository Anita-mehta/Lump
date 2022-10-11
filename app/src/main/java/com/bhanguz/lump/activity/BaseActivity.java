package com.bhanguz.lump.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.bhanguz.lump.R;
import com.bhanguz.lump.model.Online0Model;
import com.bhanguz.lump.utilities.ApiUrl;
import com.bhanguz.lump.utilities.SavedSharedPreference;
import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        showOnlineApi1("1");

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, instanceIdResult -> {
           String newToken = instanceIdResult.getToken();
            Log.e("newToken", newToken);
        });
    }
        @Override
        public void onResume() {
            super.onResume();

          //  isOnline(true);
        }



    @Override
    public void onPause() {
        super.onPause();
        //showOnlineApi1("0");
       // isOnline(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//       showOnlineApi1("0");
    }

    private void showOnlineApi1(String status) {

        String token1="012d%lump%4071";
        String uname1="bhanguz";
        String user_id1= SavedSharedPreference.getKey(BaseActivity.this,"KEY_user_id");
        String status1 ="status";
        Log.d("TAG", "showOnlineBroad: "+user_id1);
        ApiUrl.getAllClient().getOnline(token1,uname1,user_id1,status).enqueue(new Callback<Online0Model>() {

            @Override
            public void onResponse(Call<Online0Model> call, Response<Online0Model> response) {
                // dialog.dismiss();
                if (response.isSuccessful() && null != response.body().getError()) {

                }

            }
            public void onFailure(Call<Online0Model> call, Throwable t) {
                Log.d("TAG", "onFailure: "+t.toString());
            }
        });

    }

    }
