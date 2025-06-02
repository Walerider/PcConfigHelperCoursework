package com.example.pcconfighelpercoursework.items;

import android.os.Parcel;

import androidx.annotation.NonNull;

import com.example.pcconfighelpercoursework.api.items.ProductAttributeDAO;

import java.util.List;
import java.util.Map;

public class StorageDevice extends Component {
    public StorageDevice(int id, String name, String image, String componentType, String description, int price, boolean selected, Map<String,String> attributes) {
        super(id, name, image, componentType, description, price, selected, attributes);
    }

    public StorageDevice(int id, String name, String description, String componentType, int price, Map<String,String> attributes) {
        super(id, name, description, componentType, price, attributes);
    }

    public StorageDevice(Parcel in) {
        super(in);
    }

    public StorageDevice() {
    }

    public StorageDevice(String componentType) {
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
        return new StorageDevice(
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
