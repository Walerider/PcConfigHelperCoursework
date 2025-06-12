package com.example.pcconfighelpercoursework.items;

import android.os.Parcel;

import androidx.annotation.NonNull;

import com.example.pcconfighelpercoursework.api.items.ProductAttributeDAO;

import java.util.List;
import java.util.Map;

public class PowerSupply extends Component {

    public PowerSupply(int id, String name, String image, String componentType, String description, int price, boolean selected, Map<String,String> attributes) {
        super(id, name, image, componentType, description, price, selected, attributes);
    }

    public PowerSupply(int id, String name, String image, String description, String componentType, int price, Map<String, String> attributes) {
        super(id, name, image, description, componentType, price, attributes);
    }

    public PowerSupply() {
    }

    public PowerSupply(String componentType) {
        super(componentType);
    }

    public PowerSupply(Parcel in) {
        super(in);
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
        return new PowerSupply(
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
