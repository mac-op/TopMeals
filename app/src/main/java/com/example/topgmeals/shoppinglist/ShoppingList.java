package com.example.topgmeals.shoppinglist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.topgmeals.ingredientstorage.Ingredient;
import com.example.topgmeals.mealplan.MealPlan;
import com.example.topgmeals.R;
import com.example.topgmeals.recipebook.Recipe;
import com.example.topgmeals.recipebook.RecipeAdapter;
import com.example.topgmeals.recipebook.RecipeBook;
import com.example.topgmeals.ingredientstorage.IngredientStorage;

import java.util.ArrayList;
import java.util.Collections;

public class ShoppingList extends AppCompatActivity implements ShoppingListAdapter.ItemClickListener {

    private RecyclerView shoppingListView;
    private ArrayList<Ingredient> shoppingList;
    private ArrayList<Ingredient> fullshoppingList;
    private Boolean check=Boolean.FALSE;
    private ShoppingListAdapter shoppingListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);


        // Initialize variables for ListView
        shoppingListView = findViewById(R.id.shoppingListView);
        shoppingListView.setLayoutManager(new LinearLayoutManager(this));
        shoppingListView.addItemDecoration(
                new DividerItemDecoration(this, null));
        shoppingList = new ArrayList<>();
        shoppingListAdapter = new ShoppingListAdapter(this, shoppingList);


        Ingredient burger = new Ingredient("Ice Cream Manyoasasdasdds", null, "freezer", (float)2.3, "4 lbs", "Dairyatrozics", "s");
        Ingredient pizza = new Ingredient("Steak", null, "freezer", (float)8.9, "2kg", "Meat", "s");
        Ingredient burger2 = new Ingredient("Ice Cream", null, "freezer", (float)2.3, "4 lbs", "Dairy", "s");
        Ingredient burger3 = new Ingredient("Ice Cream", null, "freezer", (float)2.3, "4 lbs", "Abrs", "s");
        Ingredient burger4 = new Ingredient("Ace Cream", null, "freezer", (float)2.3, "4 lbs", "stakle", "s");
        Ingredient burger5 = new Ingredient("ice Cream", null, "freezer", (float)2.3, "4 lbs", "sno", "s");
        Ingredient burger6 = new Ingredient("Ays", null, "freezer", (float)2.3, "4 lbs", "Pleas", "s");

        shoppingList.add(burger);

        shoppingList.add(pizza);
        shoppingList.add(burger2);
        shoppingList.add(burger3);
        shoppingList.add(burger4);
        shoppingList.add(burger5);
        shoppingList.add(burger6);

        fullshoppingList = (ArrayList<Ingredient> ) shoppingList.clone();

        shoppingListAdapter.setClickListener(this);
        Log.e("t", "load" + Integer.toString( shoppingList.size()));
        shoppingListView.setAdapter(shoppingListAdapter);

        Toast.makeText(this, "Swipe right or check the Checkbox to cross off the item. Press 'Done Shopping when finished.", Toast.LENGTH_LONG).show();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                Log.e("e", "SUIIIII");
//                Log.e("e",Integer.toString( shoppingListAdapter.getItemCount()));
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                shoppingList.remove(viewHolder.getLayoutPosition());
                shoppingListAdapter.notifyDataSetChanged();
                Intent intent = new Intent();
                intent.putExtra("test", shoppingList);

            }
        });

        itemTouchHelper.attachToRecyclerView(shoppingListView);

        Button sortByDescription = (Button) findViewById(R.id.shopSortDescription);
        sortByDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(shoppingList, (d1, d2) -> d1.getDescription().compareTo(d2.getDescription()));
                shoppingListAdapter.notifyDataSetChanged();

            }
        });

        Button sortByCategory = (Button) findViewById(R.id.shopSortCategory);
        sortByCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(shoppingList, (d1, d2) -> d1.getCategory().compareTo(d2.getCategory()));
                shoppingListAdapter.notifyDataSetChanged();

            }
        });

        Button finishShopping = (Button) findViewById(R.id.shopFinishShopping);
        finishShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoppingList.this, ShoppingListFinish.class);

                Log.e("e",Integer.toString( shoppingList.size()));
                Log.e("e",Integer.toString( fullshoppingList.size()));

                intent.putExtra("Endshoplist", shoppingList);
                intent.putExtra("Fullshoplist", fullshoppingList);

                startActivity(intent);
                finish();

            }
        });



        ShoppingList currentClass = ShoppingList.this;
        //region ButtonSwapping
        Button IngredientButton = (Button) findViewById(R.id.switchToIngredientStorage);
        IngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, IngredientStorage.class);
                finish();
                startActivity(intent);
            }
        });

        Button ShoppingButton = (Button) findViewById(R.id.switchToShoppingList);

        ShoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, ShoppingList.class);
                finish();
                startActivity(intent);
            }
        });

        Button MealPlanButton = (Button) findViewById(R.id.switchToMealPlan);

        MealPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, MealPlan.class);
                finish();
                startActivity(intent);
            }
        });

        Button RecipesButton = (Button) findViewById(R.id.switchToRecipes);

        RecipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, RecipeBook.class);
                finish();
                startActivity(intent);
            }
        });
        //endregion

    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + shoppingListAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}