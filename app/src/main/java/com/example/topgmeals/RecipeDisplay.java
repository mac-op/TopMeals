package com.example.topgmeals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class RecipeDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipee_information);
        RecipeDisplay currentClass = RecipeDisplay.this;

        EditText title = (EditText) findViewById(R.id.title_editText);
        EditText prepTime = (EditText) findViewById(R.id.PreparationTime_editText);
        EditText servings = (EditText) findViewById(R.id.servings_editText);
        EditText category = (EditText) findViewById(R.id.Category_editText);
        EditText comments = (EditText) findViewById(R.id.Comments_editText);


        Intent intent = getIntent();
        String titleToDisplay = intent.getExtras().getString("TITLE");
        String prepTimeToDisplay = intent.getExtras().getString("PREP_TIME");
        Integer servingsToDisplay = intent.getExtras().getInt("SERVINGS");
        String categoryToDisplay = intent.getExtras().getString("CATEGORY");
        String commentsToDisplay = intent.getExtras().getString("COMMENTS");

        title.setText(titleToDisplay);
        prepTime.setText(prepTimeToDisplay);
        servings.setText(servingsToDisplay.toString()); // Not displaying right servings value
        category.setText(categoryToDisplay);
        comments.setText(commentsToDisplay);



        Button ingredients = (Button) findViewById(R.id.ingredients_button);
        ingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, IngredientRecipe.class);
                startActivity(intent);
            }
        });

        Button delete = (Button) findViewById(R.id.delete_recipe);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, IngredientRecipe.class);

                startActivity(intent);
            }
        });


        Button backButton = (Button) findViewById(R.id.button_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}