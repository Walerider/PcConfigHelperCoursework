package com.example.pcconfighelpercoursework.catalog.items;

import android.os.Parcel;

import androidx.annotation.NonNull;

public class CatalogPowerSupply extends CatalogItem {
    int power;
    String certificate;

    public CatalogPowerSupply(int id, String type, String name, String image, String componentType, String description, int price, int power, String certificate) {
        super(id, type, name, image, componentType, description, price);
        this.power = power;
        this.certificate = certificate;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }
}
