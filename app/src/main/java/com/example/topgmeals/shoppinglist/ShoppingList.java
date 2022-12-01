package com.example.topgmeals.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.topgmeals.R;
import com.example.topgmeals.ingredientstorage.Ingredient;
import com.example.topgmeals.ingredientstorage.IngredientAdapter;
import com.example.topgmeals.ingredientstorage.IngredientStorage;
import com.example.topgmeals.mealplan.Meal;
import com.example.topgmeals.mealplan.MealPlan;
import com.example.topgmeals.recipebook.Recipe;
import com.example.topgmeals.recipebook.RecipeBook;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class is for the Activity that is the Shopping List Menu for the App
 */
public class ShoppingList extends AppCompatActivity implements ShoppingListAdapter.ItemClickListener {

    /**
     * An {@link RecyclerView.Adapter} that holds {@link Ingredient} objects.
     */
    private RecyclerView shoppingListView;

    /**
     * An {@link ArrayList} that holds the current {@link Ingredient} objects.
     */
    private ArrayList<Ingredient> shoppingList;

    /**
     * An {@link ArrayList} that holds the initial {@link Ingredient} objects.
     */
    private ArrayList<Ingredient> fullshoppingList;


    /**
     * An {@link Boolean} that acts as a flag.
     */
    private Boolean check=Boolean.FALSE;

    /**
     * A custom {@link RecyclerView.Adapter} of type {@link IngredientAdapter} that handles the view
     * of the list of ingredients.
     */
    private ShoppingListAdapter shoppingListAdapter;

    /**
     * Holds firebase variable
     */
    private FirebaseApp app;

    /**
     * An {@link ArrayList} that holds the {@link Meal} objects.
     */
    private ArrayList<Meal> mealList;

    /**
     * An {@link String} that holds the firebase user ID.
     */
    private String id;

    /**
     * An {@link ArrayList} that holds the {@link Recipe} objects.
     */
    private ArrayList<Recipe> recipeBook;

    /**
     * An {@link ArrayList} that holds the {@link Ingredient} objects.
     */
    private ArrayList<Ingredient> ingredientsList;

