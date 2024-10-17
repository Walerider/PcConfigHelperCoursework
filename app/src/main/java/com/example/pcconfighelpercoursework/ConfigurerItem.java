package com.example.pcconfighelpercoursework;

public class ConfigurerItem {
    private long id;
    private String type;
    private String name;
    private int image;
    private String componentType;
    private boolean selected;

    public ConfigurerItem(long id, String type, String name, int image, String componentType, boolean selected) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.image = image;
        this.componentType = componentType;
        this.selected = selected;
    }

    public long getId() {
        return id;
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
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
}