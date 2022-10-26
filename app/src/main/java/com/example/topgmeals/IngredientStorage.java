package com.example.topgmeals;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class IngredientStorage extends AppCompatActivity {

    private ArrayList<Ingredient> ingredientList;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_storage);

        setTitle("Ingredient Storage");

        IngredientStorage currentClass = IngredientStorage.this;
        //region ButtonSwapping
        Button IngredientButton = (Button) findViewById(R.id.switchToIngredientStorage);
        IngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, IngredientStorage.class);
                startActivity(intent);
            }
        });

        Button ShoppingButton = (Button) findViewById(R.id.switchToShoppingList);

        ShoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, ShoppingList.class);
                startActivity(intent);
                finish();
            }
        });

        Button MealPlanButton = (Button) findViewById(R.id.switchToMealPlan);

        MealPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, MealPlan.class);
                startActivity(intent);
            }
        });

        Button RecipiesButton = (Button) findViewById(R.id.switchToRecipes);

        RecipiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, Recipes.class);
                startActivity(intent);
            }
        });
        //endregion

        ingredientList = new ArrayList<>();
        RecyclerView ingredientView = findViewById(R.id.ingredient_list);
        adapter = new CustomAdapter(ingredientList);
        ingredientView.setAdapter(adapter);
        ingredientView.setLayoutManager(new LinearLayoutManager(this));

        ActivityResultLauncher<Intent> editActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // DELETE
                    if (result.getResultCode() == 2) {
                        Intent deleteIntent = result.getData();
                        int pos = deleteIntent.getIntExtra("delete_position", -1);
                        assert (pos!=-1);
                        ingredientList.remove(pos);
                        adapter.notifyItemRemoved(pos);
                    }
                    // EDIT
                    else if (result.getResultCode() == Activity.RESULT_OK){
                        Intent editIntent = result.getData();
                        Ingredient ingredient = editIntent.getParcelableExtra("edited_ingredient");
                        int pos = editIntent.getIntExtra("edited_position", -1);
                        assert (pos != -1);
                        ingredientList.set(pos, ingredient);
                        adapter.notifyItemChanged(pos);
                    }
                });

        View.OnClickListener onItemClickListener = view -> {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int pos = viewHolder.getAbsoluteAdapterPosition();
            Ingredient ingredient = ingredientList.get(pos);

            Intent intent = new Intent(getBaseContext(), AddEditIngredientActivity.class);
            intent.putExtra("purpose", "EDIT");
            intent.putExtra("ingredient_object",ingredient);
            intent.putExtra("position", pos);
            editActivityResultLauncher.launch(intent);
        };
        adapter.setOnItemClickListener(onItemClickListener);


        ingredientView.setOnClickListener(view -> {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();

            int position = viewHolder.getAdapterPosition();
            Log.println(Log.ASSERT,"", String.valueOf(position));
        });
        ActivityResultLauncher<Intent> addActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        assert data != null;
                        Ingredient ingredient = data.getParcelableExtra("added_ingredient");
                        ingredientList.add(ingredient);
                        adapter.notifyItemInserted(ingredientList.size()-1);
                    }
                });

        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(view -> {
            Intent intent = new Intent(currentClass, AddEditIngredientActivity.class);
            intent.putExtra("purpose", "ADD");
            addActivityResultLauncher.launch(intent);
        });

    }

}