    /**
     * The onCreate Method creates the
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        setTitle("Shopping List");

        // Initialize variables for ListView
        shoppingListView = findViewById(R.id.shoppingListView);
        shoppingListView.setLayoutManager(new LinearLayoutManager(this));
        shoppingListView.addItemDecoration(
                new DividerItemDecoration(this, null));
        shoppingList = new ArrayList<>();
        shoppingListAdapter = new ShoppingListAdapter(this, shoppingList);
        fullshoppingList = new ArrayList<Ingredient>();
        mealList = new ArrayList<>();
        recipeBook = new ArrayList<>();
        ingredientsList = new ArrayList<>();

        // Connect to the Firestore database and get the Reference to the ingredients collection
        app = FirebaseApp.initializeApp(ShoppingList.this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference ingredientsDb = db.collection("ingredients");
        final CollectionReference mealCollection = db.collection("mealplan");
        final CollectionReference RecipeRef = db.collection("recipes");

        // Get the user ID with FirebaseAuth
        id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        ingredientsDb.whereEqualTo("id", id)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException error) {
                        ingredientsList.clear();
                        assert value != null;
                        for (QueryDocumentSnapshot doc : value){
                            Ingredient ingredient = doc.toObject(Ingredient.class);
                            ingredient.setDocumentID(doc.getId());
                            ingredientsList.add(ingredient);
                        }
                        ShoppingGlobalVars.getInstance().setcurIngredientsList(ingredientsList);
                    }
                });


        mealCollection.whereEqualTo("id", id).addSnapshotListener((value, error) -> {
            for (QueryDocumentSnapshot doc: value){
                Date date1=new SimpleDateFormat("MM/dd/yyyy").parse(doc.getString("date"),
                        new ParsePosition(0));

                Date New = new Date(date1.getTime() + (1000 * 60 * 60 * 24));
                Date cur = new Date();

                if (New.after(cur)){
                    Log.e("t", date1.toString());
                    Log.e("t", New.toString());

                    Log.e("t", cur.toString());
                    Log.e("t", "PASS");
                    Meal meal = doc.toObject(Meal.class);
                    meal.setDocRef(doc.getId());
                    mealList.add(meal);
                }

            }
        });

        RecipeRef.whereEqualTo("id", id)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException error) {

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
                        Log.e("t", "ENER");
                        load(db);
                    }
                });

        Log.e("t", "load" + Integer.toString( shoppingList.size()));

        shoppingListView.setAdapter(shoppingListAdapter);

        Toast.makeText(this, "Swipe right or check the Checkbox to cross off the " +
                "item.", Toast.LENGTH_LONG).show();

        ItemTouchHelper itemTouchHelper =
                new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                shoppingList.remove(viewHolder.getLayoutPosition());
                shoppingListAdapter.notifyDataSetChanged();
                Intent intent = new Intent();
                intent.putExtra("test", shoppingList);
            }
        });

        itemTouchHelper.attachToRecyclerView(shoppingListView);

        Button sortByDescription = (Button) findViewById(R.id.shopSortDescription);
        sortByDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(shoppingList,
                        (d1, d2) -> d1.getDescription().compareTo(d2.getDescription()));
                shoppingListAdapter.notifyDataSetChanged();

            }
        });

        Button sortByCategory = (Button) findViewById(R.id.shopSortCategory);
        sortByCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(shoppingList, (d1, d2) -> d2.getCategory().compareTo(d1.getCategory()));
                shoppingListAdapter.notifyDataSetChanged();

            }
        });

        Button finishShopping = (Button) findViewById(R.id.shopFinishShopping);
        finishShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("t","SHOPLIST SIZE " + Integer.toString( shoppingList.size()));

                Intent intent = new Intent(ShoppingList.this, ShoppingListFinish.class);

                Log.e("t","SHOPLIST SIZE " + Integer.toString( shoppingList.size()));
                Log.e("t",Integer.toString( fullshoppingList.size()));

                ArrayList<Ingredient> inCart = new ArrayList<Ingredient>();
                for (Ingredient i : fullshoppingList){
                    inCart.add(i);
                }

                if (shoppingList.size() != 0){
                    for (Ingredient i : shoppingList) {
                        inCart.remove(i);
                    }
                }
                ShoppingGlobalVars.getInstance().setList(inCart);
                startActivity(intent);
            }
        });

        // Begin Region Activity Swapping
        Button IngredientButton = (Button) findViewById(R.id.switchToIngredientStorage);
        IngredientButton.setOnClickListener(view -> {
            startActivity(new Intent(ShoppingList.this, IngredientStorage.class));
            finish();
        });

        Button ShoppingButton = (Button) findViewById(R.id.switchToShoppingList);
        ShoppingButton.setOnClickListener(view -> startActivity(new Intent(ShoppingList.this, ShoppingList.class)));

        Button MealPlanButton = (Button) findViewById(R.id.switchToMealPlan);
        MealPlanButton.setOnClickListener(view -> {
            startActivity(new Intent(ShoppingList.this, MealPlan.class));
            finish();
        });

        Button RecipesButton = (Button) findViewById(R.id.switchToRecipes);
        RecipesButton.setOnClickListener(view -> {
            startActivity(new Intent(ShoppingList.this, RecipeBook.class));
            finish();
        });
        // End Region Activity Swapping
    }

    /**
     * This function takes all the ArraysList from IngredientStorage and Recipies and
     * creates a Shopping list based on the needs of the customer
     * @param db
     */
    private void load(FirebaseFirestore db){
        final CollectionReference RecipeIngRef = db.collection("recipeIngredients");

        Set<String> SeenIngredients = new HashSet<String>();
        for ( Meal m : mealList) {
            for (Ingredient i : ingredientsList){
                if (i.getDescription().equals(m.getMealName())){

                    if (SeenIngredients.contains(i.getDescription())){

                        for (Ingredient is: shoppingList){
                            if (is.getDescription().equals(i.getDescription())){
                                is.setAmount(is.getAmount() + (float)m.getNumServings());
                                shoppingListAdapter.notifyDataSetChanged();
                            }
                            break;
                        }

                        break;
                    }

                    Log.e("t", i.getDescription() + String.valueOf(m.getNumServings()) +
                            " " + String.valueOf(i.getAmount()));
                    if (m.getNumServings() > i.getAmount()){
                        Ingredient curI = new Ingredient(m.getMealName(), new Date(),
                                i.getLocation(), (float)m.getNumServings() - (float)i.getAmount(),
                                i.getUnit(), i.getCategory(), "s");
                        shoppingList.add(curI);
                        fullshoppingList.add(curI);
                        shoppingListAdapter.notifyDataSetChanged();
                        SeenIngredients.add(m.getMealName());
                    }
                    break;
                }
            }

            // Else must be a Recipe
            for (Recipe r : recipeBook){
                if (r.getTitle().equals(m.getMealName())){
                    SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

                    ArrayList<Ingredient> RecIngredientList = new ArrayList<>();
                    Log.e("T", "HEHEHEHEH");
                    RecipeIngRef.whereEqualTo("id", r.getDocumentID())
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value,
                                                @Nullable FirebaseFirestoreException error) {

                                for (QueryDocumentSnapshot doc : value){
                                    // refList.add(doc.getId());
                                    Map<String, Object> rData = doc.getData();

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

                                    RecIngredientList.add(curIng);
                                }

                                Boolean NA = true;
                                for (Ingredient ir : RecIngredientList) {
                                    NA = true;
                                    Log.e("t", ir.getDescription());
                                    for (Ingredient i : ingredientsList) {
                                        if (ir.getDescription().equals(i.getDescription())) {

                                            float tempTot = (float) m.getNumServings() * (float) ir.getAmount();

                                            if (SeenIngredients.contains(ir.getDescription())){
                                                Log.e("t", "SEEN " + ir.getDescription() );
                                                for (Ingredient is: shoppingList){
                                                    if (is.getDescription().equals(ir.getDescription())){
                                                        Log.e("t", "b4 " + String.valueOf(is.getAmount()) );
                                                        is.setAmount(is.getAmount() + tempTot);
                                                        Log.e("t", "a4 " + String.valueOf(is.getAmount()) );
                                                        shoppingListAdapter.notifyDataSetChanged();
                                                        break;

                                                    }
                                                }
                                                NA = false;
                                                break;
                                            }
                                            if (tempTot > i.getAmount()) {
                                                Ingredient curI = new Ingredient(ir.getDescription(),
                                                        new Date(), i.getLocation(),
                                                        (float)tempTot - (float)i.getAmount(),
                                                        i.getUnit(), i.getCategory(), "s");
                                                shoppingList.add(curI);
                                                fullshoppingList.add(curI);
                                                shoppingListAdapter.notifyDataSetChanged();
                                                SeenIngredients.add(ir.getDescription());
                                            }
                                            NA = false;
                                            break;
                                        }
                                    }
                                    // Add new Ig if not in list
                                    if (NA) {

                                        if (SeenIngredients.contains(ir.getDescription())) {
                                            float tempTot = (float) m.getNumServings() * (float) ir.getAmount();
                                            Log.e("t", "SEEN " + ir.getDescription());
                                            for (Ingredient is : shoppingList) {
                                                if (is.getDescription().equals(ir.getDescription())) {
                                                    Log.e("t", "b4 " + String.valueOf(is.getAmount()));
                                                    is.setAmount(is.getAmount() + tempTot);
                                                    Log.e("t", "a4 " + String.valueOf(is.getAmount()));
                                                    shoppingListAdapter.notifyDataSetChanged();
                                                    break;

                                                }
                                            }
                                        }
                                        else {


                                            Ingredient curI = new Ingredient(ir.getDescription(),
                                                    new Date(), ir.getLocation(),
                                                    (float) m.getNumServings() * (float) ir.getAmount(),
                                                    ir.getUnit(), ir.getCategory(), "s");
                                            shoppingList.add(curI);
                                            fullshoppingList.add(curI);
                                            shoppingListAdapter.notifyDataSetChanged();
                                            Log.e("t", "SHOPLIST SIZE: " + String.valueOf(shoppingList.size()));
                                            SeenIngredients.add(ir.getDescription());
                                        }
                                    }
                                }
                            }
                        });
                }
            }
        }
        shoppingListAdapter.notifyDataSetChanged();
    }

    /**
     * Used for onItemClickEvents
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(View view, int position) {
    }
}