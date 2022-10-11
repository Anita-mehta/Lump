package com.bhanguz.lump.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collection;
import java.util.List;

public class ModelFav {
    @SerializedName("data")
    @Expose
    private List<ModelFavData> data = null;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("error_msg")
    @Expose
    private String errorMsg;

    public Collection<? extends ModelFavData> getData() {
        return data;
    }

    public void setData(List<ModelFavData> data) {
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
