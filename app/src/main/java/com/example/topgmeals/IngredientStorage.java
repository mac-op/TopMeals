package com.example.topgmeals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class IngredientStorage extends AppCompatActivity {

    private ArrayList<Ingredient> ingredientList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_storage);

        setTitle("Ingredient Storage");

        IngredientStorage currentClass = IngredientStorage.this;
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
                Intent intent = new Intent(currentClass, Recipes.class);
                startActivity(intent);
            }
        });
        //endregion

        ingredientList = new ArrayList<>();
//        ingredientAdapter = new CustomArrayAdapter(this, ingredientList);
        RecyclerView ingredientView = findViewById(R.id.ingredient_list);
        CustomAdapter adapter = new CustomAdapter(ingredientList);
        ingredientView.setAdapter(adapter);
        ingredientView.setLayoutManager(new LinearLayoutManager(this));

        ingredientList.add(new Ingredient("1", new Date(), "l", 3, "e", "test"));
        ingredientList.add(new Ingredient("2", new Date(), "l", 3, "e", "test"));
        ingredientList.add(new Ingredient("3", new Date(), "l", 3, "e", "test"));
        ingredientList.add(new Ingredient("4", new Date(), "l", 3, "e", "test"));
        ingredientList.add(new Ingredient("5", new Date(), "l", 3, "e", "test"));
        ingredientList.add(new Ingredient("6", new Date(), "l", 3, "e", "test"));

    }

}