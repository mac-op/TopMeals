package com.example.topgmeals.ingredientstorage;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.topgmeals.DateFormat;
import com.example.topgmeals.MealPlan;
import com.example.topgmeals.R;
import com.example.topgmeals.Recipes;
import com.example.topgmeals.ShoppingList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.installations.FirebaseInstallations;

import java.util.ArrayList;
import java.util.HashMap;

public class IngredientStorage extends AppCompatActivity {

    private ArrayList<Ingredient> ingredientList;
    private CustomAdapter adapter;
    FirebaseApp app;
    SharedPreferences sharedPreferences;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_storage);

        app = FirebaseApp.initializeApp(IngredientStorage.this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference ingredientsDb = db.collection("ingredients");
        final DateFormat format = new DateFormat();
        sharedPreferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        id = sharedPreferences.getString("Installation ID", "");
        Log.d("IngID", id);
        setTitle("Ingredient Storage");

        IngredientStorage currentClass = IngredientStorage.this;
        //region ButtonSwapping
        Button IngredientButton = (Button) findViewById(R.id.switchToIngredientStorage);
        IngredientButton.setOnClickListener(view -> {
            Intent intent = new Intent(currentClass, IngredientStorage.class);
            startActivity(intent);
        });

        Button ShoppingButton = (Button) findViewById(R.id.switchToShoppingList);

        ShoppingButton.setOnClickListener(view -> {
            Intent intent = new Intent(currentClass, ShoppingList.class);
            startActivity(intent);
            finish();
        });

        Button MealPlanButton = (Button) findViewById(R.id.switchToMealPlan);

        MealPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, MealPlan.class);
                startActivity(intent);
            }
        });

        Button RecipiesButton = (Button) findViewById(R.id.switchToRecipes);

        RecipiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, Recipes.class);
                startActivity(intent);
            }
        });
        //endregion

        ingredientList = new ArrayList<>();
        RecyclerView ingredientView = findViewById(R.id.ingredient_list);
        adapter = new CustomAdapter(ingredientList);
        ingredientView.setAdapter(adapter);
        ingredientView.setLayoutManager(new LinearLayoutManager(this));

//        getInstallationID();
        ActivityResultLauncher<Intent> editActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // DELETE
                    if (result.getResultCode() == 2) {
                        Intent deleteIntent = result.getData();
                        int pos = deleteIntent.getIntExtra("delete_position", -1);
                        assert (pos!=-1);
                        ingredientList.remove(pos);
                        adapter.notifyItemRemoved(pos);

                    }
                    // EDIT
                    else if (result.getResultCode() == Activity.RESULT_OK){
                        Intent editIntent = result.getData();
                        Ingredient ingredient = editIntent.getParcelableExtra("edited_ingredient");
                        int pos = editIntent.getIntExtra("edited_position", -1);
                        assert (pos != -1);
                        ingredientList.set(pos, ingredient);
                        adapter.notifyItemChanged(pos);
                    }
                });

        View.OnClickListener onItemClickListener = view -> {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int pos = viewHolder.getAbsoluteAdapterPosition();
            Ingredient ingredient = ingredientList.get(pos);

            Intent intent = new Intent(getBaseContext(), AddEditIngredientActivity.class);
            intent.putExtra("purpose", "EDIT");
            intent.putExtra("ingredient_object",ingredient);
            intent.putExtra("position", pos);
            editActivityResultLauncher.launch(intent);
        };
        adapter.setOnItemClickListener(onItemClickListener);


        ingredientView.setOnClickListener(view -> {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();

            int position = viewHolder.getAdapterPosition();
            Log.println(Log.ASSERT,"", String.valueOf(position));
        });
        ActivityResultLauncher<Intent> addActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        assert data != null;
                        Ingredient ingredient = data.getParcelableExtra("added_ingredient");
//                        ingredientList.add(ingredient);
//                        adapter.notifyItemInserted(ingredientList.size()-1);

                        HashMap<String,Object> added = new HashMap<>();
                        added.put("id", id);
                        added.put("description", ingredient.getDescription());
                        added.put("bestBefore", ingredient.getBestBefore());
                        added.put("amount", ingredient.getAmount());
                        added.put("unit", ingredient.getUnit());
                        added.put("category", ingredient.getCategory());
                        added.put("location", ingredient.getLocation());
//                        added.put("id", id);
//                        added.put("ingredient", ingredient);
                        ingredientsDb.document()
                                .set(added)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d("success", "Added successfully");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("failure", "failed");
                                    }
                                });
                    }
                });

        ingredientsDb.whereEqualTo("id", id)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        ingredientList.clear();
                        assert value != null;
                        for (QueryDocumentSnapshot doc : value){
                            Ingredient ingredient = doc.toObject(Ingredient.class);
                            ingredientList.add(ingredient);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(view -> {
            Intent intent = new Intent(currentClass, AddEditIngredientActivity.class);
            intent.putExtra("purpose", "ADD");
            addActivityResultLauncher.launch(intent);
        });

    }

//dbBo4TSeTXq2RAAjYZa7Tp
}