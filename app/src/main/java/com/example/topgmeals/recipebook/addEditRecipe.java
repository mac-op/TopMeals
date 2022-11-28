package com.example.topgmeals.recipebook;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.topgmeals.R;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
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
public class addEditRecipe extends AppCompatActivity {

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
        addEditRecipe currentClass = addEditRecipe.this;

        mImageView = findViewById(R.id.recipeImage);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        /* Performing the add recipe button functionality */
        Button add_new = findViewById(R.id.add_recipe);
        add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_add = new Intent(currentClass, RecipeBook.class);
                EditText title = (EditText) findViewById(R.id.title_editText);
                EditText prep_time = (EditText) findViewById(R.id.prep_time_editText);
                EditText serving = (EditText) findViewById(R.id.serving_editText);
                EditText category = (EditText) findViewById(R.id.Category_editText);
                EditText comments = (EditText) findViewById(R.id.Comments_editText);

                String title_text = title.getText().toString();
                if (title_text.isEmpty()) {
                    title.setError("Title is required!");
                    title.requestFocus();
                    return;
                }

                String prep_time_text = prep_time.getText().toString();
                if (prep_time_text.isEmpty()) {
                    prep_time.setError("Preparation time is required!");
                    prep_time.requestFocus();
                    return;
                }
                if (prep_time_text.compareTo("0 mins")==0){
                    prep_time.setError("Preparation time Cannot be 0!");
                    prep_time.requestFocus();
                    return;
                }

                if (serving.getText().toString().equals("")) {
                    serving.setError("Servings is required!");
                    serving.requestFocus();
                    return;
                }
                Integer serving_text = Integer.parseInt(serving.getText().toString());
                if (serving_text.equals(0)) {
                    serving.setError("Servings Cannot be 0!");
                    serving.requestFocus();
                    return;
                }

                String category_text = category.getText().toString();
                if (category_text.isEmpty()) {
                    category.setError("Category is required!");
                    category.requestFocus();
                    return;
                }

                String comments_text = comments.getText().toString();
                if (comments_text.isEmpty()) {
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
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());

                                StorageReference uploadRef = mStorageRef.child("uploads/" + documentReference.getId().toString());
                                UploadTask uploadTask = uploadRef.putFile(mImageUri);


                                // Register observers to listen for when the download is done or if it fails
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle unsuccessful uploads
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                                        // ...
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });
                startActivity(intent_add);
            }
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
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            mImageUri = data.getData();
            mImageView.setImageURI(mImageUri);

        }
    }
}