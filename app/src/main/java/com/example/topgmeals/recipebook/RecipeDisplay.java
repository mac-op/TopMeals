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
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.topgmeals.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is an Activity that handles the Recipe Display menu. The user will be able to see
 * the information of each recipe, and edit and delete a recipe.
 */
public class RecipeDisplay extends AppCompatActivity {

    private StorageReference mStorageRef;

    /**
     *  This method gets called when the Activity is created. It creates the layouts
     *  and handles the logic for displaying a {@link Recipe}.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipee_information);
        RecipeDisplay currentClass = RecipeDisplay.this;
        setTitle("Recipe Information");

        mStorageRef = FirebaseStorage.getInstance().getReference();

        EditText title = findViewById(R.id.title_editText);
        EditText prepTime = findViewById(R.id.PreparationTime_editText);
        EditText servings = findViewById(R.id.servings_editText);
        EditText category = findViewById(R.id.Category_editText);
        EditText comments = findViewById(R.id.Comments_editText);

        Intent dataIntent = getIntent();

        String titleToDisplay = dataIntent.getExtras().getString("TITLE");
        String prepTimeToDisplay = dataIntent.getExtras().getString("PREP_TIME");
        String servingsToDisplay = dataIntent.getStringExtra("SERVINGS");
        String categoryToDisplay = dataIntent.getExtras().getString("CATEGORY");
        String commentsToDisplay = dataIntent.getExtras().getString("COMMENTS");
        String recipeID = dataIntent.getExtras().getString("RecipeID");

        title.setText(titleToDisplay);
        prepTime.setText(prepTimeToDisplay);
        servings.setText(servingsToDisplay);
        category.setText(categoryToDisplay);
        comments.setText(commentsToDisplay);

        ImageView recImg = findViewById(R.id.recImage);
        mStorageRef.child("uploads/" + recipeID).getDownloadUrl().addOnSuccessListener(uri -> {
            // Got the download URL for 'users/me/profile.png'
            Log.e("madeit", uri.toString());
            Glide.with(RecipeDisplay.this).load(uri.toString()).into(recImg);

            try {
                Glide.with(RecipeDisplay.this).load(uri.toString()).into(recImg);
            } catch (Exception e){
                Log.e("Error", e.toString());
            }
        }).addOnFailureListener(exception -> {
            if (recImg.getDrawable() == null){
                recImg.setImageResource(R.drawable.meal_plan_64);
            }
        });

        Button ingredients = findViewById(R.id.ingredients_button);
        ingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, IngredientRecipe.class);

                intent.putExtra("RECIPE_ID", recipeID);
                startActivity(intent);
            }
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        /* Performs the edit recipe button functionality */
        Button edit_recipe = findViewById(R.id.update_recipe);
        edit_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> data = new HashMap<>();

                // Title validation
                data.put("title", title.getText().toString());
                if ((title.getText().toString().trim()).isEmpty()) {
                    title.setError("Title is required!");
                    title.requestFocus();
                    return;
                }

                // Prep time validation
                data.put("prepTime", prepTime.getText().toString());
                if ((prepTime.getText().toString()).isEmpty()) {
                    prepTime.setError("Preparation time is required!");
                    prepTime.requestFocus();
                    return;
                } else if (Integer.parseInt(prepTime.getText().toString()) == 0){
                    prepTime.setError("Preparation time cannot be 0!");
                    prepTime.requestFocus();
                    return;
                }

                // Serving validation
                data.put("servings", Integer.valueOf(servings.getText().toString()));
                if ((servings.getText().toString().trim()).isEmpty()) {
                    servings.setError("Servings is required!");
                    servings.requestFocus();
                    return;
                } else if (Integer.valueOf(servings.getText().toString()) == 0) {
                    servings.setError("Servings Cannot be 0!");
                    servings.requestFocus();
                    return;
                }

                // Category validation
                data.put("category", category.getText().toString());
                if ((category.getText().toString().trim()).isEmpty()) {
                    category.setError("Category is required!");
                    category.requestFocus();
                    return;
                }

                // Comments validation
                data.put("comments", comments.getText().toString());
                if ((comments.getText().toString().trim()).isEmpty()) {
                    comments.setError("Comments is required!");
                    comments.requestFocus();
                    return;
                }
                data.put("id", uid);

                db.collection("recipes").document(recipeID)
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
                Intent intent = new Intent(currentClass, RecipeBook.class);
                startActivity(intent);
            }
        });

        /* Performs the delete recipe button functionality */
        Button delete = findViewById(R.id.delete_recipe);
        delete.setOnClickListener(view -> {
            AlertDialog.Builder deleteDialog =
                    new AlertDialog.Builder(RecipeDisplay.this);
            deleteDialog.setMessage("Are you sure you want to delete this " +
                            "recipe?").setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            db.collection("recipes").document(recipeID)
                                    .delete()
                                    .addOnSuccessListener(aVoid -> Log.d(TAG,
                                            "DocumentSnapshot successfully deleted!"))
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error deleting document", e);
                                        }
                                    });

                            db.collection("mealplan").whereEqualTo("ref", recipeID)
                                    .get()
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            for (DocumentSnapshot document : task.getResult()) {
                                                db.collection("mealplan").document(document.getId()).delete();
                                            }
                                        } else {
                                            Log.d("DELETE MEAL",
                                                    "Error getting documents: ",
                                                    task.getException());
                                        }
                                    });
                            Intent intent = new Intent(currentClass, RecipeBook.class);
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
            AlertDialog alertDelete = deleteDialog.create();
            alertDelete.setTitle("Delete Confirmation");
            alertDelete.show();
        });
    }
}