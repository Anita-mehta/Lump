package com.bhanguz.lump.model;

import java.util.ArrayList;

public class ModelDiscoverNew {
    private String error;

    private String error_msg;

    private ArrayList<Profile> data;


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

    public ArrayList<Profile> getData() {
        return data;
    }

    public void setData(ArrayList<Profile> data) {
        this.data = data;
    }
}
