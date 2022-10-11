package com.bhanguz.lump.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bhanguz.lump.MainActivity;
import com.bhanguz.lump.R;
import com.bhanguz.lump.model.ModelLogin;
import com.bhanguz.lump.model.ModelOtp;
import com.bhanguz.lump.utilities.ApiUrl;
import com.bhanguz.lump.utilities.SavedSharedPreference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneOtpActivity extends BaseActivity {
    Button send_otp;
    ImageView back;
    FrameLayout mloaderLayout1;
    // CustomProgressDialog dialog;
    EditText mobile_number;
    private String newToken = "";
    TextView otp_tv3, otp, otp_et;
    String phonenumber;
    FirebaseAuth auth;
    private String verificationCode;
    String userId = "", newUser = "";

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_otp);
        mobile_number = findViewById(R.id.mobile_number);
        send_otp = findViewById(R.id.sendotp);
//        otp_tv3 = findViewById(R.id.otp_tv3);
//        otp = findViewById(R.id.otp);
//        back = findViewById(R.id.back);
//        next = findViewById(R.id.next);
        mloaderLayout1 = findViewById(R.id.mloaderLayout);
        // otp_et = findViewById(R.id.otp_et);

        //   StartFirebaseLogin();//call



        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, instanceIdResult -> {
            newToken = instanceIdResult.getToken();
            Log.e("newToken", newToken);
        });



        //sendotp
        send_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobile = mobile_number.getText().toString().trim();

                if (mobile.isEmpty() || mobile.length() < 10) {
                    mobile_number.setError("Enter a valid mobile");
                    mobile_number.requestFocus();
                    return;
                }

                Intent intent = new Intent(PhoneOtpActivity.this, VerifiedOtp.class);
                intent.putExtra("mobile", mobile);
                startActivity(intent);
            }
        });
    }


//
//        send_otp.setOnClickListener(new View.OnClickListener() {//
//            @Override
//            public void onClick(View v) {
//               String phoneNumber=mobile_number.getText().toString();
//                StartFirebaseLogin();
//                PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                       "+91"+phoneNumber,                     // Phone number to verify
//                        60,                           // Timeout duration
//                        TimeUnit.SECONDS,                // Unit of timeout
//                        PhoneOtpActivity.this,        // Activity (for callback binding)
//                        mCallback);                      // OnVerificationStateChangedCallbacks
//            }
//
//
//        });
//
////        send_otp.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                validation();
////
////            }
////        });
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                finish();
//            }
//        });
//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //getOtp();
//            }
//        });
//
//
//
//        /////
//
//
//
//
//    }

    private void getLogin() {
        mloaderLayout1.setVisibility(View.VISIBLE);
        String notification_token1=  newToken;
        Log.e("TAG","getlogin: Phone: "+ notification_token1);
        ApiUrl.getAllClient().getLogin(mobile_number.getText().toString(),
                "bhanguz", "012d%lump%4071",notification_token1)
                .enqueue(new Callback<ModelLogin>() {
                    @Override
                    public void onResponse(Call<ModelLogin> call, Response<ModelLogin> response) {
                        mloaderLayout1.setVisibility(View.GONE);
                        String error = response.body().getError();
                        if (error.equalsIgnoreCase("1")) {

                            userId = response.body().getUserId();
                            newUser = response.body().getNewUser();

                          //  otp_tv3.setVisibility(View.VISIBLE);
                            //next.setVisibility(View.VISIBLE);
                           // send_otp.setVisibility(View.GONE);
                           // otp_et.setVisibility(View.VISIBLE);
                            //otp.setText(response.body().getOtp());

                        } else {
                            Toast.makeText(PhoneOtpActivity.this, "Please enter mobile number", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<ModelLogin> call, Throwable t) {
                     mloaderLayout1.setVisibility(View.GONE);
                        Toast.makeText(PhoneOtpActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                    }
                });
    }

//    private void getOtp() {
//       mloaderLayout1.setVisibility(View.VISIBLE);
//        String uname = "bhanguz";
//        String token = "012d%lump%4071";
//        String otp1 = otp_et.getText().toString();
//        String notification_token1=  newToken;
//         Log.e("TAG" , "getotp: "+ notification_token1 );
//        String mobile = mobile_number.getText().toString();
//        ApiUrl.getAllClient().getOtp(mobile, otp1, uname, token,notification_token1).enqueue(new Callback<ModelOtp>() {
//            @Override
//            public void onResponse(Call<ModelOtp> call, Response<ModelOtp> response) {
//             mloaderLayout1.setVisibility(View.GONE);
//                if (response.isSuccessful()) {
//                    if (response.body().getError().equalsIgnoreCase("1")) {
//
//                        if (newUser.equals("1")) {
//                            Intent intent = new Intent(PhoneOtpActivity.this, RegisterActivity.class);
//                            intent.putExtra("userId", userId);
//                            startActivity(intent);
//                            finishAffinity();
//                        } else {
//                            SavedSharedPreference.putKey(getApplicationContext(), "KEY_user_id", userId);
//                            Intent intent = new Intent(PhoneOtpActivity.this, MainActivity.class);
//                            startActivity(intent);
//                        }
//
//
//                    } else {
//                        Toast.makeText(PhoneOtpActivity.this, response.body().getErrorMsg(), Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(PhoneOtpActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ModelOtp> call, Throwable t) {
//               mloaderLayout1.setVisibility(View.GONE);
//                Toast.makeText(PhoneOtpActivity.this, "Failed", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//
//    }
//
//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//
//    }

//    private void validation() {
//        if (mobile_number.getText().toString().isEmpty()) {
//            mobile_number.setError("Enter phone number ");
//            Toast.makeText(getApplicationContext(), "enter phone number", Toast.LENGTH_SHORT).show();
//        } else if (mobile_number.getText().toString().length()!= 10) {
//            Toast.makeText(getApplicationContext(), "invalid number", Toast.LENGTH_SHORT).show();
//        } else {
//            phonenumber = mobile_number.getText().toString();
//            //getLogin();
//        }
//
//    }
}