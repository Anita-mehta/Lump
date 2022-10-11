package com.bhanguz.lump.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class SavedSharedPreference {
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    private static SavedSharedPreference mInstance;
    private static Context mctx;
    private static final String user_age="KEY_user";
    private static final String user_id = "KEY_user_id";
    private static final String user_bio="KEY_user_bio";
    private static final String USER_CALL ="user_call";
    private static final String is_active = "KEY_is_active";

    public SavedSharedPreference(Context context) {
        mctx = context;
    }

    public static synchronized SavedSharedPreference getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SavedSharedPreference(context);
        }
        return mInstance;
    }

    public static String putKey(Context context, String Key, String Value) {
        sharedPreferences = context.getSharedPreferences("AppCache", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(Key, Value);
        editor.commit();

        return Key;
    }

    public static String getKey(Context contextGetKey, String Key) {
        sharedPreferences = contextGetKey.getSharedPreferences("AppCache", Context.MODE_PRIVATE);
        String Value = sharedPreferences.getString(Key, "");
        return Value;

    }
    public static void removeKey(Context context, String key) {
        sharedPreferences = context.getSharedPreferences("AppCache", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    public static void removeclear(Context context) {
        sharedPreferences = context.getSharedPreferences("AppCache", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }




}
