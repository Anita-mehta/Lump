package com.bhanguz.lump.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bhanguz.lump.MainActivity;
import com.bhanguz.lump.R;
import com.bhanguz.lump.model.ModelLogin;
import com.bhanguz.lump.utilities.ApiUrl;
import com.bhanguz.lump.utilities.SavedSharedPreference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OtpTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifiedOtp extends AppCompatActivity {
    private String mVerificationId;
    private String newToken = "";
    String mobile;
    String userId = "", newUser = "";
  // OtpTextView
           EditText editTextCode;
    private DatabaseReference databaseReference;
    FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private String phoneno, otp;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;


    //firebase auth object
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verified_otp);
        mAuth = FirebaseAuth.getInstance();
        editTextCode = findViewById(R.id.editww);
        //getting mobile number from the previous activity
        //and sending the verification code to the number
        Intent intent = getIntent();
         mobile = intent.getStringExtra("mobile");

        //        FirebaseAuth.getInstance().getFirebaseAuthSettings()
//                .setAppVerificationDisabledForTesting(true);

        sendVerificationCode(mobile);


        //if the automatic sms detection did not work, user can also enter the code manually
        //so adding a click listener to the button
        findViewById(R.id.continue_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = editTextCode.getText().toString().trim();

               // email1=email.getText().toString().trim();
                //pass=password.getText().toString().trim();
                //registerWithEmailandPassword(email1,pass);
               
                if (code.isEmpty() || code.length() < 6) {
                    editTextCode.setError("Enter valid code");
                    editTextCode.requestFocus();
                    return;
                }

                //verifying the code entered manually
                verifyVerificationCode(code);
            }
        });

    }

    //the method is sending verification code
    //the country id is concatenated
    //you can take the country id as user input as well
    private void sendVerificationCode(String mobile) {
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                "+91" + mobile,
//                60,
//                TimeUnit.SECONDS,
//              VerifiedOtp.this,
//                mCallbacks);

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91" + mobile)       // Phone number to verify
                        .setTimeout(6L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);



    }


    //the callback to detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                editTextCode.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerifiedOtp.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };


    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerifiedOtp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profile activity

                            getLogin();
                            //Log.d("TAG", "signInWithCredential:success");

                            //FirebaseUser user_id = task.getResult().getUser();
                            //Log.d("TAG","onComplete:+ user_id");

                        } else {

                            //verification unsuccessful.. display an error message

                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }

                        }
                    }
                });
    }
    private void getLogin() {
       // mloaderLayout1.setVisibility(View.VISIBLE);
        String notification_token1=  newToken;
        Log.e("TAG","getlogin: Phone: "+ notification_token1);
        ApiUrl.getAllClient().getLogin(mobile,
                "bhanguz", "012d%lump%4071",notification_token1)
                .enqueue(new Callback<ModelLogin>() {
                    @Override
                    public void onResponse(Call<ModelLogin> call, Response<ModelLogin> response) {
                        //  mloaderLayout1.setVisibility(View.GONE);
                        String error = response.body().getNewUser();
                        if (error.equalsIgnoreCase("1")) {

                            userId = response.body().getUserId();
                            newUser = response.body().getNewUser();

                            Intent intent = new Intent(VerifiedOtp.this, RegisterActivity.class);
                            intent.putExtra("userId", userId);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            //  otp_tv3.setVisibility(View.VISIBLE);
                            //next.setVisibility(View.VISIBLE);
                            // send_otp.setVisibility(View.GONE);
                            // otp_et.setVisibility(View.VISIBLE);
                            //otp.setText(response.body().getOtp());

                        } else {
                            userId = response.body().getUserId();
                            SavedSharedPreference.putKey(getApplicationContext(), "KEY_user_id", userId);
                            Intent intent = new Intent(VerifiedOtp.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onFailure(Call<ModelLogin> call, Throwable t) {
                       // mloaderLayout1.setVisibility(View.GONE);
                        Toast.makeText(VerifiedOtp.this, "Failed", Toast.LENGTH_SHORT).show();

                    }
                });
    }

}

