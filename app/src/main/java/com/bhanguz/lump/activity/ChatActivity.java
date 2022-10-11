package com.bhanguz.lump.activity;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bhanguz.lump.R;
import com.bhanguz.lump.adapter.ShowChatAdapter;
import com.bhanguz.lump.firebaseMessagingNotification.MyFirebaseMessagingService;
import com.bhanguz.lump.model.ModelChat;
import com.bhanguz.lump.model.ModelChatDetailData;
import com.bhanguz.lump.model.ModelChatDetails;
import com.bhanguz.lump.model.ModelRegister;
import com.bhanguz.lump.utilities.ApiUrl;
import com.bhanguz.lump.utilities.SavedSharedPreference;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends BaseActivity {
    ImageView back, send, camera1, showimagechat, sendpic1,chatprofile1;
    TextView username, close1;
    ViewGroup rootView;
    RecyclerView recyclerView;
    LinearLayout edt_comment1;
    CardView showimagelayout;
    FrameLayout mloaderlayout1;
    EmojIconActions emojAction;
    private ShowChatAdapter showChatAdapter;
    private ArrayList<ModelChatDetailData> modelChatDetailsData;
    private String friendId;
    ImageView emoji1;
    EmojiconEditText emojicon_edit_text;
    String IMAGE_DIRECTORY;
    private MultipartBody.Part image_port = null;
    private File doc_1;
    int PICK_FROM_GALLERY = 2;
    private String newToken = "";
    int MY_CAMERA = 10, CAMERA_REQUEST = 20;
    private String docOneImage = "image";
    private String pic = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        close1 = findViewById(R.id.close_imge);
        back = findViewById(R.id.back);
        send = findViewById(R.id.send);
        sendpic1 = findViewById(R.id.sendpic);
        showimagechat = findViewById(R.id.showimage);
        edt_comment1 = findViewById(R.id.sendmsg_layout);
        showimagelayout = findViewById(R.id.image_layout);
        mloaderlayout1 = findViewById(R.id.mloaderLayout);
        camera1 = findViewById(R.id.camera);
        username = findViewById(R.id.userName);
        rootView = findViewById(R.id.view_root);
        emojicon_edit_text = findViewById(R.id.emojicon_edit_text);
        recyclerView = findViewById(R.id.recycler_chatmsg);
        emoji1 = findViewById(R.id.emoji);
        chatprofile1=findViewById(R.id.chatprofile);
        emojAction = new EmojIconActions(getBaseContext(), rootView, emojicon_edit_text, emoji1);


        emoji1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        emojAction.ShowEmojIcon();
                emojAction.setUseSystemEmoji(true);
            }
        });

        emojAction.setKeyboardListener(new EmojIconActions.KeyboardListener() {
            @Override
            public void onKeyboardOpen() {
                emojAction.ShowEmojIcon();
            }

            @Override
            public void onKeyboardClose() {
//                emojAction.ShowEmojIcon();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                chat_func("text");
            }

        });
        close1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_comment1.setVisibility(View.VISIBLE);
                showimagelayout.setVisibility(View.GONE);
            }
        });
        camera1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();

            }
        });
        sendpic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatimageApi("image");
                edt_comment1.setVisibility(View.VISIBLE);
                showimagelayout.setVisibility(View.GONE);

            }

        });

      //  getChatDetails();
        Intent intent = getIntent();
        friendId = intent.getStringExtra("friend_id");
        pic = intent.getStringExtra("image");

        if(pic.isEmpty()) {
            Picasso.get().load(R.drawable.placeholder).into(chatprofile1);
        } else {
            Picasso.get().load(getString(R.string.img_url)+pic).into(chatprofile1);
        }



        getChatDetails();

        if (getIntent().getExtras() != null) {
            String username1 = getIntent().getStringExtra("username");
            //String image1 = getIntent().getStringExtra("image");
           // chatprofile1.setImageResource();
            username.setText(username1);
        }


    }


    private void chat_func(String typeChat) {
        mloaderlayout1.setVisibility(View.VISIBLE);
        String uname = "bhanguz";
        String token = "012d%lump%4071";
        String message = emojicon_edit_text.getText().toString();
        String user_id = SavedSharedPreference.getKey(getApplicationContext(), "KEY_user_id");
        String type = typeChat;
        Log.e("username: ", user_id + " :" + type);

        if (TextUtils.isEmpty(message)) {
            Toast.makeText(getApplicationContext(), "Please Enter Some Message", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiUrl.getAllClient().sendMessage(user_id, friendId, message, type, uname, token).enqueue(new Callback<ModelChat>() {

            @Override
            public void onResponse(Call<ModelChat> call, Response<ModelChat> response) {
                mloaderlayout1.setVisibility(View.GONE);
                if (response.isSuccessful() && null != response.body()) {
                    ModelChat getDistributorResponse = response.body();
                    if (getDistributorResponse.getError().equalsIgnoreCase("0")) {
                        if (emojicon_edit_text != null) {

                            emojicon_edit_text.setText("");
                            //  Picasso.get().load(ChatActivity.this.getString(R.string.img_url) + modelChatDetailsData.get(position).getImage()).into(type1);
                            getChatDetails();


                        } else {
                            Toast.makeText(getApplicationContext(), getDistributorResponse.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelChat> call, Throwable t) {
                Log.e("onFailure: ", t.toString());
                mloaderlayout1.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "You have no chat", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getChatDetails() {
        String uname = "bhanguz";
        String token = "012d%lump%4071";
        String user_id = SavedSharedPreference.getKey(getApplicationContext(), "KEY_user_id");

        modelChatDetailsData = new ArrayList<>();

        Log.e("username: ", user_id);
        ApiUrl.getAllClient().getChatDetails(user_id, friendId, uname, token, "image").enqueue(new Callback<ModelChatDetails>() {
            @Override
            public void onResponse(Call<ModelChatDetails> call, Response<ModelChatDetails> response) {
                if (response.isSuccessful() && null != response.body()) {
                    ModelChatDetails getDistributorResponse = response.body();

                    if (getDistributorResponse.getError().equalsIgnoreCase("0")) {

                        if (getDistributorResponse.getData() != null) {


                            modelChatDetailsData.addAll(getDistributorResponse.getData());
                            showChatAdapter = new ShowChatAdapter(modelChatDetailsData, getApplicationContext());
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true));
                            recyclerView.setAdapter(showChatAdapter);

                        }
                    } else {
                        Toast.makeText(getApplicationContext(), getDistributorResponse.getErrorMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelChatDetails> call, Throwable t) {
                Log.e("onFailure: ", t.toString());
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

                showimagechat.setImageBitmap(thumbnail);
                edt_comment1.setVisibility(View.GONE);
                showimagelayout.setVisibility(View.VISIBLE);

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
                showimagechat.setImageBitmap(bm);
                edt_comment1.setVisibility(View.GONE);
                showimagelayout.setVisibility(View.VISIBLE);
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


    private void chatimageApi(String typeImage) {
        mloaderlayout1.setVisibility(View.VISIBLE);
        String user_id = SavedSharedPreference.getKey(ChatActivity.this, "KEY_user_id");
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        requestBodyMap.put("Content-Type", RequestBody.create(MediaType.parse("multipart/form-data"), "application/x-www-form-urlencoded"));
        // File newfile = new File(selectedImage.getPath());
        Log.d("TAG", MessageFormat.format("uploadprofile: {0}", requestBodyMap));
        RequestBody user_id_bod = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody friendId_bod = RequestBody.create(MediaType.parse("text/plain"), friendId);

        RequestBody uname = RequestBody.create(MediaType.parse("text/plain"), "bhanguz");
        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), "012d%lump%4071");
        RequestBody message = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), typeImage);
        // RequestBody image=RequestBody.
        // RequestBody type= RequestBody.
        ApiUrl.getAllClient().sendimage(user_id_bod, friendId_bod, message, type, uname, token, image_port)
                .enqueue(new Callback<ModelChat>() {

                    public void onResponse(Call<ModelChat> call, Response<ModelChat> response) {
                        mloaderlayout1.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            if (response.body().getError().equalsIgnoreCase("0")) {
                                Toast.makeText(ChatActivity.this, "upload image", Toast.LENGTH_SHORT).show();
                                getChatDetails();
                            }


                        } else {
                            Toast.makeText(ChatActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<ModelChat> call, Throwable t) {
                        mloaderlayout1.setVisibility(View.GONE);
                        Log.e("TAG", "onFailure: " + t.getMessage());
                        Toast.makeText(ChatActivity.this, "failed", Toast.LENGTH_LONG).show();
                    }


                });

    }
}

