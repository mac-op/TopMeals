package com.example.topgmeals.main.login.options;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.topgmeals.meal.plan.MealPlan;
import com.example.topgmeals.R;
import com.example.topgmeals.recipe.book.RecipeBook;
import com.example.topgmeals.shopping.list.ShoppingList;
import com.example.topgmeals.ingredient.storage.IngredientStorage;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainOptions extends AppCompatActivity {
    private TextView txtUsername;
    private Button btnSignOut;
    private Button btnIngredientStorage;
    private Button btnShoppingList;
    private Button btnMealPlanner;
    private Button btnRecipesBook;
    private GoogleSignInAccount account;
    private FirebaseUser curUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_options);

        account = GoogleSignIn.getLastSignedInAccount(this);
        curUser = FirebaseAuth.getInstance().getCurrentUser();
        txtUsername = findViewById(R.id.txtName);
        if (curUser != null) {

            txtUsername.setText(curUser.getEmail());
        }

//        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        Log.d("iddddddddddddddd", id);

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