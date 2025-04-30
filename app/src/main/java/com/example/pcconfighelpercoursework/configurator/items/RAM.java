package com.example.pcconfighelpercoursework.configurator.items;

import android.os.Parcel;

import androidx.annotation.NonNull;

public class RAM extends ConfigurerItem{
    int capacity;
    String memoryType;

    public RAM(int id,  String name, String image, String componentType, String description, int price, boolean selected, int capacity, String memoryType) {
        super(id,  name, image, componentType, description, price, selected);
        this.capacity = capacity;
        this.memoryType = memoryType;
    }

    public RAM() {
    }

    public RAM(Parcel in) {
        super(in);
        this.capacity = in.readInt();
        this.memoryType = in.readString();
    }

    public RAM(String componentType) {
        super(componentType);
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(capacity);
        dest.writeString(memoryType);
    }

    @Override
    public int describeContents() {
        return super.describeContents();
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
