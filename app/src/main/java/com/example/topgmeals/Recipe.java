package com.example.topgmeals;

import android.widget.ImageView;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Serializable {
    private String title;
    private String prepTime;
    private int servings;
    private String category;
    private String comments;
    private ImageView picture;
    private String uid;

    public Recipe (String title, String prepTime, int servings, String category, String comments, String id) {
        this.title = title;
        this.prepTime = prepTime;
        this.servings = servings;
        this.category = category;
        this.comments = comments;
        this.uid = id;
        //this.picture = picture;

    }
    protected Recipe(Parcel in) {
        title = in.readString();
        prepTime=in.readString();
        servings=in.readInt();
        category=in.readString();
        comments=in.readString();

    }




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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
