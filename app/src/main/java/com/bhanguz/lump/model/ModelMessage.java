package com.bhanguz.lump.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelMessage {
    @SerializedName("online")
    @Expose
    private Object online;
    @SerializedName("data")
    @Expose
    private List<ModelMessageData> data = null;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("error_msg")
    @Expose
    private String errorMsg;

    public Object getOnline() {
        return online;
    }

    public void setOnline(Object online) {
        this.online = online;
    }

    public List<ModelMessageData> getData() {
        return data;
    }

    public void setData(List<ModelMessageData> data) {
        this.data = data;
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
