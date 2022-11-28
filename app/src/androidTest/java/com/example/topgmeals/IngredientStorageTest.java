package com.example.topgmeals;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.topgmeals.mealplan.ingredientstorage.AddEditIngredientActivity;
import com.example.topgmeals.mealplan.ingredientstorage.Ingredient;
import com.example.topgmeals.mealplan.ingredientstorage.IngredientStorage;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

/**
 * This class tests adding and editing/deleting an ingredient. Please log in an account that has
 * no previous ingredients before conducting the tests. addIngredientTest() should be run before
 * editIngredientTest() and removeIngredientTest().
 */
@RunWith(AndroidJUnit4.class)
public class IngredientStorageTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<IngredientStorage> rule = new ActivityTestRule<>(IngredientStorage.class, true, true);

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
     * This method tests adding an ingredient to the list
     * @throws InterruptedException
     */
    @Test
    public void addIngredientTest() throws InterruptedException {
        //Go to Add Ingredient menu
        solo.assertCurrentActivity("Not in Ingredient Storage",IngredientStorage.class);
        solo.clickOnButton("Add");
        solo.assertCurrentActivity("Not in Add Ingredient", AddEditIngredientActivity.class);

        //Go back to Ingredients
        solo.clickOnButton("Cancel");
        solo.clickOnButton("Yes");

        //Go to Add Ingredient menu
        solo.assertCurrentActivity("Not in Ingredient Storage",IngredientStorage.class);
        solo.clickOnButton("Add");
        solo.assertCurrentActivity("Not in Add Ingredient", AddEditIngredientActivity.class);

        //Enter information
        solo.enterText((EditText) solo.getView(R.id.description), "Mock Description");
        solo.clickOnView(solo.getView(R.id.bb_date));
        solo.clickOnButton("OK");

        solo.enterText((EditText) solo.getView(R.id.set_location), "Mock Location");
        solo.enterText((EditText) solo.getView(R.id.amount), "12");

        solo.enterText((EditText) solo.getView(R.id.category), "Mock Category");
        solo.enterText((EditText) solo.getView(R.id.unit), "Mock Unit");

        //Save and go back to Ingredients
        solo.clickOnButton("Save");

        solo.assertCurrentActivity("Not in Ingredient Storage", IngredientStorage.class);

        solo.clickInRecyclerView(0);
        solo.assertCurrentActivity("Not in Edit Ingredient", AddEditIngredientActivity.class);
    }

    /**
     * This method tests editing an existing ingredient in the list. Should be called after adding an ingredient
     * @throws InterruptedException
     */
    @Test
    public void editIngredientTest() throws InterruptedException {
        //Click on first item in Ingredient Storage
        solo.assertCurrentActivity("Not in Ingredient Storage",IngredientStorage.class);
        solo.clickInRecyclerView(0);
        solo.assertCurrentActivity("Not in Edit Ingredient", AddEditIngredientActivity.class);

        //Change a field and Save
        solo.clearEditText((EditText) solo.getView(R.id.description));
        solo.enterText((EditText) solo.getView(R.id.description), "New Mock Description");
        assertTrue(solo.waitForText("New Mock Description", 1, 2000));
        solo.clickOnButton("Save");

        //Check for changes in Ingredient Storage
        solo.waitForView(solo.getView(R.id.add_button));
        solo.waitForActivity(IngredientStorage.class);
        solo.assertCurrentActivity("Not in Ingredient Storage",IngredientStorage.class);
        IngredientStorage activity = (IngredientStorage) solo.getCurrentActivity();
        final ArrayList<Ingredient> ingredientList = activity.ingredientList;
        assertEquals("New Mock Description",ingredientList.get(0).getDescription());
    }

    /**
     * This method tests removing an existing ingredient from the list. Should be called after adding an ingredient to the list.
     */
    @Test
    public void removeIngredientTest(){
        //Check size of list in Ingredient Storage
        IngredientStorage activity = (IngredientStorage) solo.getCurrentActivity();
        final ArrayList<Ingredient> ingredientList = activity.ingredientList;
        assertEquals(1,ingredientList.size());

        //Click on first item in the list
        solo.assertCurrentActivity("Not in Ingredient Storage",IngredientStorage.class);
        solo.clickInRecyclerView(0);
        solo.assertCurrentActivity("Not in Edit Ingredient", AddEditIngredientActivity.class);

        //Click on Delete button
        solo.clickOnButton("Delete");
        solo.clickOnButton("Yes");

        //Check size of list upon returning to Ingredient Storage
        solo.waitForActivity(IngredientStorage.class);
        solo.waitForView(solo.getView(R.id.add_button));
        solo.assertCurrentActivity("Not in Ingredient Storage",IngredientStorage.class);

        IngredientStorage activity1 = (IngredientStorage) solo.getCurrentActivity();
        final ArrayList<Ingredient> ingredientList1 = activity.ingredientList;
        assertEquals(0, ingredientList1.size());
    }
}
