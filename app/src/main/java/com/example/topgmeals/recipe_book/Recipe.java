package com.example.topgmeals.recipe_book;

import android.widget.ImageView;
import java.io.Serializable;
import android.os.Parcel;


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
    private String uid;

    /**
     * Constructor for {@link Recipe}
     */
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

    /**
     * Gets the recipe title from the recipe
     * @return
     *      Returns title of recipe
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the recipe title for the recipe
     * @param title
     *
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the preparation time from the recipe
     * @return
     *      Returns preparation time of recipe
     */
    public String getPrepTime() {
        return prepTime;
    }

    /**
     * Sets the preparation time of the recipe
     * @param prepTime
     */
    public void setPrepTime(String prepTime) {
        this.prepTime = prepTime;
    }

    /**
     * Gets the servings value from the recipe
     *  @return
     *      Returns servings of recipe
     */
    public Integer getServings() {
        return servings;
    }

    /**
     * Sets the serving value of the recipe
     * @param servings
     */
    public void setServings(int servings) {
        this.servings = servings;
    }

    /**
     * Gets the recipe category
     *  @return
     *      Returns category of recipe
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the recipe category
     * @param category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets the comments of the recipe
     *  @return
     *      Returns comments of recipe
     */
    public String getComments() {
        return comments;
    }

    /**
     * Sets comments for the recipe
     * @param comments
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * Gets the picture of the recipe
     *  @return
     *      Returns picture of recipe
     */
    public ImageView getPicture() {
        return picture;
    }

    /**
     * Sets the picture for the recipe
     * @param picture
     */
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