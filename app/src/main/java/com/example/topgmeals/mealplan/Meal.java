package com.example.topgmeals.mealplan;

public class Meal {
    private String mealName;
    private int numServings;
    private String docRef;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    public Meal(){}

    public Meal(String mealName, int numServings, String docRef, String type) {
        this.mealName = mealName;
        this.numServings = numServings;
        this.docRef = docRef;
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
