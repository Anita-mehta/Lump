package com.bhanguz.lump.activity;

import androidx.annotation.Nullable;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bhanguz.lump.R;
import com.bhanguz.lump.model.ProfileResponse;
import com.bhanguz.lump.model.SendcallModel;
import com.bhanguz.lump.utilities.ApiUrl;
import com.bhanguz.lump.utilities.SavedSharedPreference;
import com.squareup.picasso.Picasso;



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FrienddetailActivity extends BaseActivity {
    LinearLayout backlayout1;
    RelativeLayout callBtn1, chatBtn1;
    TextView userfriendname1, fanabout1, fanage1, fanlocation1;
    ImageView userfriendpic1;
    String usernameusername1, friend_id, user_id1, roomid, clientid, video_id;
    String pic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frienddetail);
        backlayout1 = findViewById(R.id.backlayout);
        chatBtn1 = findViewById(R.id.chatBtn);
        userfriendname1 = findViewById(R.id.userfriendname);
        fanabout1 = findViewById(R.id.fanabout);
        fanage1 = findViewById(R.id.age_txt);
        fanlocation1 = findViewById(R.id.fan_locationtxt);
        userfriendpic1 = findViewById(R.id.userfriendpic);

        Intent intent = getIntent();
        roomid = intent.getStringExtra("room_id");
        clientid = intent.getStringExtra("client_id");
        //testtttttttttttttttt cut callllllllllllll
        video_id = intent.getStringExtra("video_id");
        Log.d("TAG", "onCreate:" + video_id);
        Log.d("TAG", "onCreate: " + roomid);
        SavedSharedPreference.getKey(FrienddetailActivity.this, "user_id");

        backlayout1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onBackPressed();

            }
        });


        chatBtn1.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent i = new Intent(FrienddetailActivity.this, ChatActivity.class);
                i.putExtra("username", usernameusername1);
                i.putExtra("client_id", SavedSharedPreference.getKey(getApplicationContext(), "KEY_user_id"));
                i.putExtra("friend_id", friend_id);
                i.putExtra("image", pic);
                startActivity(i);

            }
        });
        if (getIntent().getExtras() != null) {
            String uname1 = getIntent().getStringExtra("username");
            userfriendname1.setText(uname1);
           usernameusername1 = getIntent().getStringExtra("username");


            friend_id = getIntent().getStringExtra("user_id");
            pic = getIntent().getStringExtra("image");
            Picasso.get().load(ApiUrl.BASE_url_image + pic).placeholder(R.drawable.placeholder).into(userfriendpic1);

        }
        FrienddetailApi();
    }

    private void FrienddetailApi() {
        String uname = "bhanguz";
        String token = "012d%lump%4071";
        String userid1 = getIntent().getStringExtra("user_id");
        SavedSharedPreference.getKey(getApplicationContext(), "KEY_user_id");

        ApiUrl.getAllClient().getProfile(uname, token, userid1).enqueue(new Callback<ProfileResponse>() {

            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful()) {

                    // user_id1= getIntent().getStringExtra("user_id");
                    userfriendname1.setText(response.body().getUsername());
                    fanlocation1.setText(response.body().getCountry());
                    fanabout1.setText(response.body().getAbout());
                    fanage1.setText(response.body().getAge());
                    pic = response.body().getProfile();
                    Picasso.get().load(getApplicationContext().getString(R.string.img_url) + response.body().getProfile()).placeholder(R.drawable.placeholder).into(userfriendpic1);

                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {

            }
        });
    }

}

