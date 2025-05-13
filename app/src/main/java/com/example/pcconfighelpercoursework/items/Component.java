package com.example.pcconfighelpercoursework.items;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Component implements Parcelable {
    private int id;
    private String name;
    private String image;
    private String componentType;
    private String description;
    private int price;
    private boolean selected;
    {
        selected = false;
    }
    //todo Сделать рейтинг для комплектующих
    public Component(int id, String name, String image, String componentType, String description, int price, boolean selected) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.componentType = componentType;
        this.description = description;
        this.price = price;
        this.selected = selected;
    }

    public Component(int id, String name, String description, String componentType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.componentType = componentType;
    }

    public Component() {
    }
    protected Component(Parcel in) {

        this.id = in.readInt();
        this.name = in.readString();
        this.image = in.readString();
        this.componentType = in.readString();
        this.description = in.readString();
        this.price = in.readInt();
        this.selected = in.readByte() != 0;

    }
    public Component(String componentType) {
        this.componentType = componentType;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ConfigurerItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", componentType='" + componentType + '\'' +
                ", descpription='" + description + '\'' +
                ", price=" + price +
                ", selected=" + selected +
                '}';
    }

    public static final Creator<Component> CREATOR = new Creator<Component>() {
        @Override
        public Component createFromParcel(Parcel in) {
            return new Component(in);
        }

        @Override
        public Component[] newArray(int size) {
            return new Component[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(componentType);
        dest.writeString(description);
        dest.writeInt(price);
        dest.writeByte((byte) (selected ? 1 : 0));
    }
    public Component createUpdatedComponent(String componentType){
        return new Component(
                this.getId(),
                this.getName(),
                this.getImage(),
                componentType,
                this.getDescription(),
                this.getPrice(),
                true
        );
    };
}