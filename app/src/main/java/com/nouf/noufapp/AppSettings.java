package com.nouf.noufapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppSettings {
    private static final String APP_SHARED_PREFS = "nouf_app_prefs";
    private SharedPreferences appSharedPrefs;
    private Editor prefsEditor;

    public AppSettings(Context context){
        this.appSharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }

    public String getString(String key) {
        return appSharedPrefs.getString(key, "");
    }
    
    public int getInt(String key) {
        return appSharedPrefs.getInt(key, 0);
    }
    
    public long getLong(String key) {
        return appSharedPrefs.getLong(key, 0);
    }
    
    public boolean getBoolean(String key) {
        return appSharedPrefs.getBoolean(key, false);
    }

    public void putString(String key, String value) {
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }
    
    public void putInt(String key, int value) {
        prefsEditor.putInt(key, value);
        prefsEditor.commit();
    }
    
    public void putLong(String key, long value) {
        prefsEditor.putLong(key, value);
        prefsEditor.commit();
    }
    
    public void putBoolean(String key, boolean value) {
        prefsEditor.putBoolean(key, value);
        prefsEditor.commit();
    }
    
    public void remove(String key){
    	prefsEditor.remove(key);
    	prefsEditor.commit();
    }
    
    public void clear(){
    	prefsEditor.clear();
    	prefsEditor.commit();
    }
    
}