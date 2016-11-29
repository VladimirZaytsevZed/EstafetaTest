package com.volodia.estafetatest;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Volodia on 28.11.2016.
 */

public class PrefsManager {
    public static final String PREFS_NAME = "Login";

    public static final String KEY_LOGIN = "loginSuccess";
    public static final String KEY_PASS = "pass";

    SharedPreferences preferences;

    public PrefsManager(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveLoginPass(String login, String pass) {
        preferences.edit().putString(KEY_LOGIN, login).putString(KEY_PASS, pass).apply();
    }

    public String getUserLogin() {
        return preferences.getString(KEY_LOGIN, null);
    }

    public String getUserPass() {
        return preferences.getString(KEY_PASS, null);
    }

    public void clearUser(){
        preferences.edit().clear().apply();
    }

}
