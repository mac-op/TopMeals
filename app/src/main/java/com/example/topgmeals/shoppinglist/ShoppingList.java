package com.example.topgmeals.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.topgmeals.mealplan.MealPlan;
import com.example.topgmeals.R;
import com.example.topgmeals.recipebook.RecipeBook;
import com.example.topgmeals.ingredientstorage.IngredientStorage;

public class ShoppingList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        setTitle("Shopping List");

        // Begin Region ButtonSwapping
        Button btnIngredientStorage = findViewById(R.id.switchToIngredientStorage);
        btnIngredientStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShoppingList.this, IngredientStorage.class));
                finish();
            }
        });

        Button btnShoppingList = findViewById(R.id.switchToShoppingList);
        btnShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShoppingList.this, ShoppingList.class));
            }
        });

        Button btnMealPlanner = findViewById(R.id.switchToMealPlan);
        btnMealPlanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShoppingList.this, MealPlan.class));
                finish();
            }
        });

        Button btnRecipesBook = findViewById(R.id.switchToRecipes);
        btnRecipesBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShoppingList.this, RecipeBook.class));
                finish();
            }
        });
        // End Region ButtonSwapping
    }
}