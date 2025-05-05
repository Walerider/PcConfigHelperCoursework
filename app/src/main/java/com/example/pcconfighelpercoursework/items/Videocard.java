package com.example.pcconfighelpercoursework.items;

import android.os.Parcel;

import androidx.annotation.NonNull;

public class Videocard extends Component {
    int vramSize;
    String series;

    public Videocard(int id, String name, String image, String componentType, String description, int price, boolean selected, int vramSize, String series) {
        super(id, name, image, componentType, description, price, selected);
        this.vramSize = vramSize;
        this.series = series;
    }

    public Videocard() {
    }

    public Videocard(Parcel in) {
        super(in);
        this.vramSize = in.readInt();
        this.series = in.readString();
    }

    public Videocard(String componentType) {
        super(componentType);
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(vramSize);
        dest.writeString(series);
    }

    public int getVramSize() {
        return vramSize;
    }

    public void setVramSize(int vramSize) {
        this.vramSize = vramSize;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    @Override
    public Component createUpdatedComponent(String componentType) {
        return new Videocard(
                this.getId(),
                this.getName(),
                this.getImage(),
                componentType,
                this.getDescription(),
                this.getPrice(),
                true,
                this.getVramSize(),
                this.getSeries()
        );
    }
}
