package com.example.topgmeals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class RecipeDisplay extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipee_information);

//        Intent intent = getIntent();
//        String titleToDisplay = intent.getExtras().getString("TITLE");
//        String prepTimeToDisplay = intent.getExtras().getString("PREP_TIME");
//        Integer servingsToDisplay = intent.getExtras().getInt("SERVINGS");
//        String categoryToDisplay = intent.getExtras().getString("CATEGORY");
//        String commentsToDisplay = intent.getExtras().getString("COMMENTS");



        Button backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}