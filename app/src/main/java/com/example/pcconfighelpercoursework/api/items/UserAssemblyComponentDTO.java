package com.example.pcconfighelpercoursework.api.items;

import com.google.gson.annotations.SerializedName;

public class UserAssemblyComponentDTO {
    @SerializedName("id")
    private Long id;
    @SerializedName("productName")
    private String productName;
    @SerializedName("componentCategory")
    private String componentCategory;

    public UserAssemblyComponentDTO(Long id, String productName, String componentCategory) {
        this.id = id;
        this.productName = productName;
        this.componentCategory = componentCategory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getComponentCategory() {
        return componentCategory;
    }

    public void setComponentCategory(String componentCategory) {
        this.componentCategory = componentCategory;
    }
}
