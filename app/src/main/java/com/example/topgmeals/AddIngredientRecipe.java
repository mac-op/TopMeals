package com.example.topgmeals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddIngredientRecipe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ingredient_recipee);
        AddIngredientRecipe currentClass = AddIngredientRecipe.this;


        Button addIngredient = (Button) findViewById(R.id.add_ingredient);
        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intentAddIngredient = new Intent(AddIngredientRecipe.this)
                EditText name = (EditText) findViewById(R.id.name_editText);
                EditText description = (EditText) findViewById(R.id.description_editText);
                EditText quantity = (EditText) findViewById(R.id.quantity_editText);
                EditText category = (EditText) findViewById(R.id.category_editText);

                String nameText = name.getText().toString();
                String descriptionText = description.getText().toString();
                Integer quantityText = Integer.parseInt(quantity.getText().toString());
                String categoryText = category.getText().toString();

                IngredientRecipe newIngredient = new IngredientRecipe(nameText, descriptionText, quantityText, categoryText);



            }
        });

        Button back = findViewById(R.id.back_button_ingredient_recipe);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
}