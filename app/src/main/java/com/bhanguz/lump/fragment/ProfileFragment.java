package com.bhanguz.lump.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bhanguz.lump.R;
import com.bhanguz.lump.activity.Editprofileactivity;
import com.bhanguz.lump.activity.FrienddetailActivity;
import com.bhanguz.lump.activity.FullScreenActivity;
import com.bhanguz.lump.activity.SplashActivity;
import com.bhanguz.lump.adapter.FanAdapter;
import com.bhanguz.lump.model.ModelFav;
import com.bhanguz.lump.model.ProfileResponse;
import com.bhanguz.lump.utilities.ApiUrl;
import com.bhanguz.lump.utilities.SavedSharedPreference;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;


public class ProfileFragment extends Fragment {
    TextView personal_info1,profilename1,profilelocation;
    ImageView profileimage1;
    ImageView editpen1;
    FrameLayout mloaderLayout;
    TextView note, logout1;
    String IMAGE_DIRECTORY;
    LinearLayout backbtn;
    FrameLayout mloaderLayout1;
    private MultipartBody.Part image_port = null;
    private File doc_1;
    int PICK_FROM_GALLERY = 2;
    int MY_CAMERA = 10, CAMERA_REQUEST = 20;
    private String docOneImage = "image";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        personal_info1 = view.findViewById(R.id.personal_info);
        profileimage1=view.findViewById(R.id.profileimage);
        profilename1=view.findViewById(R.id.profilename);
        mloaderLayout=view.findViewById(R.id.mloaderLayout);
        profilelocation=view.findViewById(R.id.profilelocation);
        logout1 = view.findViewById(R.id.logout);
        editpen1 =view.findViewById(R.id.editpen);
        profileimage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FullScreenActivity.class);

                startActivity(intent);
            }
        });
        editpen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FullScreenActivity.class);
                startActivity(intent);
            }
        });

        personal_info1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Editprofileactivity.class);
                startActivity(intent);
            }
        });
        logout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.app_name);
                builder.setIcon(R.drawable.logouttlogo);
                builder.setMessage(" Are you sure,you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SavedSharedPreference.removeclear(getApplicationContext());
                                FirebaseAuth.getInstance().signOut();
                                LoginManager.getInstance().logOut();
                                Intent intent = new Intent(getContext(), SplashActivity.class);
                                startActivity(intent);
                                getActivity().finishAffinity();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }

        });
        ProfileApi();
        return view;

    }
    private void ProfileApi() {
        mloaderLayout.setVisibility(View.VISIBLE);
        String uname="bhanguz";
        String token="012d%lump%4071";
        String user_id= SavedSharedPreference.getKey(getActivity(),"KEY_user_id");

        ApiUrl.getAllClient().getProfile(uname,token,user_id).enqueue(new Callback<ProfileResponse>() {

            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                mloaderLayout.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    profilename1.setText(response.body().getUsername());
                    profilelocation.setText(response.body().getCountry());
                    Picasso.get().load(getApplicationContext().getString(R.string.img_url)+response.body().getProfile()).placeholder(R.drawable.placeholder).into(profileimage1);

                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                mloaderLayout.setVisibility(View.GONE);

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ProfileApi();
    }


}