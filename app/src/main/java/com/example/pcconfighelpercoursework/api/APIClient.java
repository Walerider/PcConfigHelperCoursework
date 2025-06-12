package com.example.pcconfighelpercoursework.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    //10.0.2.2
    private static final String BASE_URL = "http://195.2.71.134:8080";
    private static Retrofit retrofit = null;
    public static API getApi(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(API.class);
    }
}
