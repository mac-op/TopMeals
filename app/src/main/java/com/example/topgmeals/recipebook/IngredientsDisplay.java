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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is an Activity that handles the Ingredients display menu for a recipe. The user will be able to see
 * the information of each ingredient, and delete an ingredient for a recipe.
 */
public class IngredientsDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_ingredient);
        IngredientsDisplay currentClass = IngredientsDisplay.this;

        EditText description = (EditText) findViewById(R.id.description_editText);
        EditText amount = (EditText) findViewById(R.id.amount_editText);
        EditText units = (EditText) findViewById(R.id.unit_editText);
        EditText category = (EditText) findViewById(R.id.Category_editText);


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



        Button backButton = (Button) findViewById(R.id.back_button_modify_ingredient);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button update = (Button) findViewById(R.id.update_ingredient);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> data = new HashMap<>();
                data.put("description", description.getText().toString());
                data.put("bestBefore", "11/11/22");
                data.put("location", "fridge");
                data.put("amount", Float.valueOf(amount.getText().toString()));
                data.put("unit", units.getText().toString());
                data.put("category", category.getText().toString());
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


        Button delete = (Button) findViewById(R.id.delete_ingredient);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

            }
        });

    }
}