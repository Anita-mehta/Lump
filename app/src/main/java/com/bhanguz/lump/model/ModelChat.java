package com.bhanguz.lump.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelChat {
    @SerializedName("message")
    @Expose
    private Object message;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("error_msg")
    @Expose
    private String errorMsg;

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

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

}
