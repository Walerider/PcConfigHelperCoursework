package com.example.pcconfighelpercoursework.items;

import android.os.Parcel;

import androidx.annotation.NonNull;

import com.example.pcconfighelpercoursework.api.items.ProductAttributeDAO;

import java.util.List;

public class CPUCooler extends Component {


    public CPUCooler(int id, String name, String image, String componentType, String description, int price, boolean selected, List<ProductAttributeDAO> attributes) {
        super(id, name, image, componentType, description, price, selected, attributes);
    }

    public CPUCooler(int id, String name, String description, String componentType, int price, List<ProductAttributeDAO> attributes) {
        super(id, name, description, componentType, price, attributes);
    }

    public CPUCooler(Parcel in) {
        super(in);
    }

    public CPUCooler(String componentType) {
        super(componentType);
    }

    public CPUCooler() {
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }


    @Override
    public Component createUpdatedComponent(String componentType) {
        return new CPUCooler(
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
