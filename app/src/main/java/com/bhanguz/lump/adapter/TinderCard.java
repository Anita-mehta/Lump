package com.bhanguz.lump.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.bhanguz.lump.R;
import com.bhanguz.lump.model.ModelRequestStatus;
import com.bhanguz.lump.model.Profile;
import com.bhanguz.lump.utilities.ApiUrl;
import com.bhanguz.lump.utilities.SavedSharedPreference;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

@Layout(R.layout.tindercardlayout)
public class TinderCard {
    private CardView cardView;
    @View(R.id.cardimage)
    private ImageView cardimagenew;

    @SuppressLint("NonConstantResourceId")
    @View(R.id.usernamenew)
    private TextView nameAgeTxt;

    @View(R.id.userlocationnew)
    private TextView locationNameTxt;

    private Profile mProfile;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;

    public TinderCard(Context context, Profile profile, SwipePlaceHolderView swipeView) {
        mContext = context;
        mProfile = profile;
        mSwipeView = swipeView;
    }

    @Resolve
    private void onResolved() {
        Log.d("TAG", "onResolved: "+ mProfile.getImage());
        if (mProfile.getGender().equalsIgnoreCase("Female")) {
            Picasso.get().load("http://103.43.153.37:8060/img/" + mProfile.getImage()).placeholder(R.drawable.ic_woman).into(cardimagenew);
        } else {
            Picasso.get().load("http://103.43.153.37:8060/img/" + mProfile.getImage()).placeholder(R.drawable.ic_boy).into(cardimagenew);
        }

        locationNameTxt.setText(mProfile.getCountry());
        nameAgeTxt.setText(mProfile.getUsername());
    }


    @SwipeOut
    private void onSwipedOut() {
//        doReject(mProfile.getUserId());
        doAccept(mProfile.getUserId(), "Rejected");
        Log.d("EVENT", "reject");
        mSwipeView.addView(this);
    }

    @SwipeCancelState
    private void onSwipeCancelState() {

        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn() {
        doAccept(mProfile.getUserId(), "Accepted");
        mSwipeView.addView(this);
        Log.d("EVENT", "accept");
    }

    @SwipeInState
    private void onSwipeInState()
    {
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState() {
        Log.d("EVENT", "onSwipeOutState");
    }


    //======================retrofit classes for accept reject user===========================

    public void doAccept(String friend_id, String status) {

        String uname = "bhanguz";
        String token = "012d%lump%4071";
        String username = "username";


        String client_id = SavedSharedPreference.getKey(mContext, "KEY_user_id");

        Log.e("username: ", client_id);

        ApiUrl.getAllClient().getStatus(client_id, friend_id, status, uname, token).enqueue(new Callback<ModelRequestStatus>() {
            @Override
            public void onResponse(Call<ModelRequestStatus> call, Response<ModelRequestStatus> response) {


                String error = response.body().error;
                if (error.equalsIgnoreCase("1")) {

                    //Picasso.get().load(getApplicationContext().getString(R.string.img_url)+response.body().getI()).into(cardimagenew);


                } else {
                    Toast.makeText(mContext, response.body().error_msg, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ModelRequestStatus> call, Throwable t) {
                Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();

            }
        });
    }
}




