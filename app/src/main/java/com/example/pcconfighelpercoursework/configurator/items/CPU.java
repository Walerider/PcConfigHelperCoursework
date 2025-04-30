package com.example.pcconfighelpercoursework.configurator.items;

import android.os.Parcel;

import androidx.annotation.NonNull;

public class CPU extends ConfigurerItem{
    int cores;
    String socket;
    public CPU(Parcel in) {
        super(in);
        this.cores = in.readInt();
        this.socket = in.readString();
    }

    public CPU() {
    }

    public CPU(int id, String name, String image, String componentType, String description, int price, boolean selected, int cores, String socket) {
        super(id, name, image, componentType, description, price, selected);
        this.cores = cores;
        this.socket = socket;
    }

    public CPU(String componentType) {
        super(componentType);
    }

    @Override
    public int describeContents() {
        return super.describeContents();
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(cores);
        dest.writeString(socket);
    }

    public int getCores() {
        return cores;
    }

    public void setCores(int cores) {
        this.cores = cores;
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }
}
