package com.bhanguz.lump.model;

public class EndcallModel {
    public String error;
    public Object room_no;
    public String caller_name;
    public String error_msg;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getRoom_no() {
        return room_no;
    }

    public void setRoom_no(Object room_no) {
        this.room_no = room_no;
    }

    public String getCaller_name() {
        return caller_name;
    }

    public void setCaller_name(String caller_name) {
        this.caller_name = caller_name;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}
