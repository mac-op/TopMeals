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


/**
 * This class is an Activity that handles the ADD functionality of the Recipe Book menu.
 */
public class addEditRecipe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_recipee);
        addEditRecipe currentClass = addEditRecipe.this;


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
                String prep_time_text = prep_time.getText().toString();
                Integer serving_text = Integer.parseInt(serving.getText().toString());
                String category_text = category.getText().toString();
                String comments_text = comments.getText().toString();


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
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });

                // Recipe new_recipe =new Recipe(title_text,prep_time_text,3,category_text,comments_text, "si");

                startActivity(intent_add);


            }
        });

        Button back = findViewById(R.id.back_button_recipe);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(currentClass, RecipeBook.class);
                startActivity(intent);
                currentClass.finish();
            }
        });




    }

}