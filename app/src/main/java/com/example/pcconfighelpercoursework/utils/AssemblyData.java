package com.example.pcconfighelpercoursework.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AssemblyData {
    private static final String PREFS_NAME = "assemblyPrefs";
    private static SharedPreferences sharedPreferences;
    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
    public static void clear(Context context) {
        if(sharedPreferences != null){
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                    .edit()
                    .clear()
                    .apply();
        }
    }
    public static void setString(String key,String value){
        sharedPreferences.edit().putString(key,value).apply();
    }
    public static String getString(String key){
        return sharedPreferences.getString(key,"");
    }
    public static void setInt(String key,int value){
        sharedPreferences.edit().putInt(key,value).apply();
    }
    public static int getInt(String key){
        return sharedPreferences.getInt(key,0);
    }

}
