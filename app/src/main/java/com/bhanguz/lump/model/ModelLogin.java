package com.bhanguz.lump.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelLogin {
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("error_msg")
    @Expose
    private String errorMsg;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("new_user")
    @Expose
    private String newUser;
    @SerializedName("is_active")
    @Expose
    private String isActive;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNewUser() {
        return newUser;
    }

    public void setNewUser(String newUser) {
        this.newUser = newUser;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
