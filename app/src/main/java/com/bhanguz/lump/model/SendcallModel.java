package com.bhanguz.lump.model;

public class SendcallModel {

        public String error;
        public String room_no;
        public int video_call_id;
        public Object caller_name;
        public String error_msg;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }

    public int getVideo_call_id() {
        return video_call_id;
    }

    public void setVideo_call_id(int video_call_id) {
        this.video_call_id = video_call_id;
    }

    public Object getCaller_name() {
        return caller_name;
    }

    public void setCaller_name(Object caller_name) {
        this.caller_name = caller_name;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}


