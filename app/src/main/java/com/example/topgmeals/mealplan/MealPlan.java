package com.example.topgmeals.mealplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.example.topgmeals.R;
import com.example.topgmeals.ingredientstorage.IngredientStorage;
import com.example.topgmeals.recipebook.RecipeBook;
import com.example.topgmeals.shoppinglist.ShoppingList;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MealPlan extends AppCompatActivity {
    private ExpandableListAdapter adapter;
    private ExpandableListView mealListView;
    ArrayList<String> dateList;
    HashMap<String, ArrayList<String>> mealsByDate = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mealplan);

//        http://theopentutorials.com/tutorials/android/listview/android-expandable-list-view-example/
        mealListView = findViewById(R.id.expandable_meal_plan);
        MealPlan currentClass = MealPlan.this;

        //TODO: Custom class for meals by date??
        //region mock data
        ArrayList<String> date1 = new ArrayList<String>();
        date1.add("Pizza");
        date1.add("Pizza");

        ArrayList<String> date2 = new ArrayList<String>();
        date2.add("Hamburger");
        date2.add("Chicken");

        ArrayList<String> date3 = new ArrayList<String>();
        date3.add("United States of Pizzas");
        date3.add("The Pizza Strikes Back");

        ArrayList<String> date4 = new ArrayList<String>();
        date4.add("United States of Pizzas");
        date4.add("The Pizza Strikes Back");

        ArrayList<String> date5 = new ArrayList<String>();
        date5.add("United States of Pizzas");
        date5.add("The Pizza Strikes Back");

        ArrayList<String> date6 = new ArrayList<String>();
        date6.add("United States of Pizzas");
        date6.add("The Pizza Strikes Back");


        mealsByDate.put("date1", date1);
        mealsByDate.put("date2", date2);
        mealsByDate.put("date3", date3);
        mealsByDate.put("date4", date4);
        mealsByDate.put("date5", date5);
        mealsByDate.put("date6", date6);

        dateList = new ArrayList<>(mealsByDate.keySet());
        //endregion

        adapter = new ExpandableListAdapter(this, dateList, mealsByDate);
        mealListView.setAdapter(adapter);

        //region ButtonSwapping
        Button IngredientButton = (Button) findViewById(R.id.switchToIngredientStorage);
        IngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, IngredientStorage.class);
                startActivity(intent);
            }
        });

        Button ShoppingButton = (Button) findViewById(R.id.switchToShoppingList);

        ShoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, ShoppingList.class);
                startActivity(intent);
            }
        });

        Button MealPlanButton = (Button) findViewById(R.id.switchToMealPlan);

        MealPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, MealPlan.class);
                startActivity(intent);
            }
        });

        Button RecipiesButton = (Button) findViewById(R.id.switchToRecipes);

        RecipiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, RecipeBook.class);
                startActivity(intent);
            }
        });
        //endregion


    }
}