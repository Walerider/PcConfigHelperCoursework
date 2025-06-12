package com.example.pcconfighelpercoursework.api.items;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDAO {
    @SerializedName("id")
    long id;
    @SerializedName("name")
    String name;
    @SerializedName("description")
    String description;
    @SerializedName("prices")
    private List<Integer> prices;
    @SerializedName("productAttributes")
    private List<ProductAttributeDAO> attributes;
    @SerializedName("productImages")
    private List<ProductImageDTO> productImages;

    public ProductDAO(long id, String name, String description, List<Integer> prices, List<ProductAttributeDAO> attributes, List<ProductImageDTO> productImages) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.prices = prices;
        this.attributes = attributes;
        this.productImages = productImages;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getPrices() {
        return prices;
    }

    public void setPrices(List<Integer> prices) {
        this.prices = prices;
    }

    @Override
    public String toString() {
        return "ProductDAO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", prices=" + prices +
                ", attributes=" + attributes +
                '}';
    }

    public List<ProductAttributeDAO> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ProductAttributeDAO> attributes) {
        this.attributes = attributes;
    }

    public List<ProductImageDTO> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImageDTO> productImages) {
        this.productImages = productImages;
    }
}

