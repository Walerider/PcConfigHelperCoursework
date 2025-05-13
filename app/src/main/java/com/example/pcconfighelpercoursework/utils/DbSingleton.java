package com.example.pcconfighelpercoursework.utils;

import android.content.Context;

public class DbSingleton {
    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }
}