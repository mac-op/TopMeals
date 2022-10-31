package com.example.topgmeals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class RecipeDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipee_information);
        Intent intent = getIntent();
        String title = intent.getStringExtra("TITLE");
        String prep_time = intent.getStringExtra("PREP_TIME");
        String servings = intent.getStringExtra("SERVINGS");
        String category= intent.getStringExtra("CATEGORY");
        String comments = intent.getStringExtra("COMMENTS");

        EditText title_text = findViewById(R.id.title_editText);
        EditText prep_time_text= findViewById(R.id.PreparationTime_editText);
        EditText servings_text= findViewById(R.id.servings_editText);
        EditText category_text = findViewById(R.id.Category_editText);
        EditText comments_text= findViewById(R.id.Comments_editText);

        title_text.setText(title);
        prep_time_text.setText(prep_time);
        servings_text.setText(servings);
        category_text.setText(category);
        comments_text.setText(comments);
    }
}