package com.cricscore.deepakshano.cricscore.activity;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "login_success";

    private static final String IS_LOGIN_SUCCESS_FULL = "IsSuccessful";

    public PreferenceManager(Context context) {
        try{
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        }catch (Exception e){
            e.getMessage();
            e.printStackTrace();
        }
    }

    public void setFirstTimeLaunch(boolean isLoginSuccessful) {
        try{
        editor.putBoolean(IS_LOGIN_SUCCESS_FULL, isLoginSuccessful);
        editor.commit();
        }catch (Exception e){
            e.getMessage();
            e.printStackTrace();
        }
    }

    public boolean isLoginSuccessful() {
        return pref.getBoolean(IS_LOGIN_SUCCESS_FULL, false);
    }
}