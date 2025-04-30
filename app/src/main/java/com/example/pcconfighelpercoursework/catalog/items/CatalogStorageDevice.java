package com.example.pcconfighelpercoursework.catalog.items;

import android.os.Parcel;

import androidx.annotation.NonNull;

public class CatalogStorageDevice extends CatalogItem {
    String type;
    int capacity;

    public CatalogStorageDevice(int id, String type, String name, String image, String componentType, String description, int price, String type1, int capacity) {
        super(id, type, name, image, componentType, description, price);
        this.type = type1;
        this.capacity = capacity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
