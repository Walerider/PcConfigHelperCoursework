package com.example.pcconfighelpercoursework.configurator;

public class ConfigurerItem {
    private int id;
    private String type;
    private String name;
    private String image;
    private String componentType;
    private int price;
    private boolean selected;
    {
        selected = false;
    }

    public ConfigurerItem(int id, String type, String name, String image, String componentType, int price, boolean selected) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.image = image;
        this.componentType = componentType;
        this.price = price;
        this.selected = selected;
    }

    public ConfigurerItem() {
    }

    public ConfigurerItem(String componentType) {
        this.componentType = componentType;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ConfigurerItem{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", componentType='" + componentType + '\'' +
                ", price=" + price +
                ", selected=" + selected +
                '}';
    }
}