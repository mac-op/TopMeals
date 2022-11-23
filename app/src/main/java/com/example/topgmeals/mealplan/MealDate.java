package com.example.topgmeals.mealplan;

import java.util.ArrayList;

public class MealDate {
    private String date;
    private ArrayList<Object> meals;

    public MealDate(){}

    public MealDate(String date, ArrayList<Object> meals) {
        this.date = date;
        this.meals = meals;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Object> getMeals() {
        return meals;
    }

    public void setMeals(ArrayList<Object> meals) {
        this.meals = meals;
    }

    public int getSize(){
        return meals.size();
    }

    public Object getMeal(int i){
        return meals.get(i);
    }

    public interface MealName {
        String getMealName();
        int getMealServings();
    }
}
