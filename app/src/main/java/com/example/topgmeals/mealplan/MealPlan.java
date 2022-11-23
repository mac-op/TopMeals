package com.example.topgmeals.mealplan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.example.topgmeals.R;
import com.example.topgmeals.ingredientstorage.IngredientStorage;
import com.example.topgmeals.recipebook.RecipeBook;
import com.example.topgmeals.shoppinglist.ShoppingList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MealPlan extends AppCompatActivity {
    ArrayList<MealDate> dateList;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mealplan);
        setTitle("Meal Planner");

        dateList = new ArrayList<>();

//        http://theopentutorials.com/tutorials/android/listview/android-expandable-list-view-example/
        ExpandableListView mealListView = findViewById(R.id.expandable_meal_plan);
        MealPlan currentClass = MealPlan.this;

        ExpandableListAdapter adapter = new ExpandableListAdapter(this, dateList);
        mealListView.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference mealCollection = db.collection("mealplan");
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mealCollection.whereEqualTo("id", userID).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                dateList.clear();
                for (QueryDocumentSnapshot doc: value){
                    MealDate date = doc.toObject(MealDate.class);
                    dateList.add(date);
                }
                adapter.notifyDataSetChanged();
            }
        });

        Button addButton = findViewById(R.id.add_meal_button);
        addButton.setOnClickListener(view -> {
            Intent addIntent = new Intent(getBaseContext(), AddMealActivity.class);
            startActivity(addIntent);
       });

        //region ButtonSwapping
        Button IngredientButton = (Button) findViewById(R.id.switchToIngredientStorage);
        IngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MealPlan.this, IngredientStorage.class));
                finish();
            }
        });

        Button btnShoppingList = findViewById(R.id.switchToShoppingList);
        btnShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MealPlan.this, ShoppingList.class));
                finish();
            }
        });

        Button btnMealPlanner = findViewById(R.id.switchToMealPlan);
        btnMealPlanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MealPlan.this, MealPlan.class));
            }
        });

        Button btnRecipesBook = findViewById(R.id.switchToRecipes);
        btnRecipesBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MealPlan.this, RecipeBook.class));
                finish();
            }
        });
        // endregion
    }
}