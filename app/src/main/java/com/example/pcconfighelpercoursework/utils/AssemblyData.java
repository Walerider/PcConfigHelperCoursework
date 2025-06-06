package com.example.pcconfighelpercoursework.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.pcconfighelpercoursework.items.Component;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AssemblyData {
    private static final String PREFS_NAME = "assemblyPrefs";
    private static SharedPreferences sharedPreferences;
    private static final Gson gson = new Gson();
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
    public static void saveList(List<Component> list) {
        String json = gson.toJson(list);
        sharedPreferences.edit().putString(PREFS_NAME, json).apply();
    }

    public static List<Component> loadList() {
        String json = sharedPreferences.getString(PREFS_NAME, null);
        Type type = new TypeToken<List<Component>>(){}.getType();
        return json != null ? gson.fromJson(json, type) : new ArrayList<>();
    }
    public static void setString(String key,String value){
        sharedPreferences.edit().putString(key,value).apply();
    }
    public static String getString(String key){
        return sharedPreferences.getString(key, "");
    }
    public static void setInt(String key,int value){
        sharedPreferences.edit().putInt(key,value).apply();
    }
    public static int getInt(String key){
        return sharedPreferences.getInt(key,0);
    }

}
