package com.example.topgmeals.recipe.book;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.topgmeals.R;
import com.example.topgmeals.ingredient.storage.Ingredient;

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
                Intent intentAddIngredient = new Intent(currentClass, IngredientRecipe.class);
                EditText description = (EditText) findViewById(R.id.description_editText);
                EditText amount = (EditText) findViewById(R.id.amount_editText);
                EditText unit = (EditText) findViewById(R.id.unit_editText);
                EditText category = (EditText) findViewById(R.id.category_editText);

                String descriptionText = description.getText().toString();
                Float amountText = Float.parseFloat(amount.getText().toString());
                String unitText = unit.getText().toString();
                String categoryText = category.getText().toString();

                Ingredient new_ingredient = new Ingredient(descriptionText,new java.util.Date(System.currentTimeMillis()),null,amountText,unitText,categoryText);
                intentAddIngredient.putExtra("NEW", new_ingredient);
                startActivity(intentAddIngredient);





            }
        });

        Button back = findViewById(R.id.back_button_ingredients);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }
}