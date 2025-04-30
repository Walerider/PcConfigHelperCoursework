package com.example.pcconfighelpercoursework.catalog.items;

import android.os.Parcel;

import androidx.annotation.NonNull;

public class CatalogMotherboard extends CatalogItem {
    String socket;
    String formFactor;

    public CatalogMotherboard(int id, String type, String name, String image, String componentType, String description, int price, String socket, String formFactor) {
        super(id, type, name, image, componentType, description, price);
        this.socket = socket;
        this.formFactor = formFactor;
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public String getFormFactor() {
        return formFactor;
    }

    public void setFormFactor(String formFactor) {
        this.formFactor = formFactor;
    }
}
