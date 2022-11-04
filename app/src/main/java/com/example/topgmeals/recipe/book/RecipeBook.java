package com.example.topgmeals.recipe.book;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.topgmeals.R;
import com.example.topgmeals.ingredient.storage.IngredientStorage;
import com.example.topgmeals.meal.plan.MealPlan;
import com.example.topgmeals.shopping.list.ShoppingList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class RecipeBook extends AppCompatActivity  {

    ListView recipeList;
    ArrayList<Recipe> recipeBook;
    Boolean check=Boolean.FALSE;
    RecipeAdapter recipeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipies);

        recipeList = (ListView) findViewById(R.id.recipe_book);
        recipeBook = new ArrayList<>();

        recipeListAdapter = new RecipeAdapter(this, R.layout.recipee_book_content, recipeBook);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //getCurrentUserRecipes(uid, db);

        final CollectionReference RecipeRef = db.collection("recipes");

        RecipeRef.whereEqualTo("id", uid)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        for (QueryDocumentSnapshot doc : value){
                            // refList.add(doc.getId());
                            Map<String, Object> rData = doc.getData();
                            Recipe curRecipe = new Recipe(rData.get("title").toString(),
                                    rData.get("prepTime").toString(),
                                    (int)(long)rData.get("servings") ,
                                    rData.get("category").toString(),
                                    rData.get("comments").toString(),
                                    doc.getId());

                            recipeBook.add(curRecipe);

                        }
                        recipeList.setAdapter(recipeListAdapter);
                        Log.e("v", "" +value.size());
                    }
                });
        Log.e("her", "ho");


//        Recipe burger = new Recipe("a","Half Hour", 2 , "Fast Food", "Follow the instruction as is");
//        Recipe pizza = new Recipe("Pizza","15 mins", 3, "fastfood", "Follow instructions");
//        recipeBook.add(burger);
//        recipeBook.add(pizza);

        recipeList.setAdapter(recipeListAdapter);

        RecipeBook currentClass = RecipeBook.this;




        recipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(currentClass, RecipeDisplay.class);
                String title = recipeBook.get(i).getTitle();
                String prep_time = recipeBook.get(i).getPrepTime();
                Integer servings = recipeBook.get(i).getServings();
                String s_servings=servings.toString();
                String category = recipeBook.get(i).getCategory();
                String comments = recipeBook.get(i).getComments();

                intent.putExtra("TITLE",title);
                intent.putExtra("PREP_TIME",prep_time);
                intent.putExtra("SERVINGS",s_servings);
                intent.putExtra("CATEGORY",category);
                intent.putExtra("COMMENTS",comments);
                intent.putExtra("ID", recipeBook.get(i).getUid());

                startActivity(intent);
            }
        });

        //region ButtonSwapping
        Button IngredientButton = (Button) findViewById(R.id.switchToIngredientStorage);
        IngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, IngredientStorage.class);
                startActivity(intent);
            }
        });

        Button ShoppingButton = (Button) findViewById(R.id.switchToShoppingList);

        ShoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, ShoppingList.class);
                startActivity(intent);
            }
        });

        Button MealPlanButton = (Button) findViewById(R.id.switchToMealPlan);

        MealPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, MealPlan.class);
                startActivity(intent);
            }
        });

        Button add_recipe=(Button) findViewById(R.id.add_button);

        add_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, addEditRecipe.class);
                startActivity(intent);
            }
        });

        Button RecipiesButton = (Button) findViewById(R.id.switchToRecipes);

        RecipiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentClass, RecipeBook.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                check=Boolean.TRUE;
                startActivity(intent);

            }
        });
        System.out.println(check);
        Recipe new_recipe=(Recipe) getIntent().getSerializableExtra("NEW");
        if (new_recipe!=null){
            recipeBook.add(new_recipe);
        }
        //endregion

    }

    private void getCurrentUserRecipes(String uid, FirebaseFirestore db){
        CollectionReference RecipeRef = db.collection("recipes");
        RecipeRef.whereEqualTo("id", uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.e("SUI", "here");

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                    Log.e("END", "sor");
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
        Log.e("her", "ho");

//        RecipeRef
//            .whereEqualTo("id", uid)
//            .get()
//            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                    if (task.isSuccessful()) {
//                        Log.e("SUI", "here");
//
//                        for (QueryDocumentSnapshot document : task.getResult()) {
//                            Log.d(TAG, document.getId() + " => " + document.getData());
//                        }
//                        Log.e("END", "sor");
//                    } else {
//                        Log.d(TAG, "Error getting documents: ", task.getException());
//                    }
//                }
//            });

    }

}