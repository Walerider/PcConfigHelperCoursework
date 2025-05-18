package com.example.pcconfighelpercoursework.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class NavigationData {
    private static final String PREFS_NAME = "NavigationPrefs";
    private static SharedPreferences sharedPreferences;

    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static void setBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key,value).apply();
    }

    public static Boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key,false);
    }
}
