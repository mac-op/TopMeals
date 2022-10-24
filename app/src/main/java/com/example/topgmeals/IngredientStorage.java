package com.example.topgmeals;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class IngredientStorage extends AppCompatActivity {

    private ListView ingredientListView;
    private ArrayList<Ingredient> ingredientList;
    private ArrayAdapter<Ingredient> ingredientAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_storage);

        ingredientListView = findViewById(R.id.ingredient_list);
        ingredientList = new ArrayList<>();
        ingredientAdapter = new CustomArrayAdapter(this, ingredientList);

        ingredientListView.setAdapter(ingredientAdapter);

        ingredientList.add(new Ingredient("1", new Date(), "l", 3, "e", "test"));
        ingredientList.add(new Ingredient("2", new Date(), "l", 3, "e", "test"));
        ingredientList.add(new Ingredient("3", new Date(), "l", 3, "e", "test"));
        ingredientList.add(new Ingredient("4", new Date(), "l", 3, "e", "test"));
        ingredientList.add(new Ingredient("5", new Date(), "l", 3, "e", "test"));
        ingredientList.add(new Ingredient("6", new Date(), "l", 3, "e", "test"));

    }
}