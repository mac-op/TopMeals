package com.example.topgmeals.recipe.book;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.topgmeals.R;
import com.example.topgmeals.ingredient.storage.Ingredient;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

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

        Intent Itemintent = getIntent();
        String RecipeID = Itemintent.getExtras().getString("RECIPE_ID");

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
                            Ingredient curIng = new Ingredient(
                                    rData.get("description").toString(),
                                    new SimpleDateFormat("dd/MM/yyyy").parse(rData.get("bestBefore").toString(), new ParsePosition(0)),
                                    rData.get("location").toString(),
                                    (float)(long)rData.get("amount"),
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
                //Intent new_intent=getIntent();


                Intent intent = new Intent(currentClass, IngredientsDisplay.class);
                //String name = recipeBook.get(i).getTitle();
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




                //startActivity(intent);
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
                onBackPressed();
            }
        });

        Ingredient new_ingredient= (Ingredient) getIntent().getParcelableExtra("NEW");
        if (new_ingredient!=null){
            ingredientsRecipeBook.add(new_ingredient);

        }



    }
}