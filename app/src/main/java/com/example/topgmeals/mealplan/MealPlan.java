package com.example.topgmeals.mealplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.topgmeals.R;
import com.example.topgmeals.ingredientstorage.IngredientStorage;
import com.example.topgmeals.recipebook.RecipeBook;
import com.example.topgmeals.shoppinglist.ShoppingList;

public class MealPlan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan);
        setTitle("Meal Planner");

        //Begin Region ButtonSwapping
        Button btnIngredientStorage = findViewById(R.id.switchToIngredientStorage);
        btnIngredientStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MealPlan.this, IngredientStorage.class));
                finish();
            }
        });

        Button btnShoppingList = findViewById(R.id.switchToShoppingList);
        btnShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MealPlan.this, ShoppingList.class));
                finish();
            }
        });

        Button btnMealPlanner = findViewById(R.id.switchToMealPlan);
        btnMealPlanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MealPlan.this, MealPlan.class));
            }
        });

        Button btnRecipesBook = findViewById(R.id.switchToRecipes);
        btnRecipesBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MealPlan.this, RecipeBook.class));
                finish();
            }
        });
        // End Region ButtonSwapping
    }
}