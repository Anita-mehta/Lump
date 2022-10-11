package com.bhanguz.lump.utilities;

import android.widget.TextView;

import com.bhanguz.lump.model.EndcallModel;
import com.bhanguz.lump.model.ListOnlineModel;
import com.bhanguz.lump.model.ModelChat;
import com.bhanguz.lump.model.ModelChatDetails;
import com.bhanguz.lump.model.ModelDiscoverNew;
import com.bhanguz.lump.model.ModelFav;
import com.bhanguz.lump.model.ModelLogin;
import com.bhanguz.lump.model.ModelMessage;
import com.bhanguz.lump.model.ModelOtp;
import com.bhanguz.lump.model.ModelRegister;
import com.bhanguz.lump.model.ModelRequestStatus;
import com.bhanguz.lump.model.Online0Model;
import com.bhanguz.lump.model.ProfileResponse;
import com.bhanguz.lump.model.SendcallModel;
import com.bhanguz.lump.model.SocialModel;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("login.php")
    Call<ModelLogin> getLogin(@Field("mobile_number") String mobile_number,
                              @Field("uname") String uname,
                              @Field("token") String token,
                              @Field("fcm_token")String fcm
    );


    @FormUrlEncoded
    @POST("check_otp.php")
    Call<ModelOtp> getOtp(@Field("mobile_number") String mobile_number,
                          @Field("otp") String otp,
                          @Field("uname") String uname,
                          @Field("token") String token,
                          @Field("fcm_token")String fcm);

    @FormUrlEncoded
    @POST("login_social.php")
    Call<SocialModel> getlogin(@Field("token") String token,
                               @Field("email") String email,
                               @Field("uname") String uname,
                               @Field("type") String type,
                               @Field("token_id") String tokenid,
                               @Field("fcm_token") String fcm);
    @Multipart
    @POST("update_profile.php")
    Call<ModelRegister> getRegister(@Part("uname") RequestBody uname,
                                    @Part("token") RequestBody token,
                                    @Part("user_id") RequestBody user_id,
                                    @Part("username") RequestBody username,
                                    @Part("age") RequestBody age,
                                    @Part("gender") RequestBody gender,
                                    @Part("interested_in") RequestBody interested_in,
                                    @Part("about") RequestBody about,
                                    @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("discover_people.php")
    Call<ModelDiscoverNew> getDiscover(@Field("user_id") String user_id,
                                       @Field("uname") String uname,
                                       @Field("token") String token);


    @FormUrlEncoded
    @POST("request_status.php")
    Call<ModelRequestStatus> getStatus(@Field("user_id") String user_id,
                                       @Field("friend_id") String friend_id,
                                       @Field("status") String status,
                                       @Field("uname") String uname,
                                       @Field("token") String token);
    @FormUrlEncoded
    @POST("favourite_matches.php")
    Call<ModelFav> getFavData(@Field("user_id") String user_id,
                              @Field("uname") String uname,
                              @Field("token") String token);
    @FormUrlEncoded
    @POST("user_info.php")
    Call<ProfileResponse> getProfile(@Field("uname") String uname,
                                       @Field("token") String token,
                                       @Field("user_id") String user_id);
    @FormUrlEncoded
    @POST("list_user_chats.php")
    Call<ModelMessage> getChatList(@Field("user_id") String user_id,
                                   @Field("uname") String uname,
                                   @Field("token") String token);

    @FormUrlEncoded
    @POST("chat.php")
    Call<ModelChat> sendMessage(@Field("user_id") String user_id,
                                @Field("friend_id") String friend_id,
                                @Field("message") String message,
                                @Field("type") String type,
                                @Field("uname") String uname,
                                @Field("token") String token);
    @Multipart
    @POST("chat.php")
    Call<ModelChat>sendimage(@Part("user_id") RequestBody user_id,
                                    @Part("friend_id") RequestBody friend_id,
                                    @Part("message") RequestBody message,
                                    @Part("type") RequestBody type,
                                    @Part("uname") RequestBody uname,
                                    @Part("token") RequestBody token,
                                    @Part MultipartBody.Part image);


    @FormUrlEncoded
    @POST("show_chat_details.php")
    Call<ModelChatDetails> getChatDetails(@Field("user_id") String user_id,
                                          @Field("friend_id") String friend_id,
                                          @Field("uname") String uname,
                                          @Field("token") String token,
                                          @Field("type") String type);

    @Multipart
    @POST("update_profile.php")
    Call<ModelRegister> editprofile(@Part("uname") RequestBody uname,
                                    @Part("token") RequestBody token,
                                    @Part("user_id") RequestBody user_id,
                                    @Part("username") RequestBody username,
                                    @Part("age") RequestBody age,
                                    @Part("gender") RequestBody gender,
                                    @Part("interested_in") RequestBody interested_in,
                                    @Part("about") RequestBody about,
                                    @Part MultipartBody.Part image);
    @FormUrlEncoded
    @POST("update_online_status.php")
    Call<Online0Model> getOnline(@Field("token") String token,
                                 @Field("uname") String uname,
                                 @Field("user_id") String user_id,
                                 @Field("is_online") String is_online);
    @FormUrlEncoded
    @POST("list_online_users.php")
    Call<ListOnlineModel> getlistOnline(@Field("uname") String uname,
                                        @Field("token") String token,
                                        @Field("user_id") String user_id);
    @FormUrlEncoded
    @POST("send_video_call.php")
    Call<SendcallModel> sendCall(@Field("uname") String uname,
                                 @Field("token")String token,
                                 @Field("user_id") String user_id,
                                 @Field("friend_id") String friend_id,
                                 @Field("room_no") String room_no);

    @FormUrlEncoded
    @POST("end_video_call.php")
    Call<EndcallModel> endCall(@Field("uname") String uname,
                                @Field("token")String token,
                                @Field("video_call_id") String video_call_id,
                                @Field("user_id") String user_id);
}
