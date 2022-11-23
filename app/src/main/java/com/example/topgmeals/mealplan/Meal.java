package com.example.topgmeals.mealplan;

public class Meal {
    private String mealName;
    private int numServings;

    public Meal(){}

    public Meal(String mealName, int numServings) {
        this.mealName = mealName;
        this.numServings = numServings;
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
}
