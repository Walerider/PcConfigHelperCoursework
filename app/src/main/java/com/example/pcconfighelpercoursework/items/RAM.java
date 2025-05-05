package com.example.pcconfighelpercoursework.items;

import android.os.Parcel;

import androidx.annotation.NonNull;

public class RAM extends Component {
    int capacity;
    String memoryType;

    public RAM(int id,  String name, String image, String componentType, String description, int price, boolean selected, int capacity, String memoryType) {
        super(id,  name, image, componentType, description, price, selected);
        this.capacity = capacity;
        this.memoryType = memoryType;
    }

    public RAM() {
    }

    public RAM(Parcel in) {
        super(in);
        this.capacity = in.readInt();
        this.memoryType = in.readString();
    }

    public RAM(String componentType) {
        super(componentType);
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(capacity);
        dest.writeString(memoryType);
    }

    @Override
    public int describeContents() {
        return super.describeContents();
    }

    public int getCapacity() {
        return capacity;
    }

    public String getMemoryType() {
        return memoryType;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setMemoryType(String memoryType) {
        this.memoryType = memoryType;
    }


    @Override
    public Component createUpdatedComponent(String componentType) {
        return new RAM(
                this.getId(),
                this.getName(),
                this.getImage(),
                componentType,
                this.getDescription(),
                this.getPrice(),
                true,
                this.getCapacity(),
                this.getMemoryType()
        );
    }
}
