package com.example.topgmeals.mealplan;

import java.util.ArrayList;

public class MealDate {
    private String date;
    private ArrayList<Meal> meals;

    public MealDate(){}

    public MealDate(String date, ArrayList<Meal> meals) {
        this.date = date;
        this.meals = meals;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Meal> getMeals() {
        return meals;
    }

    public int getSize(){
        return meals.size();
    }

    public Meal getMeal(int i){
        return meals.get(i);
    }

    public void addMeal(Meal meal){
        meals.add(meal);
    }

    public interface MealName {
        String getMealName();
        int getMealServings();
    }
}
