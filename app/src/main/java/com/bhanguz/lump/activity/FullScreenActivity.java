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
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bhanguz.lump.R;
import com.bhanguz.lump.model.ModelRegister;
import com.bhanguz.lump.model.ProfileResponse;
import com.bhanguz.lump.utilities.ApiUrl;
import com.bhanguz.lump.utilities.SavedSharedPreference;
import com.squareup.picasso.Picasso;

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

import static com.facebook.FacebookSdk.getApplicationContext;

public class FullScreenActivity extends BaseActivity {
     ImageView fullimage1;
    LinearLayout backbtn;
    String IMAGE_DIRECTORY;
    Button update1;
  //  ImageView viewImage;

    private MultipartBody.Part image_port = null;
    private File doc_1;
    int PICK_FROM_GALLERY = 2;
    int MY_CAMERA = 10, CAMERA_REQUEST = 20;
    private String docOneImage = "image";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        fullimage1=findViewById(R.id.fullimage);
        backbtn = findViewById(R.id.backedit);
        update1=findViewById(R.id.update);
        update1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadprofile();
            }
        });
        fullimage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();

            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onBackPressed();

            }
        });
        fullimageApi();
    }
    private void fullimageApi() {
       // mloaderLayout.setVisibility(View.VISIBLE);
        String uname="bhanguz";
        String token="012d%lump%4071";
        String user_id= SavedSharedPreference.getKey(getApplicationContext(),"KEY_user_id");

        ApiUrl.getAllClient().getProfile(uname,token,user_id).enqueue(new Callback<ProfileResponse>() {

            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
               // mloaderLayout.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                   // profilename1.setText(response.body().getUsername());
                   // profilelocation.setText(response.body().getCountry());
                    Picasso.get().load(getApplicationContext().getString(R.string.img_url)+response.body().getProfile()).placeholder(R.drawable.placeholder).into(fullimage1);

                }

            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
              //  mloaderLayout.setVisibility(View.GONE);

            }
        });
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

                fullimage1.setImageBitmap(thumbnail);


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
                fullimage1.setImageBitmap(bm);

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



    private void uploadprofile() {

        String user_id = SavedSharedPreference.getKey(FullScreenActivity.this, "KEY_user_id");
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        requestBodyMap.put("Content-Type", RequestBody.create(MediaType.parse("multipart/form-data"), "application/x-www-form-urlencoded"));
        // File newfile = new File(selectedImage.getPath());
        Log.d("TAG", MessageFormat.format("uploadprofile: {0}", requestBodyMap));
        RequestBody uname = RequestBody.create(MediaType.parse("text/plain"), "bhanguz");
        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), "012d%lump%4071");
        RequestBody username = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody userIdBody = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody genderBody = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody ageBody = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody interestBody = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody edtBioBody = RequestBody.create(MediaType.parse("text/plain"),"");
        ApiUrl.getAllClient().editprofile(uname, token, userIdBody, username, genderBody, ageBody, interestBody, edtBioBody, image_port)
                .enqueue(new Callback<ModelRegister>() {

                    public void onResponse(Call<ModelRegister> call, Response<ModelRegister> response) {

                        if (response.isSuccessful()) {
                            if (response.body().getError().equalsIgnoreCase("1")) {
                                Toast.makeText(FullScreenActivity.this, "upload image", Toast.LENGTH_SHORT).show();
                                finish();
                            }


                        } else {
                            Toast.makeText(FullScreenActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<ModelRegister> call, Throwable t) {

                        Log.e("TAG", "onFailure: " + t.getMessage());
                        Toast.makeText(FullScreenActivity.this, "failed", Toast.LENGTH_LONG).show();
                    }


                });

    }

}
