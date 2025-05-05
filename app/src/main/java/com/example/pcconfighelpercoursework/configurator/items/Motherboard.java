package com.example.pcconfighelpercoursework.configurator.items;

import android.os.Parcel;

import androidx.annotation.NonNull;

public class Motherboard extends ConfigurerItem{
    String socket;
    String formFactor;

    public Motherboard(int id,  String name, String image, String componentType, String description, int price, boolean selected, String socket, String formFactor) {
        super(id, name, image, componentType, description, price, selected);
        this.socket = socket;
        this.formFactor = formFactor;
    }

    public Motherboard() {
    }

    public Motherboard(Parcel in) {
        super(in);
        this.socket = in.readString();
        this.formFactor = in.readString();
    }

    public Motherboard(String componentType) {
        super(componentType);
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(socket);
        dest.writeString(formFactor);
    }

    @Override
    public int describeContents() {
        return super.describeContents();
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

    @Override
    public ConfigurerItem createUpdatedComponent(String componentType) {
        return new Motherboard(
                this.getId(),
                this.getName(),
                this.getImage(),
                componentType,
                this.getDescription(),
                this.getPrice(),
                true,
                this.getSocket(),
                this.getFormFactor()
        );
    }
}
