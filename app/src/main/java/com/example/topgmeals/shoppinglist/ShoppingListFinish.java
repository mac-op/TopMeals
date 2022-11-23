package com.example.topgmeals.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.topgmeals.R;
import com.example.topgmeals.ingredientstorage.Ingredient;
import com.example.topgmeals.ingredientstorage.IngredientAdapter;
import com.example.topgmeals.recipebook.RecipeAdapter;

import java.util.ArrayList;

public class ShoppingListFinish extends AppCompatActivity {

    private ArrayList<Ingredient> inCart;
    private IngredientAdapter ingredientAdapter;
    private RecyclerView ingredientView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_finish);


        inCart = ShoppingGlobalVars.getInstance().getList();

        ingredientView = findViewById(R.id.shoppingFinishListView);
        ingredientAdapter = new IngredientAdapter(inCart);
        ingredientView.setAdapter(ingredientAdapter);



        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        ingredientView.setLayoutManager(layoutManager);
        androidx.recyclerview.widget.DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(ingredientView.getContext(),
                layoutManager.getOrientation());
        ingredientView.addItemDecoration(dividerItemDecoration);


        ingredientAdapter.notifyDataSetChanged();




    }
}