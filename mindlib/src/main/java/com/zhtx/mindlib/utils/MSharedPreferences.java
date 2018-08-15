package com.zhtx.mindlib.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class MSharedPreferences {

    private SharedPreferences mPreferences;

    public MSharedPreferences(Context context, String spName) {
        mPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    public SharedPreferences getPreferences() {
        return mPreferences;
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return mPreferences.getBoolean(key, defaultValue);
    }

    public boolean setBoolean(String key, boolean value) {
        return mPreferences.edit().putBoolean(key, value).commit();
    }

    public String getString(String key, String defaultValue) {
        return mPreferences.getString(key, defaultValue);
    }

    public boolean setString(String key, String value) {
        return mPreferences.edit().putString(key, value).commit();
    }

    public int getInt(String key, int defaultValue) {
        return mPreferences.getInt(key, defaultValue);
    }

    public boolean setInt(String key, int value) {
        return mPreferences.edit().putInt(key, value).commit();
    }

    public boolean remove(String key) {
        return mPreferences.edit().remove(key).commit();
    }

}
