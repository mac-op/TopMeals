package com.example.topgmeals.recipebook;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.topgmeals.R;
import com.example.topgmeals.mealplan.ingredientstorage.Ingredient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
/**
 * This class is an Activity that handles the ADD functionality of the Ingredients of a Recipe where
 * user can add a new {@link Ingredient} to a {@link Recipe}. Called by {@link IngredientRecipe}
 */
public class AddIngredientRecipe extends AppCompatActivity {
    String RecipeID;

    /**
     * Method to handle layout of the Activity when it is created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_ingredient_recipee);
        AddIngredientRecipe currentClass = AddIngredientRecipe.this;
        setTitle("New Ingredient Information");

        Intent Rintent = getIntent();
        RecipeID = Rintent.getExtras().getString("rID");

        /* Performing the add ingredient button functionality */
        Button addIngredient = findViewById(R.id.add_ingredient);
        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddIngredient = new Intent(currentClass, IngredientRecipe.class);
                EditText description = (EditText) findViewById(R.id.description_editText);
                EditText amount = (EditText) findViewById(R.id.amount_editText);
                EditText unit = (EditText) findViewById(R.id.unit_editText);
                EditText category = (EditText) findViewById(R.id.category_editText);

                // Description validation
                String descriptionText = description.getText().toString();
                if ((descriptionText.trim()).isEmpty()) {
                    description.setError("Description is required!");
                    description.requestFocus();
                    return;
                }

                // Amount validation and setting its value
                float amountText;
                if ((amount.getText().toString().trim()).isEmpty()) {
                    amount.setError("Amount is required!");
                    amount.requestFocus();
                    return;
                } else if (Float.parseFloat(amount.getText().toString()) == 0) {
                    amount.setError("Amount cannot be 0!");
                    amount.requestFocus();
                    return;
                } else {
                    amountText = Float.parseFloat(amount.getText().toString());
                }

                // Unit validation
                String unitText = unit.getText().toString();
                if ((unitText.trim()).isEmpty()) {
                    unit.setError("Unit is required!");
                    unit.requestFocus();
                    return;
                }

                // Category validation
                String categoryText = category.getText().toString();
                if ((categoryText.trim()).isEmpty()) {
                    category.setError("Category is required!");
                    category.requestFocus();
                    return;
                }

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
                                Log.d(TAG, "DocumentSnapshot written with ID: " +
                                        documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });
                intentAddIngredient.putExtra("RECIPE_ID", RecipeID);
                startActivity(intentAddIngredient);
            }
        });

        // When the user wants to discard changes and go back to Ingredients For Recipe
        Button cancel = findViewById(R.id.cancel_ingredient_recipe_button);
        cancel.setOnClickListener(view -> {
            AlertDialog.Builder cancelDialog =
                    new AlertDialog.Builder(AddIngredientRecipe.this);
            cancelDialog.setMessage("Do you want to discard changes and return to Ingredients " +
                            "For Recipe?").setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog alertCancel = cancelDialog.create();
            alertCancel.setTitle("Discard Changes");
            alertCancel.show();
        });
    }
}