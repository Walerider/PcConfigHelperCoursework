package com.example.pcconfighelpercoursework.configurator.items;

import android.os.Parcel;

import androidx.annotation.NonNull;

public class PowerSupply extends ConfigurerItem{
    int power;
    String certificate;

    public PowerSupply(int id, String name, String image, String componentType, String description, int price, boolean selected, int power, String certificate) {
        super(id, name, image, componentType, description, price, selected);
        this.power = power;
        this.certificate = certificate;
    }

    public PowerSupply() {
    }

    public PowerSupply(String componentType) {
        super(componentType);
    }

    public PowerSupply(Parcel in) {
        super(in);
        this.power = in.readInt();
        this.certificate = in.readString();
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(power);

    }

    @Override
    public int describeContents() {
        return super.describeContents();
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
