package com.example.topgmeals;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.topgmeals.ingredientstorage.Ingredient;

import java.util.ArrayList;

public class IngredientRecipe extends AppCompatActivity {

    ListView ingredientsList;
    ArrayList<Ingredient> ingredientsRecipeBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        ingredientsList = (ListView) findViewById(R.id.ingredient_recipe);
        ingredientsRecipeBook = new ArrayList<>();
        Ingredient beef = new Ingredient("Beef", null, null,5,"5","Meat");
        ingredientsRecipeBook.add(beef);

        IngredientRecipeAdapter ingredientListAdapter = new IngredientRecipeAdapter(this, R.layout.ingredients_recipe_content, ingredientsRecipeBook);
        ingredientsList.setAdapter(ingredientListAdapter);



        IngredientRecipe currentClass = IngredientRecipe.this;

        ActivityResultLauncher<Intent> viewIngredient = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == 2){
                        Intent intent = result.getData();
                        int position = intent.getIntExtra("POSITION", -1);
                        assert (position != -1);
                        ingredientsRecipeBook.remove(position);
                        ingredientListAdapter.notifyDataSetChanged();
                    }

                }

        );

        ingredientsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Intent new_intent=getIntent();


                Intent intent = new Intent(currentClass, IngredientsDisplay.class);
                //String name = recipeBook.get(i).getTitle();
                String description = ingredientsRecipeBook.get(i).getDescription();
                Float amount = ingredientsRecipeBook.get(i).getAmount();
                String s_amount = amount.toString();
                String unit = ingredientsRecipeBook.get(i).getUnit();
                String category = ingredientsRecipeBook.get(i).getCategory();



                intent.putExtra("DESCRIPTION",description);
                intent.putExtra("AMOUNT",s_amount);
                intent.putExtra("UNIT",unit);
                intent.putExtra("CATEGORY",category);
                intent.putExtra("POSITION",i);




                //startActivity(intent);
                viewIngredient.launch(intent);
            }
        });




        Button addIngredient = (Button) findViewById(R.id.add_ingredient_recipe);
        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(currentClass, AddIngredientRecipe.class);
                startActivity(intent);
            }
        });





        Button backButton = (Button) findViewById(R.id.back_button_ingredient_recipe);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Ingredient new_ingredient= (Ingredient) getIntent().getParcelableExtra("NEW");
        if (new_ingredient!=null){
            ingredientsRecipeBook.add(new_ingredient);

        }



    }
}