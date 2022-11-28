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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is an Activity that handles the Ingredients display menu for a recipe. The user will
 * be able to see the information of each ingredient, and delete an ingredient for a recipe.
 */
public class IngredientsDisplay extends AppCompatActivity {
    /**
     *  This method gets called when the Activity is created. It creates the layouts
     *  and handles the logic for displaying a {@link IngredientRecipe}.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_ingredient);
        setTitle("Modify Recipe Ingredient");

        IngredientsDisplay currentClass = IngredientsDisplay.this;

        EditText description = findViewById(R.id.description_editText);
        EditText amount = findViewById(R.id.amount_editText);
        EditText units = findViewById(R.id.unit_editText);
        EditText category = findViewById(R.id.Category_editText);

        Intent getIntent = getIntent();

        String descriptionToDisplay = getIntent.getExtras().getString("DESCRIPTION");
        String amountToDisplay = getIntent.getExtras().getString("AMOUNT");
        String unitToDisplay = getIntent.getExtras().getString("UNIT");
        String categoryToDisplay = getIntent.getExtras().getString("CATEGORY");
        String ingredientId = getIntent.getExtras().getString("docID");
        String recipeId = getIntent.getExtras().getString("recipeID");
        int position = getIntent.getIntExtra("POSITION",-1);

        description.setText(descriptionToDisplay);
        amount.setText(amountToDisplay);
        units.setText(unitToDisplay);
        category.setText(categoryToDisplay);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        /* Performs the update ingredient button functionality */
        Button update = (Button) findViewById(R.id.update_ingredient);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> data = new HashMap<>();

                // Description validation
                data.put("description", description.getText().toString());
                if ((description.getText().toString().trim()).isEmpty()) {
                    description.setError("Description is required!");
                    description.requestFocus();
                    return;
                }

                // Need to provide default values for best before and location
                data.put("bestBefore", "11/11/22");
                data.put("location", "N/A");

                // Amount validation and setting its value
                if ((amount.getText().toString().trim()).isEmpty()) {
                    amount.setError("Amount is required!");
                    amount.requestFocus();
                    return;
                } else if (Float.parseFloat(amount.getText().toString()) == 0) {
                    amount.setError("Amount cannot be 0!");
                    amount.requestFocus();
                    return;
                }
                data.put("amount", Float.valueOf(amount.getText().toString()));

                // Unit validation
                data.put("unit", units.getText().toString());
                if ((units.getText().toString().trim()).isEmpty()) {
                    units.setError("Unit is required!");
                    units.requestFocus();
                    return;
                }

                // Category validation
                data.put("category", category.getText().toString());
                if ((category.getText().toString().trim()).isEmpty()) {
                    category.setError("Category is required!");
                    category.requestFocus();
                    return;
                }

                data.put("id", recipeId);

                db.collection("recipeIngredients").document(ingredientId)
                        .set(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                            }
                        });

                Intent intent = new Intent(currentClass, IngredientRecipe.class);
                intent.putExtra("RECIPE_ID", recipeId);
                startActivity(intent);
            }
        });

        /* Performs the delete ingredient button functionality */
        Button delete = findViewById(R.id.delete_ingredient);
        delete.setOnClickListener(view -> {
            AlertDialog.Builder cancelDialog =
                    new AlertDialog.Builder(IngredientsDisplay.this);
            cancelDialog.setMessage("Are you sure you want to delete this recipe " +
                            "ingredient?").setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            db.collection("recipeIngredients").document(ingredientId)
                                    .delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error deleting document", e);
                                        }
                                    });

                            Intent intent = new Intent(currentClass, IngredientRecipe.class);
                            intent.putExtra("RECIPE_ID", recipeId);
                            startActivity(intent);
                            currentClass.finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog alertCancel = cancelDialog.create();
            alertCancel.setTitle("Delete Confirmation");
            alertCancel.show();
        });
    }
}