package com.example.topgmeals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.topgmeals.ingredientstorage.IngredientStorage;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * This class handles the menu after the user successfully logs in, where they can choose between 4 Activities
 */
public class MainOptions extends AppCompatActivity {
    private TextView txtUsername;
    private Button btnSignOut;
    private Button btnIngredientStorage;
    private Button btnShoppingList;
    private Button btnMealPlanner;
    private Button btnRecipesBook;
    private GoogleSignInAccount account;
    private FirebaseUser curUser;

    /**
     * This class handles the layout and logic of the Activity. Called on Activity creation.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_options);

        //Check if user is already logged in
        account = GoogleSignIn.getLastSignedInAccount(this);
        curUser = FirebaseAuth.getInstance().getCurrentUser();
        txtUsername = findViewById(R.id.txtName);
        if (curUser != null) {
            txtUsername.setText(curUser.getEmail());
        }


        // 4 buttons that point to Ingredient Storage, Shopping List, Meal Planner and Recipe Book respectively
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
                Intent intent = new Intent(MainOptions.this, RecipeBook.class);
                startActivity(intent);
            }
        });

        //Sign out button
        btnSignOut = findViewById(R.id.btnSignOut);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                GoogleSignIn.getClient(
                        getApplicationContext(),
                        new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
                ).signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}