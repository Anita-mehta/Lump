package com.bhanguz.lump.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelFavData {
    @SerializedName("follow_user_id")
    @Expose
    private String followUserId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("user_image")
    @Expose
    private String userImage;
    @SerializedName("follow_status")
    @Expose
    private String followStatus;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;


    private String image;

    private String user_id;
    public String getFollowUserId() {
        return followUserId;
    }

    public void setFollowUserId(String followUserId) {
        this.followUserId = followUserId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(String followStatus) {
        this.followStatus = followStatus;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

}
