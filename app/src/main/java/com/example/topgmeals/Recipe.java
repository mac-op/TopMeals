package com.example.topgmeals;

import android.widget.ImageView;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable {
    private String title;
    private String prepTime;
    private int servings;
    private String category;
    private String comments;
    private ImageView picture;

    public Recipe (String title, String prepTime, int servings, String category, String comments) {
        this.title = title;
        this.prepTime = prepTime;
        this.servings = servings;
        this.category = category;
        this.comments = comments;
        //this.picture = picture;

    }
    protected Recipe(Parcel in) {
        title = in.readString();
        prepTime=in.readString();
        servings=in.readInt();
        category=in.readString();
        comments=in.readString();

    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(String prepTime) {
        this.prepTime = prepTime;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ImageView getPicture() {
        return picture;
    }

    public void setPicture(ImageView picture) {
        this.picture = picture;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(prepTime);
        parcel.writeInt(servings);
        parcel.writeString(category);
        parcel.writeString(comments);

    }
}
