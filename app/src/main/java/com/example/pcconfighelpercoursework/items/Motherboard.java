package com.example.pcconfighelpercoursework.items;

import android.os.Parcel;

import androidx.annotation.NonNull;

import com.example.pcconfighelpercoursework.api.items.ProductAttributeDAO;

import java.util.List;

public class Motherboard extends Component {


    public Motherboard(int id, String name, String image, String componentType, String description, int price, boolean selected, List<ProductAttributeDAO> attributes) {
        super(id, name, image, componentType, description, price, selected, attributes);
    }

    public Motherboard(int id, String name, String description, String componentType, int price, List<ProductAttributeDAO> attributes) {
        super(id, name, description, componentType, price, attributes);
    }

    public Motherboard() {
    }

    public Motherboard(Parcel in) {
        super(in);
    }

    public Motherboard(int id, String name, String description, String componentType, int price) {
        super(id, name, description, componentType, price);
    }

    public Motherboard(String componentType) {
        super(componentType);
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @Override
    public int describeContents() {
        return super.describeContents();
    }

    @Override
    public Component createUpdatedComponent(String componentType) {
        return new Motherboard(
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
