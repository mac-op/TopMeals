package com.example.topgmeals.recipe.book;

import android.widget.ImageView;
import java.io.Serializable;


/**
 * This is a class to represent a recipe. It implements interface {@link Serializable} to be sent
 * between Activities.
 */
public class Recipe implements Serializable {
    private String title;
    private String prepTime;
    private Integer servings;
    private String category;
    private String comments;
    private ImageView picture;

    /**
     * Constructor for {@link Recipe}
     */
    public Recipe (String title, String prepTime, int servings, String category, String comments) {
        this.title = title;
        this.prepTime = prepTime;
        this.servings = servings;
        this.category = category;
        this.comments = comments;

    }


    /**
     * Gets the recipe title from the recipe
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the recipe title for the recipe
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the preparation time from the recipe
     */
    public String getPrepTime() {
        return prepTime;
    }

    /**
     * Sets the preparation time o
     */
    public void setPrepTime(String prepTime) {
        this.prepTime = prepTime;
    }

    /**
     * Gets the recipe title from the recipe
     */
    public Integer getServings() {
        return servings;
    }

    /**
     * Gets the recipe title from the recipe
     */
    public void setServings(int servings) {
        this.servings = servings;
    }

    /**
     * Gets the recipe title from the recipe
     */
    public String getCategory() {
        return category;
    }

    /**
     * Gets the recipe title from the recipe
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets the recipe title from the recipe
     */
    public String getComments() {
        return comments;
    }

    /**
     * Gets the recipe title from the recipe
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * Gets the recipe title from the recipe
     */
    public ImageView getPicture() {
        return picture;
    }

    /**
     * Gets the recipe title from the recipe
     */
    public void setPicture(ImageView picture) {
        this.picture = picture;
    }
}