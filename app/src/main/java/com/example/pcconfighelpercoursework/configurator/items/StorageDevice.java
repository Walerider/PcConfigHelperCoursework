package com.example.pcconfighelpercoursework.configurator.items;

import android.os.Parcel;

import androidx.annotation.NonNull;

public class StorageDevice extends ConfigurerItem{
    String type;
    int capacity;

    public StorageDevice(int id,  String name, String image, String componentType, String description, int price, boolean selected, String type, int capacity) {
        super(id,  name, image, componentType, description, price, selected);
        this.type = type;
        this.capacity = capacity;
    }

    public StorageDevice(Parcel in) {
        super(in);
        this.type = in.readString();
        this.capacity = in.readInt();
    }

    public StorageDevice() {
    }

    public StorageDevice(String componentType) {
        super(componentType);
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(type);
        dest.writeInt(capacity);
    }

    @Override
    public int describeContents() {
        return super.describeContents();
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
