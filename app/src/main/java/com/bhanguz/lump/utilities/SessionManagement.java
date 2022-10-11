package com.bhanguz.lump.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {
    SharedPreferences preferences;

    SharedPreferences.Editor editor;

    Context context;

    //Sharedpref file name
    private static final String PREF_NAME ="REDPAY";

    private static final String IS_LOGIN ="IsLoggedIn";

    private static final String USER_ID ="user_id";

    private static final String USER_TYPE = "user_type";
    private static final String USER_CALL ="user_call";

    private static final String ELEC = "elec";

    public SessionManagement(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME,0);
        editor = preferences.edit();
    }

    public void setUserCall() {
       editor.putBoolean("user_call", true);
       // editor.getString("key_name", null);

    }
    public String getUserCall(){
        String user_call= preferences.getString(USER_CALL,null);
        return user_call;
    }
    public void setUserCall(String userType){

        editor.putString(USER_CALL, userType);
        editor.commit();
    }
    public void createLoginSession(String user_id, String user_type){

        editor.putBoolean(IS_LOGIN, true);




        editor.putString(USER_TYPE, user_type);

        editor.commit();
    }

    public boolean isLoggedIn(){
        return preferences.getBoolean(IS_LOGIN, false);
    }

    public String getUserId(){
        String user_id = preferences.getString(USER_ID,null);
        return user_id;
    }

    public void setOutletStatus(String electricity){
        editor.putString(ELEC, electricity);
        editor.commit();
    }

    public void setUserType(String userType){

        editor.putString(USER_TYPE, userType);
        editor.commit();
    }


    public String getUserType(){
        String user_type = preferences.getString(USER_TYPE,null);
        return user_type;
    }

    public String getOutletStatus(){
        String elec = preferences.getString(ELEC,null);
        return elec;
    }

    public void destroyLoginSession(){

        editor.putBoolean(IS_LOGIN, false);

        editor.commit();

    }


}
