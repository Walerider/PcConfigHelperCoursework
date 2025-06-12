package com.example.pcconfighelpercoursework.api;

import com.example.pcconfighelpercoursework.api.items.AssemblyPOJO;
import com.example.pcconfighelpercoursework.api.items.FilterDAO;
import com.example.pcconfighelpercoursework.api.items.PriceDAO;
import com.example.pcconfighelpercoursework.api.items.ProductDAO;
import com.example.pcconfighelpercoursework.api.items.UserAssemblyDAO;
import com.example.pcconfighelpercoursework.api.items.UserPOJO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {
    @GET("/api/products/category/{id}")
    Call<List<ProductDAO>> getProductsByCategory(@Path("id") long id);
    @GET("/api/products/{id}")
    Call<ProductDAO> getProductById(@Path("id") long id);
    @POST("/api/products/category/filter/{id}")
    Call<List<ProductDAO>> getProductsByCategoryFilter(@Path("id") long id, @Body FilterDAO filterRequest);
    @GET("/api/products/price/{id}")
    Call<PriceDAO> getProductPrice(@Path("id") long id);
    @GET("/api/assemblies/user/{id}")
    Call<List<UserAssemblyDAO>> getAllAssembliesByUserId(@Path("id") long id);
    @POST("/api/users/create")
    Call<String> registerUser(@Body UserPOJO user);
    @GET("/api/users/user")
    Call<String> loginUser(
            @Query("username") String username, // Параметр username
            @Query("password") String password  // Параметр password
    );
    @POST("/api/assemblies/create")
    Call<AssemblyPOJO> createAssembly(@Body AssemblyPOJO assembly);
}
