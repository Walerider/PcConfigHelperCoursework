package com.example.pcconfighelpercoursework.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AssemblyData {
    private static final String PREFS_NAME = "assemblyPrefs";
    private static SharedPreferences sharedPreferences;
    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
    public static void setString(String key,String value){
        sharedPreferences.edit().putString(key,value).apply();
    }
    public static String getString(String key){
        return sharedPreferences.getString(key,null);
    }
}
