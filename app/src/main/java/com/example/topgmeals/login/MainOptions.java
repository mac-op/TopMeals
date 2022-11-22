package com.example.topgmeals.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.topgmeals.R;
import com.example.topgmeals.ingredientstorage.IngredientStorage;
import com.example.topgmeals.mealplan.MealPlan;
import com.example.topgmeals.recipebook.RecipeBook;
import com.example.topgmeals.shoppinglist.ShoppingList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * This class handles the menu after the user successfully logs in, where they can choose between 4 Activities
 */
public class MainOptions extends AppCompatActivity {
    private TextView textViewUsername;
    private Button btnLogout;
    private Button btnIngredientStorage;
    private Button btnShoppingList;
    private Button btnMealPlanner;
    private Button btnRecipesBook;
    private String userId;
    private FirebaseFirestore db;

    /**
     * This class handles the layout and logic of the Activity. Called on Activity creation.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_options);

        // Hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        textViewUsername = findViewById(R.id.txtName);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();
        final CollectionReference userDocRef = db.collection("users");
        userDocRef.whereEqualTo("uid", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                textViewUsername.setText(document.get("username").toString());
                            }
                        } else {
                            Toast.makeText(MainOptions.this,
                                    "Failed to get username!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

        // 4 buttons that point to Ingredient Storage, Shopping List, Meal Planner and Recipe Book respectively
        btnIngredientStorage = findViewById(R.id.btnOptionIngredientStorage);
        btnIngredientStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainOptions.this, IngredientStorage.class));
            }
        });

        btnShoppingList = findViewById(R.id.btnOptionShoppingList);
        btnShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainOptions.this, ShoppingList.class));
            }
        });

        btnMealPlanner = findViewById(R.id.btnOptionMealPlanner);
        btnMealPlanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainOptions.this, MealPlan.class));
            }
        });

        btnRecipesBook = findViewById(R.id.btnOptionRecipesBook);
        btnRecipesBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainOptions.this, RecipeBook.class));
            }
        });

        // Logout button
        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainOptions.this, MainActivity.class));
                finish();
            }
        });
    }
}