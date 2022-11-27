package com.example.topgmeals.ingredientstorage;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * This is a class to represent an ingredient. It implements interface {@link Parcelable} to be sent
 * between Activities.
 */
public class Ingredient implements Parcelable {
    /**
     * The Ingredient's description.
     */
    private String description;

    /**
     * The Ingredient's best before date.
     */
    private Date bestBefore;

    /**
     * The Ingredient's location
     */
    private String location;

    /**
     * Amount of the Ingredient
     */
    private float amount;

    /**
     * Unit of the Ingredient
     */
    private String unit;

    /**
     * The Ingredient's category
     */
    private String category;
    private String documentID;

    /**
     * No-argument constructor for Ingredient
     */
    public Ingredient() {

    }

    /**
     * Constructor for {@link Ingredient}
     */
    public Ingredient(String description, Date bestBefore, String location, float amount, String unit,
                      String category, String did) {
        this.description = description;
        this.bestBefore = bestBefore;
        this.location = location;
        this.amount = amount;
        this.unit = unit;
        this.category = category;
        this.documentID = did;
    }

    /**
     * Method to unpack the {@link Parcel} to get an {@link Ingredient}
     */
    protected Ingredient(Parcel in) {
        description = in.readString();
        bestBefore = new Date(in.readLong());
        location = in.readString();
        amount = in.readFloat();
        unit = in.readString();
        category = in.readString();
        documentID = in.readString();
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

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Method to convert the Ingredient into a Parcel
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(description);
        parcel.writeLong(bestBefore.getTime());
        parcel.writeString(location);
        parcel.writeFloat(amount);
        parcel.writeString(unit);
        parcel.writeString(category);
        parcel.writeString(documentID);
    }

}