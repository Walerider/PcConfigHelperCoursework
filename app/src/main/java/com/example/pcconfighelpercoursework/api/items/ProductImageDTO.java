package com.example.pcconfighelpercoursework.api.items;

import com.google.gson.annotations.SerializedName;

public class ProductImageDTO {
    @SerializedName("id")
    private long id;
    @SerializedName("productId")
    private long productId;
    @SerializedName("source")
    private String source;

    public ProductImageDTO(long id, long productId, String source) {
        this.id = id;
        this.productId = productId;
        this.source = source;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "ProductImageDTO{" +
                "id=" + id +
                ", productId=" + productId +
                ", source='" + source + '\'' +
                '}';
    }
}
