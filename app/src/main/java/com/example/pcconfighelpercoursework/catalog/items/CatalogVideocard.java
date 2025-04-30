package com.example.pcconfighelpercoursework.catalog.items;

import android.os.Parcel;

import androidx.annotation.NonNull;

public class CatalogVideocard extends CatalogItem {
    int vramSize;
    String series;

    public CatalogVideocard(int id, String type, String name, String image, String componentType, String description, int price, int vramSize, String series) {
        super(id, type, name, image, componentType, description, price);
        this.vramSize = vramSize;
        this.series = series;
    }

    public int getVramSize() {
        return vramSize;
    }

    public void setVramSize(int vramSize) {
        this.vramSize = vramSize;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }
}
