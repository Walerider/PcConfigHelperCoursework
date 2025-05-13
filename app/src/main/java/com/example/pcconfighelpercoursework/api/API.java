package com.example.pcconfighelpercoursework.api;

import com.example.pcconfighelpercoursework.api.items.ProductDAO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface API {
    @GET("/api/products/category/{id}")
    Call<List<ProductDAO>> getProductsByCategory(@Path("id") long id);
}
