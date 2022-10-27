package com.example.topgmeals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class MainOptions extends AppCompatActivity {
    private TextView txtUsername;
    private Button btnSignOut;
    private Button btnIngredientStorage;
    private Button btnShoppingList;
    private Button btnMealPlanner;
    private Button btnRecipesBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_options);

        txtUsername = findViewById(R.id.txtName);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            txtUsername.setText(account.getDisplayName());
        }

        btnIngredientStorage = findViewById(R.id.btnOptionIngredientStorage);
        btnIngredientStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainOptions.this, IngredientStorage.class);
                startActivity(intent);
            }
        });

        btnShoppingList = findViewById(R.id.btnOptionShoppingList);
        btnShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainOptions.this, ShoppingList.class);
                startActivity(intent);
            }
        });

        btnMealPlanner = findViewById(R.id.btnOptionMealPlanner);
        btnMealPlanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainOptions.this, MealPlan.class);
                startActivity(intent);
            }
        });

        btnRecipesBook = findViewById(R.id.btnOptionRecipesBook);
        btnRecipesBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainOptions.this, Recipes.class);
                startActivity(intent);
            }
        });

        btnSignOut = findViewById(R.id.btnSignOut);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainOptions.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}