package com.example.topgmeals.shoppinglist;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.topgmeals.R;
import com.example.topgmeals.ingredientstorage.AddEditIngredientActivity;
import com.example.topgmeals.ingredientstorage.Ingredient;
import com.example.topgmeals.ingredientstorage.IngredientAdapter;
import com.example.topgmeals.ingredientstorage.IngredientStorage;
import com.example.topgmeals.login.MainOptions;
import com.example.topgmeals.mealplan.MealPlan;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingListFinish extends AppCompatActivity {

    private ArrayList<Ingredient> inCart;
    private ArrayList<Ingredient> ingredientList;
    private IngredientAdapter ingredientAdapter;
    private RecyclerView ingredientView;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list_finish);
        setTitle("Picked Up Ingredients");
        Toast.makeText(this, "Select a picked up ingredient to update your " +
                "Ingredient Storage", Toast.LENGTH_LONG).show();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference ingredientsDb = db.collection("ingredients");

        id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        inCart = ShoppingGlobalVars.getInstance().getList();
        ingredientList = ShoppingGlobalVars.getInstance().getcurIngredientsList();

        for (Ingredient i: inCart){
            for (Ingredient j: ingredientList){
                if (i.getDescription().equals(j.getDescription())){
                    i.setAmount(i.getAmount() + j.getAmount());
                    break;
                }
            }
        }

        ingredientView = findViewById(R.id.shoppingFinishListView);
        ingredientAdapter = new IngredientAdapter(inCart);
        ingredientView.setAdapter(ingredientAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        ingredientView.setLayoutManager(layoutManager);
        androidx.recyclerview.widget.DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(ingredientView.getContext(),
                layoutManager.getOrientation());
        ingredientView.addItemDecoration(dividerItemDecoration);

        ingredientAdapter.notifyDataSetChanged();

        ActivityResultLauncher<Intent> editActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // When DELETE is chosen
                    if (result.getResultCode() == 2) {
                        // Get Intent from child Activity and the position of the item to delete
                        Intent deleteIntent = result.getData();
                        Ingredient ingredient =
                                deleteIntent.getParcelableExtra("edited_ingredient");

                        for (Ingredient i : inCart){
                            if (i.getDescription().equals(ingredient.getDescription())){
                                inCart.remove(i);
                                break;

                            }
                        }
                        // Get DocumentReference of the item in the database according to its position
                        // in refList and remove it
                        ingredientAdapter.notifyDataSetChanged();

                    }
                    // When EDIT is chosen
                    else if (result.getResultCode() == Activity.RESULT_OK){
                        Log.e("t", "THOMAS");
                        Intent editIntent = result.getData();
                        Ingredient ingredient =
                                editIntent.getParcelableExtra("edited_ingredient");

                        Boolean unique = true;
                        String DocID = "";
                        for (Ingredient i: ingredientList){
                            if (i.getDescription().equals(ingredient.getDescription())){
                                unique = false;
                                DocID = i.getDocumentID();
                                break;
                            }
                        }
                        HashMap<String,Object> data = toHashMap(ingredient);

                        if (unique) {
                            db.collection("ingredients")
                                    .add(data)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Log.d(TAG, "DocumentSnapshot written with ID: "
                                                    + documentReference.getId());

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG, "Error adding document", e);
                                        }
                                    });
                        } else {
                            db.collection("ingredients").document(DocID)
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
                        }
                        for (Ingredient i : inCart){
                            if (i.getDescription().equals(ingredient.getDescription())){
                                inCart.remove(i);
                                break;
                            }
                        }
                        ingredientAdapter.notifyDataSetChanged();
                    }
                });

        View.OnClickListener onItemClickListener = view -> {
            // Get the position of the clicked item
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int pos = viewHolder.getAbsoluteAdapterPosition();
            Ingredient ingredient = inCart.get(pos);

            // Launch AddEditIngredientActivity so that the user can edit the clicked Ingredient
            Intent intent = new Intent(getBaseContext(), AddEditIngredientActivity.class);
            intent.putExtra("purpose", "UPDATE");
            intent.putExtra("ingredient_object",ingredient);
            intent.putExtra("position", pos);
            editActivityResultLauncher.launch(intent);
        };
        ingredientAdapter.setOnItemClickListener(onItemClickListener);

        Button finishShopping = (Button) findViewById(R.id.shopFinishShopping2);
        finishShopping.setOnClickListener(view -> {
            AlertDialog.Builder cancelDialog = new AlertDialog.Builder(ShoppingListFinish.this);
            cancelDialog.setMessage("Please confirm that ingredient storage has been updated and " +
                            "any remaining ingredients in the list will be unpicked.").setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(ShoppingListFinish.this, MainOptions.class));
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
            alertCancel.setTitle("Updating Ingredient Storage");
            alertCancel.show();
        });
    }

    private HashMap<String, Object> toHashMap(Ingredient ingredient){
        HashMap<String,Object> map = new HashMap<>();
        map.put("id", id);
        map.put("description", ingredient.getDescription());
        map.put("bestBefore", ingredient.getBestBefore());
        map.put("amount", ingredient.getAmount());
        map.put("unit", ingredient.getUnit());
        map.put("category", ingredient.getCategory());
        map.put("location", ingredient.getLocation());

        return map;
    }
}