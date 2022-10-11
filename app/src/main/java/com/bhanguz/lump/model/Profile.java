package com.bhanguz.lump.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {
    @SerializedName("user_id")
    @Expose
    private String user_id;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("mobile_number")
    @Expose
    private String mobile_number;

    @SerializedName("gender")
    @Expose
    private String gender;


    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("is_online")
    @Expose
    private String is_online;
    public String getUserId() {
        return user_id;
    }

    public void setUserId(String userId) {
        this.user_id = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobileNumber() {
        return mobile_number;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobile_number = mobileNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIsOnline() {
        return is_online;
    }

    public void setIsOnline(String isOnline) {
        this.is_online = isOnline;
    }

}
