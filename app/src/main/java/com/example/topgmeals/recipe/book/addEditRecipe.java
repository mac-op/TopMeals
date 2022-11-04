package com.example.topgmeals.recipe.book;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.topgmeals.R;
import com.example.topgmeals.recipe.book.Recipe;
import com.example.topgmeals.recipe.book.RecipeBook;


/**
 * This class is an Activity that handles the ADD functionality of the Recipe Book menu.
 */
public class addEditRecipe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_recipee);
        addEditRecipe currentClass = addEditRecipe.this;






        Button add_new=findViewById(R.id.add_recipe);
        add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_add = new Intent(currentClass, RecipeBook.class);
                EditText title = (EditText) findViewById(R.id.title_editText);
                EditText prep_time = (EditText) findViewById(R.id.prep_time_editText);
                EditText serving = (EditText) findViewById(R.id.serving_editText);
                EditText category = (EditText) findViewById(R.id.Category_editText);
                EditText comments = (EditText) findViewById(R.id.Comments_editText);

                String title_text = title.getText().toString();
                String prep_time_text = prep_time.getText().toString();
                Integer serving_text = Integer.parseInt(serving.getText().toString());
                String category_text = category.getText().toString();
                String comments_text = comments.getText().toString();

//                System.out.println(title_text);
//                System.out.println(prep_time);
//                System.out.println('3');
//                Boolean flag = true;
//                for (int i = 0; i < serving_text.toString().length(); i++) {
//                    if (!Character.isDigit(serving_text.toString().charAt(i))) {
//                        serving.setError("Enter valid servings number!");
//                        flag = false;
//                    }
//                }

                Recipe new_recipe = new Recipe(title_text, prep_time_text, serving_text, category_text, comments_text);
                intent_add.putExtra("NEW", new_recipe);
                startActivity(intent_add);


            }
        });

        Button back = findViewById(R.id.back_button_recipe);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




    }

}