package com.example.pcconfighelpercoursework.api.items;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilterDAO {
    @SerializedName("productAttributes")
    List<ProductAttributeDAO> productAttributes;
}
