package com.example.pcconfighelpercoursework.catalog.items;

import android.os.Parcel;

import androidx.annotation.NonNull;

public class CatalogRAM extends CatalogItem {
    int capacity;
    String memoryType;

    public CatalogRAM(int id, String type, String name, String image, String componentType, String description, int price, int capacity, String memoryType) {
        super(id, type, name, image, componentType, description, price);
        this.capacity = capacity;
        this.memoryType = memoryType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setMemoryType(String memoryType) {
        this.memoryType = memoryType;
    }
}
