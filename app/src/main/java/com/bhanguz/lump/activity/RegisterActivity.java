package com.bhanguz.lump.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Range;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.bhanguz.lump.MainActivity;
import com.bhanguz.lump.R;
import com.bhanguz.lump.model.ModelRegister;
import com.bhanguz.lump.utilities.ApiUrl;
import com.bhanguz.lump.utilities.SavedSharedPreference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;

    String[] gender_spinner;
    String gender_str = "";
    RadioButton male, female, others;
    EditText username, age, edtBio;
    Button Register;
    ImageView userImage;
    RadioGroup radioGrp;

    FrameLayout mloaderLayout1;
    String usernamee, agee, edtBioo;
    // CustomProgressDialog dialog;
    String userId, picturePath = "";
    ImageView viewImage;
    String IMAGE_DIRECTORY;
    private MultipartBody.Part image_port = null;
    private File doc_1;
    int PICK_FROM_GALLERY = 2;
    int MY_CAMERA = 10, CAMERA_REQUEST = 20;
    private String docOneImage = "image";
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    private static int PICK_IMAGE = 100;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Register = findViewById(R.id.register);
        spinner = findViewById(R.id.spinner);
        age = findViewById(R.id.age_et);

        edtBio = findViewById(R.id.edtBio);
        mloaderLayout1 = findViewById(R.id.mloaderLayout);
        username = findViewById(R.id.username_et);
        radioGrp = findViewById(R.id.radioGrp);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        others = findViewById(R.id.others);
        userImage = findViewById(R.id.userImage);
        // dialog=new CustomProgressDialog(this);
        gender_spinner = new String[]{"Male", "Female", "Gay", "Lesbian"};

        userId = getIntent().getStringExtra("userId");
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, gender_spinner);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);


        radioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.male) {
                    gender_str = "Male";
                } else if (checkedId == R.id.female) {
                    gender_str = "Female";
                } else {
                    gender_str = "Others";
                }
            }
        });

//        male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                male.setChecked(true);
//                female.setChecked(false);
//                others.setChecked(false);
//                gender_str = "Male";
//            }
//        });
//        female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                male.setChecked(false);
//                female.setChecked(true);
//                others.setChecked(false);
//                gender_str = "Female";
//            }
//        });
//        others.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                male.setChecked(false);
//                female.setChecked(false);
//                others.setChecked(true);
//                gender_str = "Other";
//            }
//        });

        //  spinner.setOnItemSelectedListener(this);
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();

            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validation();

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void UpdateProfileApi() {
        mloaderLayout1.setVisibility(View.VISIBLE);


        int age1 = Integer.parseInt(age.getText().toString());
        if (age1 < 18) {
            Toast.makeText(getApplicationContext(), "Not vaild age", Toast.LENGTH_SHORT).show();
            mloaderLayout1.setVisibility(View.GONE          );
            return;
        }


        Log.d("TAG", "UpdateProfileApi: "+ age1);


        String spinner1 = spinner.getSelectedItem().toString();
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        requestBodyMap.put("Content-Type", RequestBody.create(MediaType.parse("multipart/form-data"), "application/x-www-form-urlencoded"));
        Log.d("TAG", MessageFormat.format("uploadprofile: {0}", requestBodyMap));
        RequestBody uname = RequestBody.create(MediaType.parse("text/plain"), "bhanguz");
        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), "012d%lump%4071");
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), username.getText().toString());
        RequestBody userIdBody = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody genderBody = RequestBody.create(MediaType.parse("text/plain"), gender_str);
        RequestBody ageBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(age1));
        RequestBody interestBody = RequestBody.create(MediaType.parse("text/plain"), spinner1);
        RequestBody edtBioBody = RequestBody.create(MediaType.parse("text/plain"), edtBio.getText().toString());

        ApiUrl.getAllClient().getRegister(uname, token, userIdBody, name, ageBody, genderBody, interestBody, edtBioBody, image_port)
                .enqueue(new Callback<ModelRegister>() {
                    @Override
                    public void onResponse(Call<ModelRegister> call, Response<ModelRegister> response) {
                        mloaderLayout1.setVisibility(View.GONE);

                        if (response.isSuccessful() && null != response.body()) {
                            ModelRegister loginResponse = response.body();

                            if (loginResponse.getError().equals("1")) {
                                SavedSharedPreference.putKey(com.bhanguz.lump.activity.RegisterActivity.this, "KEY_user_id",
                                        loginResponse.getUserId());
                                Intent intent = new Intent(new Intent(getApplicationContext(), MainActivity.class));
                                startActivity(intent);
                                finishAffinity();
                            } else {
                                Toast.makeText(com.bhanguz.lump.activity.RegisterActivity.this, loginResponse.getErrorMsg(), Toast.LENGTH_SHORT).show();

                            }

                            Toast.makeText(com.bhanguz.lump.activity.RegisterActivity.this, loginResponse.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(com.bhanguz.lump.activity.RegisterActivity.this, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ModelRegister> call, Throwable t) {
                        mloaderLayout1.setVisibility(View.GONE);
                        Log.e("onFailure: ", t.toString());
                    }
                });
    }

    private void validation() {


        if (username.getText().toString().isEmpty()) {
            username.setError("Enter your name");
        } else if (age.getText().toString().isEmpty()) {
            age.setError("Enter Your age");
        }
        //R.id.editTextAge, Range.closed(13, 60), R.string.ageerror);
        else if (edtBio.getText().toString().isEmpty()) {
            edtBio.setError("Enter your Bio-data ");
        } else {
            usernamee = username.getText().toString();
            agee = age.getText().toString();
            edtBioo = edtBio.getText().toString();
            UpdateProfileApi();

        }
    }

    public void SelectImage() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Select image from");
        alertDialogBuilder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //Open camera and click image
                openCamera();
            }
        });

        alertDialogBuilder.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Open Gallery and take image
                openGallery();

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void onResume() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onResume();
    }


    public void onStart() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onStart();
    }


    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_GALLERY);
    }

    private void openCamera() {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, MY_CAMERA);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {


            if (requestCode == PICK_FROM_GALLERY) {

                onSelectFromGalleryResult(data);
            } else if (requestCode == MY_CAMERA) {
                onCaptureImageResult(data);
            }
        }
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void onCaptureImageResult(Intent data) {

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        if (thumbnail != null) {
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

            doc_1 = saveImage1(thumbnail);


            FileOutputStream fo;
            try {

                doc_1.createNewFile();
                fo = new FileOutputStream(doc_1);
                fo.write(bytes.toByteArray());
                fo.close();
                image_port = MultipartBody.Part.createFormData(docOneImage, doc_1.getName(), RequestBody.create(MediaType.parse("image/*"), doc_1));

                userImage.setImageBitmap(thumbnail);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());


                doc_1 = saveImage1(bm);
                Log.d("TAG", "onSelectFromGalleryResult: " + doc_1.getPath());
                image_port = MultipartBody.Part.createFormData(docOneImage, doc_1.getName(), RequestBody.create(MediaType.parse("image/*"), doc_1));
                userImage.setImageBitmap(bm);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public File saveImage1(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
        File imageDirectory = new File(getExternalFilesDir(null) + IMAGE_DIRECTORY);
        if (!imageDirectory.exists()) {
            imageDirectory.mkdirs();
        }
        try {
            File f = new File(imageDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            return f;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    private void age() {


    }
}

