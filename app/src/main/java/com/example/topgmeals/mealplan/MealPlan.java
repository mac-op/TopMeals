package com.example.topgmeals.mealplan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.topgmeals.R;
import com.example.topgmeals.ingredientstorage.IngredientStorage;
import com.example.topgmeals.recipebook.RecipeBook;
import com.example.topgmeals.shoppinglist.ShoppingList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is an Activity that handles the Meal Planner menu. The user will be able to see
 * a list of planned meals grouped by their dates and can choose to add or delete each meal.
 */
public class MealPlan extends AppCompatActivity implements ExpandableListAdapter.OnDeletePressedListener {

    /**
     * {@link ArrayList} that holds the dates where there are meals.
     */
    ArrayList<String> dates;

    /**
     * {@link HashMap} where each key is a date that has meals, and each value is an
     * {@link ArrayList} of {@link Meal} that belong to each date.
     */
    HashMap<String, ArrayList<Meal>> mealList;

    /**
     * The Firestore authentication ID of the user.
     */
    String userID;
    CollectionReference mealCollection;

    /**
     *  This method gets called when the Activity is created. It creates the layouts
     *  and handles the logic for the Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mealplan);
        setTitle("Meal Planner");

        // Initialize variables
        dates = new ArrayList<>();
        mealList = new HashMap<>();

        // Set adapter for ExpandableListView
        ExpandableListView mealListView = findViewById(R.id.expandable_meal_plan);
        ExpandableListAdapter adapter = new ExpandableListAdapter(this, dates, mealList);
        mealListView.setAdapter(adapter);

        // Set up Firestore connection and Snapshot listener
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mealCollection = db.collection("mealplan");
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mealCollection.whereEqualTo("id", userID).addSnapshotListener((value, error) -> {
            dates.clear();
            mealList.clear();
            for (QueryDocumentSnapshot doc: value){
                Meal meal = doc.toObject(Meal.class);
                meal.setDocRef(doc.getId());
                String date = doc.getString("date");

                if (!dates.contains(date)){
                    dates.add(date);
                    mealList.put(date, new ArrayList<>());
                }
                mealList.get(date).add(meal);
            }
            adapter.notifyDataSetChanged();
        });

        // Add Button. Calls AddMealActivity where the user can add a new meal
        Button addButton = findViewById(R.id.add_meal_button);
        addButton.setOnClickListener(view -> {
            Intent addIntent = new Intent(getBaseContext(), AddMealActivity.class);
            startActivity(addIntent);
        });

        // Begin Region Activity Swapping
        Button IngredientButton = (Button) findViewById(R.id.switchToIngredientStorage);
        IngredientButton.setOnClickListener(view -> {
            startActivity(new Intent(MealPlan.this, IngredientStorage.class));
            finish();
        });

        Button btnShoppingList = findViewById(R.id.switchToShoppingList);
        btnShoppingList.setOnClickListener(view -> {
            startActivity(new Intent(MealPlan.this, ShoppingList.class));
            finish();
        });

        Button btnMealPlanner = findViewById(R.id.switchToMealPlan);
        btnMealPlanner.setOnClickListener(view -> {
            startActivity(new Intent(MealPlan.this, MealPlan.class));
        });

        Button btnRecipesBook = findViewById(R.id.switchToRecipes);
        btnRecipesBook.setOnClickListener(view -> {
            startActivity(new Intent(MealPlan.this, RecipeBook.class));
            finish();
        });
        // End Region Activity Swapping
    }

    @Override
    public void deleteMeal(String docRef) {
        mealCollection.document(docRef).delete()
                .addOnSuccessListener(unused -> Log.d("DELETE MEAL", "Delete success"))
                .addOnFailureListener(e -> Log.d("DELETE MEAL", "Delete failed"));
    }
}