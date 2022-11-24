package com.example.topgmeals.mealplan;

public class Meal {
    private String mealName;
    private int numServings;
    private String docRef;

    public Meal(){}

    public Meal(String mealName, int numServings, String docRef) {
        this.mealName = mealName;
        this.numServings = numServings;
        this.docRef = docRef;
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
