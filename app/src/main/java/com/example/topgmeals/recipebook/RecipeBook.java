package com.example.topgmeals.recipebook;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.topgmeals.R;
import com.example.topgmeals.ingredientstorage.Ingredient;
import com.example.topgmeals.ingredientstorage.IngredientStorage;
import com.example.topgmeals.mealplan.MealPlan;
import com.example.topgmeals.shoppinglist.ShoppingList;
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
import java.util.Comparator;
import java.util.Map;

/**
 * This class is an Activity that handles the Recipe Book menu. The user will be able to see
 * a list of recipes and their information and add a new recipe.
 */
public class RecipeBook extends AppCompatActivity {

    private ListView recipeList;
    private ArrayList<Recipe> recipeBook;
    private Boolean check=Boolean.FALSE;
    private RecipeAdapter recipeListAdapter;

    /**
     *  This method gets called when the Activity is created. It creates the layouts
     *  and handles the logic for the Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipies);
        setTitle("Recipes Book");

        recipeList = (ListView) findViewById(R.id.recipe_book);
        recipeBook = new ArrayList<>();

        recipeListAdapter = new RecipeAdapter(this, R.layout.recipee_book_content, recipeBook);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

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
                    }
                });

        recipeList.setAdapter(recipeListAdapter);

        recipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(RecipeBook.this, RecipeDisplay.class);
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
                intent.putExtra("RecipeID", recipeBook.get(i).getDocumentID());

                startActivity(new Intent());
            }
        });

        Button titleSort = (Button) findViewById(R.id.title_sort2);
        sortRecipe(titleSort, 0);
        Button timeSort = (Button) findViewById(R.id.time_sort2);
        sortRecipe(timeSort, 1);
        Button servingSort = (Button) findViewById(R.id.serving_sort);
        sortRecipe(servingSort, 2);
        Button categorySort = (Button) findViewById(R.id.category2);
        sortRecipe(categorySort, 3);

        // Add recipe
        Button add_recipe=(Button) findViewById(R.id.add_button);
        add_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeBook.this, addEditRecipe.class);
                startActivity(intent);
            }
        });

        // Adding a recipe to recipeBook
        Recipe new_recipe=(Recipe) getIntent().getSerializableExtra("NEW");
        if (new_recipe!=null){
            recipeBook.add(new_recipe);
        }

        // Begin Region ButtonSwapping
        Button btnIngredientStorage = findViewById(R.id.switchToIngredientStorage);
        btnIngredientStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RecipeBook.this, IngredientStorage.class));
                finish();
            }
        });

        Button btnShoppingList = findViewById(R.id.switchToShoppingList);
        btnShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RecipeBook.this, ShoppingList.class));
                finish();
            }
        });

        Button btnMealPlanner = findViewById(R.id.switchToMealPlan);
        btnMealPlanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RecipeBook.this, MealPlan.class));
                finish();
            }
        });

        Button btnRecipesBook = findViewById(R.id.switchToRecipes);
        btnRecipesBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RecipeBook.this, RecipeBook.class));
            }
        });
        // End Region ButtonSwapping
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
    }

    private void sortRecipe(Button sortButton, int criterion){
        sortButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Comparator<Recipe> comparator = null;
                switch (criterion) {
                    case 0:
                        comparator = Comparator.comparing(Recipe::getTitle);
                        break;
                    case 1:
                        //TODO: Change preptime to int and set time unit to minutes
                        comparator = Comparator.comparing(Recipe::getPrepTime);
                        break;
                    case 2:
                        comparator = Comparator.comparing(Recipe::getServings);
                        break;
                    case 3:
                        comparator = Comparator.comparing(Recipe::getCategory);
                        break;
                }
                recipeBook.sort(comparator);
                recipeListAdapter.notifyDataSetChanged();

            }
        });
    }
}