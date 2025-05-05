package com.example.pcconfighelpercoursework.items;

import android.os.Parcel;

import androidx.annotation.NonNull;

public class Cases extends Component {
    String formFactor;

    public Cases(int id, String name, String image, String componentType, String description, int price, boolean selected, String formFactor) {
        super(id, name, image, componentType, description, price, selected);
        this.formFactor = formFactor;
    }

    public Cases(Parcel in) {
        super(in);
        this.formFactor = in.readString();
    }

    public Cases(String componentType) {
        super(componentType);
    }

    public Cases() {
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(formFactor);
    }

    public String getFormFactor() {
        return formFactor;
    }

    public void setFormFactor(String formFactor) {
        this.formFactor = formFactor;
    }

    @Override
    public Component createUpdatedComponent(String componentType) {
        return new Cases(
                this.getId(),
                this.getName(),
                this.getImage(),
                componentType,
                this.getDescription(),
                this.getPrice(),
                true,
                this.getFormFactor()
        );
    }
}

