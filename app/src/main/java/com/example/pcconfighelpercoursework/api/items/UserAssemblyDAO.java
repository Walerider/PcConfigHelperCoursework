package com.example.pcconfighelpercoursework.api.items;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserAssemblyDAO {
    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String name;
    @SerializedName("userId")
    private Long userId;
    @SerializedName("price")
    private int price;
    @SerializedName("userAssemblyComponents")
    private List<UserAssemblyComponentDTO> userAssemblyComponents;

    public UserAssemblyDAO(Long id, String name, Long userId, int price, List<UserAssemblyComponentDTO> userAssemblyComponents) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.price = price;
        this.userAssemblyComponents = userAssemblyComponents;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<UserAssemblyComponentDTO> getUserAssemblyComponents() {
        return userAssemblyComponents;
    }

    public void setUserAssemblyComponents(List<UserAssemblyComponentDTO> userAssemblyComponents) {
        this.userAssemblyComponents = userAssemblyComponents;
    }
}
