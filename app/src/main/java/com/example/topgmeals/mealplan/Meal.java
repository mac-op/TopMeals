package com.example.topgmeals.mealplan;

/**
 * This class represents a meal that each user can plan from either Ingredients or Recipes
 */
public class Meal {

    /**
     * Name of the meal
     */
    private String mealName;

    /**
     * Number of servings of the meal
     */
    private int numServings;

    /**
     * The DocumentReference of the Ingredient or Recipe that was used to create this Meal
     */
    private String docRef;

    /**
     * Whether this Meal is a Recipe or an Ingredient
     */
    private String type;

    public Meal(){}

    public Meal(String mealName, int numServings, String docRef, String type) {
        this.mealName = mealName;
        this.numServings = numServings;
        this.docRef = docRef;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public int getNumServings() {
        return numServings;
    }

    public void setNumServings(int numServings) {
        this.numServings = numServings;
    }

    public String getDocRef() {
        return docRef;
    }

    public void setDocRef(String docRef) {
        this.docRef = docRef;
    }
}
