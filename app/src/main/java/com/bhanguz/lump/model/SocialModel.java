package com.bhanguz.lump.model;

public class SocialModel {
    public String error;
    public String error_msg;
    public int otp;
    public String user_id;
    public String is_active;
    private String new_user;
    private String fcm;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getNew_user() {
        return new_user;
    }
    public void setNew_user(String new_user) {
        this.new_user = new_user;
    }
    public String getFcm() {
        return fcm;
    }

    public void setFcm(String fcm) {
        this.fcm = fcm;
    }



}
