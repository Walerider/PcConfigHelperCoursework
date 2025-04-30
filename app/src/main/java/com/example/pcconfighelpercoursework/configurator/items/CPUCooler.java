package com.example.pcconfighelpercoursework.configurator.items;

import android.os.Parcel;

import androidx.annotation.NonNull;

public class CPUCooler extends ConfigurerItem{
    String socket;
    int dissipation;

    public CPUCooler(int id, String name, String image, String componentType, String description, int price, boolean selected, String socket, int dissipation) {
        super(id, name, image, componentType, description, price, selected);
        this.socket = socket;
        this.dissipation = dissipation;
    }

    public CPUCooler(Parcel in) {
        super(in);
        this.socket = in.readString();
        this.dissipation = in.readInt();
    }

    public CPUCooler(String componentType) {
        super(componentType);
    }

    public CPUCooler() {
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(socket);
        dest.writeInt(dissipation);
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public int getDissipation() {
        return dissipation;
    }

    public void setDissipation(int dissipation) {
        this.dissipation = dissipation;
    }
}
