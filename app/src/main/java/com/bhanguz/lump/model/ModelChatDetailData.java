package com.bhanguz.lump.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelChatDetailData {
    @SerializedName("chat_id")
    @Expose
    private String chatId;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("friend_id")
    @Expose
    private String friendId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("is_online")
    @Expose
    private String isOnline;
    @SerializedName("message_type")
    @Expose
    private String message_type;
    @SerializedName("type")
    @Expose
    private int type;

    private  String date;

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserid() {
        return user_id;
    }

    public void setUserid(String userid) {
        this.user_id = userid;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    public String getMessage_type() { return message_type; }

    public void setMessage_type(String message_type) { this.message_type = message_type; }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
