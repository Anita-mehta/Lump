package com.bhanguz.lump.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bhanguz.lump.BuildConfig;
import com.bhanguz.lump.MainActivity;
import com.bhanguz.lump.R;
import com.bhanguz.lump.model.SocialModel;
import com.bhanguz.lump.utilities.ApiUrl;
import com.bhanguz.lump.utilities.SavedSharedPreference;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginMainActivity extends BaseActivity {
    Button phone,btnFacebook;
    private static final String TAG = "FacebookLogin";
    private static final int RC_SIGN_IN = 12345;
    private CallbackManager mCallbackManager;
     FirebaseAuth mAuth;
     FrameLayout mloaderLayout1;
    LoginButton loginfacebookbtn;
    private String newToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        FirebaseApp.initializeApp(getApplicationContext());

        mAuth = FirebaseAuth.getInstance();
        phone = findViewById(R.id.phone);

        mCallbackManager = CallbackManager.Factory.create();

        loginfacebookbtn = findViewById(R.id.facebook);
        mloaderLayout1=findViewById(R.id.mloaderLayout);
        btnFacebook=findViewById(R.id.btnFacebook);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, instanceIdResult -> {
            newToken = instanceIdResult.getToken();
            Log.e("newToken", newToken);
        });



        //loginfacebookbtn.setReadPermissions("email", "public_profile");
        loginfacebookbtn.setReadPermissions("email","public_Profile");
        loginfacebookbtn.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {

            }

        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginMainActivity.this,PhoneOtpActivity.class);
                startActivity(intent);

            }
        });

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fbLogin();
            }
        });

    }


    private void fbLogin(){

        if (BuildConfig.DEBUG){
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email","public_profile","user_photos"));
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();

        // Checking if the user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            Log.d(TAG, "Currently Signed in: " + currentUser.getEmail());
            Toast.makeText(LoginMainActivity.this, "Currently Logged in: " + currentUser.getEmail(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginMainActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token.getUserId());
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, UI will update with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            sociallogin(user.getEmail(), "Facebook", token.getUserId());

                        } else {
                            // If sign-in fails, a message will display to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginMainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void sociallogin(String mailaddress, String type, String token_id) {
           mloaderLayout1.setVisibility(View.VISIBLE);
        String fcmToken = getPreferences(Context.MODE_PRIVATE).getString("fb", "empty :(");
        String notification_token1=  newToken;
        Log.e(TAG, "sociallogin: "+ notification_token1 );
        ApiUrl.getAllClient().getlogin(getString(R.string.token), mailaddress, getString(R.string.uname), type, token_id,notification_token1).enqueue(new Callback<SocialModel>() {
            @Override
            public void onResponse(Call<SocialModel> call, Response<SocialModel> response) {
                mloaderLayout1.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body().getError().equalsIgnoreCase("1")) {

                        if (response.body().getNew_user().equals("1")) {
                            Intent intent = new Intent(LoginMainActivity.this,RegisterActivity.class);
                            intent.putExtra("userId", response.body().getUser_id());
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            SavedSharedPreference.putKey(getApplicationContext(), "KEY_user_id", response.body().getUser_id());
                            Intent intent = new Intent(LoginMainActivity.this, MainActivity.class);
                            startActivity(intent);
                            finishAffinity();

                        }

                    } else {

                        Toast.makeText(getApplicationContext(), response.body().getError_msg(), Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(getApplicationContext(), "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<SocialModel> call, Throwable t) {
                mloaderLayout1.setVisibility(View.GONE);
            }
        });
    }


}
