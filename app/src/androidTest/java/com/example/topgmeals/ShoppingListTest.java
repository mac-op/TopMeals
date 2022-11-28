package com.example.topgmeals;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.junit.Assert.assertEquals;

import android.app.Activity;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.topgmeals.ingredientstorage.AddEditIngredientActivity;
import com.example.topgmeals.ingredientstorage.IngredientStorage;
import com.example.topgmeals.mealplan.AddMealActivity;
import com.example.topgmeals.mealplan.MealPlan;
import com.example.topgmeals.shoppinglist.ShoppingList;
import com.example.topgmeals.shoppinglist.ShoppingListFinish;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This class tests the functionality of the {@link ShoppingList} class.
 * Please make sure there is nothing in Shopping List, Ingredient Storage and
 * Meal Plan before this test is conducted.
 */
@RunWith(AndroidJUnit4.class)
public class ShoppingListTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<ShoppingList> rule = new ActivityTestRule<>(ShoppingList.class, true, true);

    /**
     * This method is called before all tests and creates a {@link Solo} instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), rule.getActivity());
    }


    /**
     * This method tests the ability to clear the Shopping List after new items have been added.
     * @throws InterruptedException
     */
    @Test
    public void ShoppingListTest() throws InterruptedException {
        addMockMeal();                  // Create a mock item

        solo.assertCurrentActivity("Not in right Activity", ShoppingList.class);
        RecyclerView view = (RecyclerView) solo.getView(R.id.shoppingListView);

        // Check the item off
        assertEquals(1, view.getChildCount());
        solo.clickOnCheckBox(0);
        solo.sleep(1000);
        assertEquals(0, view.getChildCount());

        // Update
        solo.clickOnButton("Finish Shopping");
        solo.assertCurrentActivity("Not in right Activity", ShoppingListFinish.class);
        solo.clickOnButton("DONE");
        solo.clickOnButton("Yes");
    }

    /**
     * This method adds a mock meal so there exists an item in the Shopping List
     */
    public void addMockMeal(){
        //Go to Add Ingredient menu
        solo.sleep(500);
        solo.clickOnButton("Ingredients");
        solo.sleep(500);
        solo.assertCurrentActivity("Not in Ingredient Storage", IngredientStorage.class);
        solo.clickOnButton("Add");
        solo.assertCurrentActivity("Not in Add Ingredient", AddEditIngredientActivity.class);

        //Enter information
        solo.enterText((EditText) solo.getView(R.id.description), "Mock Description");
        solo.clickOnView(solo.getView(R.id.bb_date));
        solo.clickOnButton("OK");

        solo.enterText((EditText) solo.getView(R.id.set_location), "Mock Location");
        solo.enterText((EditText) solo.getView(R.id.amount), "1");

        solo.enterText((EditText) solo.getView(R.id.category), "Mock Category");
        solo.enterText((EditText) solo.getView(R.id.unit), "Mock Unit");

        //Save and go back to Ingredients
        solo.clickOnButton("Save");
        solo.assertCurrentActivity("Not in Ingredient Storage", IngredientStorage.class);

        solo.sleep(500);
        solo.clickOnButton("Meal Plan");

        solo.clickOnButton("Add Meal");
        solo.assertCurrentActivity("Not in Add Meal", AddMealActivity.class);

        solo.pressSpinnerItem(0, 1);
        solo.pressSpinnerItem(1, 0);
        solo.enterText((EditText) solo.getView(R.id.meal_serving), "20");

        solo.clickOnButton("Save");

        solo.sleep(500);
        solo.clickOnButton("Shop List");
    }
}
