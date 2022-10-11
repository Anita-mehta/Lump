package com.bhanguz.lump.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelRegister {
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("error_msg")
    @Expose
    private String errorMsg;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
