package com.example.topgmeals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IngredientsDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_ingredient);
        IngredientsDisplay currentClass = IngredientsDisplay.this;

        Intent intent = getIntent();
        String descriptionToDisplay = intent.getExtras().getString("DESCRIPTION");
        String amountToDisplay = intent.getExtras().getString("AMOUNT");
        Integer unitToDisplay = intent.getExtras().getInt("UNIT");
        String categoryToDisplay = intent.getExtras().getString("CATEGORY");



        Button backButton = (Button) findViewById(R.id.back_button_modify_ingredient);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button update = (Button) findViewById(R.id.update_ingredient);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, IngredientRecipe.class);
                startActivity(intent);
            }
        });

        Button delete = (Button) findViewById(R.id.delete_ingredient);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, IngredientRecipe.class);
                startActivity(intent);
            }
        });


    }
}