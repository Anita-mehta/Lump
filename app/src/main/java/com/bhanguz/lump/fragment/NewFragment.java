package com.bhanguz.lump.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bhanguz.lump.MainActivity;
import com.bhanguz.lump.R;
import com.bhanguz.lump.adapter.TinderCard;
import com.bhanguz.lump.model.ModelDiscoverNew;
import com.bhanguz.lump.model.ModelRequestStatus;
import com.bhanguz.lump.model.Profile;
import com.bhanguz.lump.utilities.ApiUrl;
import com.bhanguz.lump.utilities.SavedSharedPreference;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewFragment extends Fragment {
    private SwipePlaceHolderView mSwipeView;
    private Context mContext;
    TextView country;
    TextView noFriendTxt;
    ImageView image;
    ImageButton rejectBtn1,acceptBtn;
    static ArrayList<Profile> profiles;
    static JsonArray listStringarr;
    FrameLayout mloaderLayout1;
    CardView mainLayout;
    //TextView detail1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new, container, false);
//       detail1=view.findViewById(R.id.detail);
//       detail1.setOnClickListener(new View.OnClickListener() {
//            @Override
//          public void onClick(View v) {
//             Bottomsheet bottomsheet= new Bottomsheet();
//             bottomsheet.show(getFragmentManager(),Bottomsheet.class.getName());
//           }
//       });


        image = view.findViewById(R.id.cardimage);
        country = view.findViewById(R.id.userlocationnew);
        mSwipeView = view.findViewById(R.id.swipeView);
        acceptBtn = view.findViewById(R.id.acceptBtn1);
        rejectBtn1 = view.findViewById(R.id.rejectBtn1);
        mloaderLayout1= view.findViewById(R.id.mloaderLayout);
        mainLayout= view.findViewById(R.id.mainLayout);
        noFriendTxt= view.findViewById(R.id.noFriendTxt);

        mContext = getActivity();
        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.tinder_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.tinder_swipe_out_msg_view));
       getDiscover();


        view.findViewById(R.id.rejectBtn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(false);
            }
        });

        view.findViewById(R.id.acceptBtn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(true);
            }
        });
        return view;
    }

    private void getDiscover() {
        mloaderLayout1.setVisibility(View.VISIBLE);
        String uname = "bhanguz";
        String token = "012d%lump%4071";
        final String username = "username";
        String user_id = SavedSharedPreference.getKey(getActivity(), "KEY_user_id");

        Log.e("username: ", user_id);
        profiles = new ArrayList<>();

        ApiUrl.getAllClient().getDiscover(user_id, uname, token).enqueue(new Callback<ModelDiscoverNew>() {
            @Override
            public void onResponse(Call<ModelDiscoverNew> call, Response<ModelDiscoverNew> response) {
                 mloaderLayout1.setVisibility(View.GONE);
                if (response.isSuccessful() && null != response.body()) {
                    profiles.addAll(response.body().getData());
                    //ArrayList to JSON Array
                    Gson gson = new Gson();
                    listStringarr = gson.toJsonTree(profiles).getAsJsonArray();

                    List<Profile> profileList = loadProfiles(getActivity());

                    if (profileList.size() > 0) {
                        mSwipeView.setVisibility(View.VISIBLE);
                        mainLayout.setVisibility(View.VISIBLE);
                        noFriendTxt.setVisibility(View.GONE);
                        for (Profile profile : profileList) {

                            mSwipeView.addView(new TinderCard(mContext, profile, mSwipeView));
                        }

                    } else {
                        mSwipeView.setVisibility(View.GONE);
                        mainLayout.setVisibility(View.GONE);
                        noFriendTxt.setVisibility(View.VISIBLE);
                    }



                } else {
                    Toast.makeText(getContext(), "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelDiscoverNew> call, Throwable t) {
                mloaderLayout1.setVisibility(View.GONE);
                Log.e("onFailure: ", t.toString());
            }
        });
    }


    public static List<Profile> loadProfiles(Context context) {
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            //  JSONArray array = new JSONArray(loadJSONFromAsset(context, "profiles.json"));
            List<Profile> profileList = new ArrayList<>();
            for (int i = 0; i < listStringarr.size(); i++) {
                Profile profile = gson.fromJson(listStringarr.get(i), Profile.class);
              //  if(profile.getUserId()!= null) {
                    if (!profile.getUserId().isEmpty()) {
                        profileList.add(profile);
                    }

                }

          //  }
            return profileList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void doAccept(String friend_id, String status) {
mloaderLayout1.setVisibility(View.VISIBLE);
        String uname = "bhanguz";
        String token = "012d%lump%4071";
        String username = "username";


        String client_id = SavedSharedPreference.getKey(mContext, "KEY_client_id");

        Log.e("username: ", client_id);

        ApiUrl.getAllClient().getStatus(client_id, friend_id, status, uname, token).enqueue(new Callback<ModelRequestStatus>() {
            @Override
            public void onResponse(Call<ModelRequestStatus> call, Response<ModelRequestStatus> response) {
            mloaderLayout1.setVisibility(View.GONE);

                String error = response.body().error;
                if (error.equalsIgnoreCase("1")) {

                } else {
                    Toast.makeText(mContext, response.body().error_msg, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ModelRequestStatus> call, Throwable t) {
                mloaderLayout1.setVisibility(View.GONE);
                Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();

            }
        });

    }
}