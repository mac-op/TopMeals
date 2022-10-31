package com.example.topgmeals;

import android.widget.ImageView;

import java.io.Serializable;

public class Recipe implements Serializable {
    private String title;
    private String prepTime;
    private Integer servings;
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

    public Integer getServings() {
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





}
