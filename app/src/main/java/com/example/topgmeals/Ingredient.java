package com.example.topgmeals;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Ingredient implements Parcelable {
    private String description;
    private Date bestBefore;
    private String location;
    private float amount;
    private String unit;
    private String category;


    public Ingredient(String description, Date bestBefore, String location, float amount, String unit,
                      String category) {
        this.description = description;
        this.bestBefore = bestBefore;
        this.location = location;
        this.amount = amount;
        this.unit = unit;
        this.category = category;
    }


    protected Ingredient(Parcel in) {
        description = in.readString();
        bestBefore = new Date(in.readLong());
        location = in.readString();
        amount = in.readFloat();
        unit = in.readString();
        category = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getBestBefore() {
        return bestBefore;
    }

    public void setBestBefore(Date bestBefore) {
        this.bestBefore = bestBefore;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(description);
        parcel.writeLong(bestBefore.getTime());
        parcel.writeString(location);
        parcel.writeFloat(amount);
        parcel.writeString(unit);
        parcel.writeString(category);
    }
}
