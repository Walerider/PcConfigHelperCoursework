package com.example.pcconfighelpercoursework.catalog.items;

public class CatalogCase extends CatalogItem {
    String formFactor;

    public CatalogCase(int id, String type, String name, String image, String componentType, String description, int price, String formFactor) {
        super(id, type, name, image, componentType, description, price);
        this.formFactor = formFactor;
    }

    public String getFormFactor() {
        return formFactor;
    }

    public void setFormFactor(String formFactor) {
        this.formFactor = formFactor;
    }
}

