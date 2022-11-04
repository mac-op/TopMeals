package com.example.topgmeals.recipe.book;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.topgmeals.R;

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


        Recipe recipe_object = (Recipe) intent.getSerializableExtra("OBJECT");



        title.setText(recipe_object.getTitle());
        prepTime.setText(recipe_object.getPrepTime());
        servings.setText(recipe_object.getServings().toString());
        category.setText(recipe_object.getCategory());
        comments.setText(recipe_object.getComments());
        int position = intent.getIntExtra("POSITION",-1);




        Button ingredients = (Button) findViewById(R.id.ingredients_button);
        ingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, IngredientRecipe.class);
                startActivity(intent);
            }
        });

        Button edit_recipe = (Button) findViewById(R.id.edit_recipe_button);
        edit_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipe_object.setTitle(title.getText().toString());
                recipe_object.setPrepTime(prepTime.getText().toString());
                recipe_object.setServings(Integer.valueOf(servings.getText().toString()));
                recipe_object.setCategory(category.getText().toString());
                recipe_object.setComments(comments.getText().toString());

                Intent intent = new Intent();
                intent.putExtra("POSITION", position);
                intent.putExtra("UPDATED OBJECT",recipe_object);
                setResult(3,intent);
                finish();
            }
        });

        Button delete = (Button) findViewById(R.id.delete_recipe);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("POSITION", position);
                setResult(2,intent);
                finish();


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