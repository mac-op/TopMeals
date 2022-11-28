package com.example.topgmeals.recipebook;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.topgmeals.R;
import com.example.topgmeals.ingredientstorage.Ingredient;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

/**
 * This class is an Activity that handles the Ingredients of a recipe. The user will be able to see
 * a list of ingredients and their information and add a new ingredient.
 */
public class IngredientRecipe extends AppCompatActivity {

    ListView ingredientsList;
    ArrayList<Ingredient> ingredientsRecipeBook;
    IngredientRecipeAdapter ingredientListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        ingredientsList = (ListView) findViewById(R.id.ingredient_recipe);
        ingredientsRecipeBook = new ArrayList<>();
        ingredientListAdapter = new IngredientRecipeAdapter(this, R.layout.ingredients_recipe_content, ingredientsRecipeBook);

        Intent itemIntent = getIntent();
        String RecipeID = itemIntent.getExtras().getString("RECIPE_ID");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //getCurrentUserRecipes(uid, db);

        final CollectionReference RecipeRef = db.collection("recipeIngredients");
        SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        RecipeRef.whereEqualTo("id", RecipeID)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        for (QueryDocumentSnapshot doc : value){
                            // refList.add(doc.getId());
                            Map<String, Object> rData = doc.getData();
//                            Date cur = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1, new ParsePosition(0));
//                            Log.e("HEEEEYYYYYYYYY", cur.toString());

                            float temp = 0;
                            try{
                                temp = (float)(double)rData.get("amount");
                            }
                            catch (Exception ex){
                                temp = (long)rData.get("amount");
                            }
                            Ingredient curIng = new Ingredient(
                                    rData.get("description").toString(),
                                    new SimpleDateFormat("MM/dd/yyyy").parse(rData.get("bestBefore").toString(), new ParsePosition(0)),
                                    rData.get("location").toString(),
                                    temp,
                                    rData.get("unit").toString(),
                                    rData.get("category").toString(),
                                    doc.getId());

                            ingredientsRecipeBook.add(curIng);

                        }
                        ingredientsList.setAdapter(ingredientListAdapter);

                    }
                });


        // ingredientsRecipeBook.add(beef);

        ingredientsList.setAdapter(ingredientListAdapter);



        IngredientRecipe currentClass = IngredientRecipe.this;

        ActivityResultLauncher<Intent> viewIngredient = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == 2){
                        Intent intent = result.getData();
                        int position = intent.getIntExtra("POSITION", -1);
                        assert (position != -1);
                        ingredientsRecipeBook.remove(position);
                        ingredientListAdapter.notifyDataSetChanged();
                    }

                }

        );

        ingredientsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(currentClass, IngredientsDisplay.class);

                String description = ingredientsRecipeBook.get(i).getDescription();
                Float amount = ingredientsRecipeBook.get(i).getAmount();
                String s_amount = amount.toString();
                String unit = ingredientsRecipeBook.get(i).getUnit();
                String category = ingredientsRecipeBook.get(i).getCategory();

                intent.putExtra("DESCRIPTION",description);
                intent.putExtra("AMOUNT",s_amount);
                intent.putExtra("UNIT",unit);
                intent.putExtra("CATEGORY",category);
                intent.putExtra("POSITION",i);
                intent.putExtra("docID", ingredientsRecipeBook.get(i).getDocumentID());
                intent.putExtra("recipeID", RecipeID);

                viewIngredient.launch(intent);
            }
        });




        Button addIngredient = (Button) findViewById(R.id.add_ingredient_recipe);
        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(currentClass, AddIngredientRecipe.class);
                intent.putExtra("rID", RecipeID);
                startActivity(intent);
            }
        });





        Button backButton = (Button) findViewById(R.id.back_button_ingredient_recipe);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(currentClass, RecipeBook.class);
                startActivity(intent);
                currentClass.finish();
            }
        });

        // Adding a ingredient for a recipe
        Ingredient new_ingredient= (Ingredient) getIntent().getParcelableExtra("NEW");
        if (new_ingredient!=null){
            ingredientsRecipeBook.add(new_ingredient);

        }



    }
}