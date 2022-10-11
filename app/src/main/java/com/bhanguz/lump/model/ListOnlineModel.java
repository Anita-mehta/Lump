package com.bhanguz.lump.model;

import java.util.Collection;
import java.util.List;

public class ListOnlineModel {
    public List<ListOnlinePeople> data;
    public String error;
    public String error_msg;


    public List<ListOnlinePeople> getData() {
        return data;
    }

    public void setData(List<ListOnlinePeople> data) {
        this.data = data;
    }

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
}
