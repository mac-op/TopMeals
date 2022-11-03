package com.example.topgmeals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.topgmeals.ingredientstorage.IngredientStorage;

public class InitialHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_home);


        InitialHome currentClass = InitialHome.this;
        //region ButtonSwapping
        Button IngredientButton = (Button) findViewById(R.id.homeIngredientStorage);
        IngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, IngredientStorage.class);
                startActivity(intent);
            }
        });

        Button ShoppingButton = (Button) findViewById(R.id.homeShoppingList);

        ShoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, ShoppingList.class);
                startActivity(intent);
            }
        });

        Button MealPlanButton = (Button) findViewById(R.id.homeMealPlanner);

        MealPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, MealPlan.class);
                startActivity(intent);
            }
        });

        Button RecipesButton = (Button) findViewById(R.id.homeRecipesBook);

        RecipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, Recipes.class);
                startActivity(intent);
            }
        });
        //endregion
    }
}