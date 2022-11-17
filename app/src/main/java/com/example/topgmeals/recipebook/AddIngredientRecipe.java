package com.example.topgmeals.recipebook;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.topgmeals.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import com.example.topgmeals.ingredientstorage.Ingredient;
/**
 * This class is an Activity that handles the ADD functionality of the Ingredients of a Recipe.
 */
public class AddIngredientRecipe extends AppCompatActivity {

    String RecipeID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ingredient_recipee);
        AddIngredientRecipe currentClass = AddIngredientRecipe.this;

        Intent Rintent = getIntent();
        RecipeID = Rintent.getExtras().getString("rID");


        Button addIngredient = (Button) findViewById(R.id.add_ingredient);
        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddIngredient = new Intent(currentClass, IngredientRecipe.class);
                EditText description = (EditText) findViewById(R.id.description_editText);
                EditText amount = (EditText) findViewById(R.id.amount_editText);
                EditText unit = (EditText) findViewById(R.id.unit_editText);
                EditText category = (EditText) findViewById(R.id.category_editText);

                String descriptionText = description.getText().toString();
                Float amountText = Float.parseFloat(amount.getText().toString());
                String unitText = unit.getText().toString();
                String categoryText = category.getText().toString();

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                Map<String, Object> data = new HashMap<>();
                data.put("description", descriptionText);
                data.put("bestBefore", "11/11/22");
                data.put("location", "fridge");
                data.put("amount", amountText);
                data.put("unit", unitText);
                data.put("category", categoryText);
                data.put("id", RecipeID);

                db.collection("recipeIngredients")
                        .add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });



                // Ingredient new_ingredient = new Ingredient(descriptionText,new java.util.Date(System.currentTimeMillis()),null,amountText,unitText,categoryText, "ape");
                // intentAddIngredient.putExtra("NEW", new_ingredient);
                intentAddIngredient.putExtra("RECIPE_ID", RecipeID);
                startActivity(intentAddIngredient);


            }
        });

        Button back = findViewById(R.id.back_button_ingredients);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }
}