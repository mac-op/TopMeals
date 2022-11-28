package com.example.topgmeals.recipebook;

import static android.content.ContentValues.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.topgmeals.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;


/**
 * This class is an Activity that handles the ADD functionality of the Recipe Book menu where user can add a
 * new {@link Recipe}. Called by {@link RecipeBook}
 */
public class AddEditRecipe extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;

    private ImageView mImageView;
    private StorageReference mStorageRef;

    /**
     * Method to handle layout of the Activity when it is created
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_recipee);
        setTitle("Add Recipe");
        AddEditRecipe currentClass = AddEditRecipe.this;

        mImageView = findViewById(R.id.recipeImage);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        /* Performing the add recipe button functionality */
        Button add_new = findViewById(R.id.save_recipe_button);
        add_new.setOnClickListener(view -> {
            Intent intent_add = new Intent(currentClass, RecipeBook.class);
            EditText title = (EditText) findViewById(R.id.title_editText);
            EditText prep_time = (EditText) findViewById(R.id.prep_time_editText);
            EditText serving = (EditText) findViewById(R.id.serving_editText);
            EditText category = (EditText) findViewById(R.id.Category_editText);
            EditText comments = (EditText) findViewById(R.id.Comments_editText);

            // Title validation
            String title_text = title.getText().toString();
            if ((title_text.trim()).isEmpty()) {
                title.setError("Title is required!");
                title.requestFocus();
                return;
            }

            // Prep Time validation
            String prep_time_text = prep_time.getText().toString().trim();
            if ((prep_time_text.trim()).isEmpty()) {
                prep_time.setError("Preparation time is required!");
                prep_time.requestFocus();
                return;
            }
            if (Integer.parseInt(prep_time_text) == 0){
                prep_time.setError("Preparation time cannot be 0!");
                prep_time.requestFocus();
                return;
            }

            // Serving validation
            if ((serving.getText().toString().trim()).isEmpty()) {
                serving.setError("Servings is required!");
                serving.requestFocus();
                return;
            }
            Integer serving_text = Integer.parseInt(serving.getText().toString());
            if (serving_text.equals(0)) {
                serving.setError("Servings cannot be 0!");
                serving.requestFocus();
                return;
            }

            // Category validation
            String category_text = category.getText().toString();
            if ((category_text.trim()).isEmpty()) {
                category.setError("Category is required!");
                category.requestFocus();
                return;
            }

            // Comments validation
            String comments_text = comments.getText().toString();
            if ((comments_text.trim()).isEmpty()) {
                comments.setError("Comments is required!");
                comments.requestFocus();
                return;
            }

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            Map<String, Object> data = new HashMap<>();
            data.put("title", title_text);
            data.put("prepTime", prep_time_text);
            data.put("servings", serving_text);
            data.put("category", category_text);
            data.put("comments", comments_text);
            data.put("id", uid);

            db.collection("recipes")
                    .add(data)
                    .addOnSuccessListener(documentReference -> {
                        Log.d(TAG, "DocumentSnapshot written with ID: " +
                                documentReference.getId());

                        StorageReference uploadRef = mStorageRef.child("uploads/" +
                                documentReference.getId());

                        if (mImageUri!=null) {
                            UploadTask uploadTask = uploadRef.putFile(mImageUri);
                            // Register observers to listen for when the download is done or
                            // if it fails
                            uploadTask.addOnFailureListener(exception -> {})
                                    .addOnSuccessListener(taskSnapshot -> {});
                        }
                    })
                    .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
            startActivity(intent_add);
        });

        // When the user wants to discard changes and go back to RecipeBook
        Button cancel_recipe = findViewById(R.id.cancel_recipe_button);
        cancel_recipe.setOnClickListener(view -> {
            AlertDialog.Builder cancelDialog = new AlertDialog.Builder(AddEditRecipe.this);
            cancelDialog.setMessage("Do you want to discard changes and return to " +
                            "Recipes Book?").setCancelable(true)
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

        // Importing picture for recipe
        Button ImportImage = findViewById(R.id.import_button);
        ImportImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null &&
                data.getData() != null){
            mImageUri = data.getData();
            mImageView.setImageURI(mImageUri);
        }
    }
}