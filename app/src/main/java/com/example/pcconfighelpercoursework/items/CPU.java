package com.example.pcconfighelpercoursework.items;

import android.os.Parcel;

import androidx.annotation.NonNull;

import com.example.pcconfighelpercoursework.api.items.ProductAttributeDAO;

import java.util.List;
import java.util.Map;

public class CPU extends Component {
    public CPU(Parcel in) {
        super(in);
    }

    public CPU() {
    }

    public CPU(int id, String name, String image, String componentType, String description, int price, boolean selected, Map<String,String> attributes) {
        super(id, name, image, componentType, description, price, selected, attributes);
    }

    public CPU(int id, String name, String description, String componentType, int price) {
        super(id, name, description, componentType, price);
    }

    public CPU(String componentType) {
        super(componentType);
    }

    public CPU(int id, String name, String description, String componentType, int price, Map<String,String> attributes) {
        super(id, name, description, componentType, price, attributes);
    }

    @Override
    public int describeContents() {
        return super.describeContents();
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }



    @Override
    public Component createUpdatedComponent(String componentType) {
        return new CPU(
                this.getId(),
                this.getName(),
                this.getImage(),
                componentType,
                this.getDescription(),
                this.getPrice(),
                true,
                this.getAttributes()
        );
    }

}
