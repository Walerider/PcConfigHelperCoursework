package com.example.pcconfighelpercoursework.catalog;

public class CatalogItem {
    private int id;
    private String type;
    private String name;
    private String image;
    private String componentType;
    private String description;
    private int price;

    public CatalogItem(int id, String type, String name, String image, String componentType, String description, int price) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.image = image;
        this.componentType = componentType;
        this.description = description;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
