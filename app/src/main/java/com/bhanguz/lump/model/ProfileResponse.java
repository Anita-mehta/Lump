package com.bhanguz.lump.model;

public class ProfileResponse {
    private String user_id;
    private String lump_id = "";
    private String last_login = "";
    private String username = "";
    private String email = "";
    private String mobile_number = "";
    private String gender = "";
    private String birthday = "";
    private String profile = "";
    private String country = "";
    private String is_online = "";
    private String about = "";
    private String age = "";
    private String is_active = "";
    private String interested_in = "";
    private String error;
    private String error_msg;
//    private  ArrayList<ProfileData> profile;

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLump_id() {
        return lump_id;
    }

    public void setLump_id(String lump_id) {
        this.lump_id = lump_id;
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public String getError() {
        return error;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIs_online() {
        return is_online;
    }

    public void setIs_online(String is_online) {
        this.is_online = is_online;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
    public String getAge() { return age; }

    public void setAge(String age) { this.age = age; }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getInterested_in() {
        return interested_in;
    }

    public void setInterested_in(String interested_in) {
        this.interested_in = interested_in;
    }



}
