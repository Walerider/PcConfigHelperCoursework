package com.example.pcconfighelpercoursework.api.items;

import com.google.gson.annotations.SerializedName;

public class ProductAttributeDAO {
    @SerializedName("name")
    private String name;
    @SerializedName("value")
    private String value;

    @Override
    public String toString() {
        return "ProductAttributeDAO{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ProductAttributeDAO(String name) {
        this.name = name;
    }

    public ProductAttributeDAO(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
