package com.example.topgmeals;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.topgmeals.ingredientstorage.Ingredient;
import com.example.topgmeals.ingredientstorage.IngredientStorage;
import com.example.topgmeals.recipebook.AddIngredientRecipe;
import com.example.topgmeals.recipebook.IngredientRecipe;
import com.example.topgmeals.recipebook.IngredientsDisplay;
import com.example.topgmeals.recipebook.Recipe;
import com.example.topgmeals.recipebook.RecipeBook;
import com.example.topgmeals.recipebook.RecipeDisplay;
import com.example.topgmeals.recipebook.AddEditRecipe;
import com.robotium.solo.Solo;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;

/**
 * This class tests adding function of the recipe, editing function of the recipe
 * and the deleting function of the recipe as well.
 */
@RunWith(AndroidJUnit4.class)

public class RecipeBookTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<RecipeBook> rule = new ActivityTestRule<>(RecipeBook.class,true, true);

    /**
     * This method is called before all tests and creates a {@link Solo} instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    /**
     * This methods tests adding of a recipe to the Recipe book
     * @throws InterruptedException
     */
    @Test
    public void testAddRecipe() throws InterruptedException{
        // Adding recipe to the list
        solo.assertCurrentActivity("NOT IN RECIPE BOOK", RecipeBook.class);
        solo.clickOnButton("Add Recipe");
        solo.assertCurrentActivity("Not in Adding recipe page", AddEditRecipe.class);

        // Entering information of the new recipe
        solo.enterText((EditText) solo.getView(R.id.title_editText), "Mock title");
        solo.enterText((EditText) solo.getView(R.id.prep_time_editText), "23");
        solo.enterText((EditText) solo.getView(R.id.serving_editText), "9");
        solo.enterText((EditText) solo.getView(R.id.Category_editText), "Mock category");
        solo.enterText((EditText) solo.getView(R.id.Comments_editText), "Mock comments");

        // saving and going back to the screen
        solo.clickOnButton(" Save ");

        // checking if the recipe got added to the list
        solo.waitForActivity(RecipeBook.class);
        solo.assertCurrentActivity("Not in recipe book", RecipeBook.class);
        solo.sleep(500);
        RecipeBook activity = (RecipeBook) solo.getCurrentActivity();
        final ArrayList<Recipe> recipeList = activity.recipeBook;
        assertEquals(1,recipeList.size());
    }

    /**
     * This method will test editing an already existing recipee in the list
     */
    @Test
    public void testEditRecipe(){

        solo.assertCurrentActivity("NOT IN RECIPE BOOK", RecipeBook.class);
        solo.clickInList(1);
        solo.assertCurrentActivity("NOT IN RECIPEE DISPLAY", RecipeDisplay.class);

        solo.clearEditText((EditText) solo.getView(R.id.title_editText));
        solo.enterText((EditText) solo.getView(R.id.title_editText), "Mock title2");
        assertTrue(solo.waitForText("Mock title2", 1, 2000));

        // checking if the recipe got updated
        solo.clickOnButton("Update");
        solo.waitForActivity(RecipeBook.class);
        solo.assertCurrentActivity("NOT IN RECIPE BOOK", RecipeBook.class);
        solo.sleep(500);
        RecipeBook activity = (RecipeBook) solo.getCurrentActivity();
        final ArrayList<Recipe> recipeList = activity.recipeBook;
        assertEquals("Mock title2",recipeList.get(0).getTitle());

    }

    /**
     * This test is going to testing adding an ingredient to the recipe
     */
    @Test
    public void testAddingIngredient(){

        // Going to the recipe
        solo.assertCurrentActivity("NOT IN RECIPE BOOK", RecipeBook.class);
        solo.clickInList(1);
        solo.assertCurrentActivity("NOT IN RECIPEE DISPLAY", RecipeDisplay.class);

        // Going to add ingredient
        solo.clickOnButton("View Ingredients");
        solo.assertCurrentActivity("NOT IN INGREDIENT RECIPE", IngredientRecipe.class);

        solo.clickOnButton("Add New Ingredient");
        solo.assertCurrentActivity("NOT IN ADD INGREDIENT RECIPE", AddIngredientRecipe.class);

        solo.enterText((EditText) solo.getView(R.id.description_editText), "Mock description");
        solo.enterText((EditText) solo.getView(R.id.amount_editText), "2");
        solo.enterText((EditText) solo.getView(R.id.unit_editText), "Mock unit");
        solo.enterText((EditText) solo.getView(R.id.category_editText), "Mock category");

        solo.clickOnButton("Save");

        // checking if the ingredient got added
        solo.waitForActivity(IngredientRecipe.class);
        solo.assertCurrentActivity("NOT IN INGREDIENT RECIPE", IngredientRecipe.class);
        solo.sleep(500);
        IngredientRecipe activity = (IngredientRecipe) solo.getCurrentActivity();
        final ArrayList<Ingredient> ingredientList = activity.ingredientsRecipeBook;
        assertEquals(1, ingredientList.size());

    }

    /**
     * This test is going to test the functionality of the editing of an ingredient linked to recipe
     */
    @Test
    public void testEditIngredient(){
        solo.assertCurrentActivity("NOT IN RECIPE BOOK", RecipeBook.class);
        solo.clickInList(1);
        solo.assertCurrentActivity("NOT IN RECIPEE DISPLAY", RecipeDisplay.class);

        solo.clickOnButton("View Ingredients");
        solo.assertCurrentActivity("NOT IN INGREDIENTS RECIPE", IngredientRecipe.class);

        solo.clickInList(1);
        solo.assertCurrentActivity("NOT IN INGREDIENTS DISPLAY", IngredientsDisplay.class);

        solo.clearEditText((EditText) solo.getView(R.id.description_editText));
        solo.enterText((EditText) solo.getView(R.id.description_editText), "Mock description2");
        assertTrue(solo.waitForText("Mock description2", 1, 2000));


        solo.clickOnButton("UPDATE");
        solo.assertCurrentActivity("NOT IN INGREDIENTS DISPLAY", IngredientRecipe.class);

        // checking if ingredient got updated
        solo.waitForActivity(IngredientRecipe.class);
        solo.assertCurrentActivity("NOT IN INGREDIENT RECIPE", IngredientRecipe.class);
        solo.sleep(500);
        IngredientRecipe activity = (IngredientRecipe) solo.getCurrentActivity();
        final ArrayList<Ingredient> ingredientsList = activity.ingredientsRecipeBook;
        assertEquals("Mock description2",ingredientsList.get(0).getDescription());

    }

    /**
     * This test is going to check the functionality of deleting an ingredient from a recipe
     */
    @Test
    public void testDeletingIngredient(){

        solo.assertCurrentActivity("NOT IN RECIPE BOOK", RecipeBook.class);
        solo.clickInList(1);
        solo.assertCurrentActivity("NOT IN RECIPEE DISPLAY", RecipeDisplay.class);

        solo.clickOnButton("View Ingredients");
        solo.assertCurrentActivity("NOT IN INGREDIENT RECIPE", IngredientRecipe.class);
        solo.sleep(500);
        IngredientRecipe activity = (IngredientRecipe) solo.getCurrentActivity();
        final ArrayList<Ingredient> ingredientList = activity.ingredientsRecipeBook;
        assertEquals(1,ingredientList.size());

        solo.clickInList(1);
        solo.assertCurrentActivity("NOT IN INGREDIENTS DISPLAY", IngredientsDisplay.class);
        solo.clickOnButton("DELETE");
        solo.clickOnButton("Yes");
        // checking if ingredient got deleted
        solo.waitForActivity(IngredientRecipe.class);
        solo.assertCurrentActivity("NOT IN INGREDIENTS RECIPE", IngredientRecipe.class);
        solo.sleep(500);
        activity = (IngredientRecipe) solo.getCurrentActivity();
        final ArrayList<Ingredient> ingredientList2 = activity.ingredientsRecipeBook;
        assertEquals(0, ingredientList2.size());

    }

    /**
     * This test is testing the functionality of deletion of a recipe from the list
     */
    @Test
    public void testDeleteRecipe(){
        solo.assertCurrentActivity("NOT IN RECIPE BOOK", RecipeBook.class);
        solo.clickInList(1);
        solo.assertCurrentActivity("NOT IN RECIPE DISPLAY", RecipeDisplay.class);

        solo.clickOnButton("Delete");
        solo.clickOnButton("Yes");

        // checking if the recipe got deleted or not
        solo.waitForActivity(RecipeBook.class);
        solo.assertCurrentActivity("NOT IN RECIPE BOOK", RecipeBook.class);
        solo.sleep(500);
        RecipeBook activity = (RecipeBook) solo.getCurrentActivity();
        final ArrayList<Recipe> recipeList = activity.recipeBook;
        assertEquals(0,recipeList.size());
    }
}
