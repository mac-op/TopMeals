package com.example.topgmeals;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.topgmeals.ingredientstorage.AddEditIngredientActivity;
import com.example.topgmeals.ingredientstorage.IngredientStorage;
import com.example.topgmeals.mealplan.AddMealActivity;
import com.example.topgmeals.mealplan.Meal;
import com.example.topgmeals.mealplan.MealPlan;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * This class tests the functionality of {@link com.example.topgmeals.mealplan.MealPlan}
 * Please log in to an account that has at least one ingredient in {@link IngredientStorage}
 * or one recipe in {@link com.example.topgmeals.recipebook.RecipeBook}.
 * Otherwise, there's no meal to add.
 * In addition, there should be no meal present in MealPlan before the test is conducted.
 */
@RunWith(AndroidJUnit4.class)
public class MealPlanTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MealPlan> rule = new ActivityTestRule<>(MealPlan.class, true, true);

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
     * This method tests the functionality to add a new meal.
     */
    @Test
    public void addMealTest() {
        // Go to AddMealActivity
        solo.assertCurrentActivity("Not in Meal Planner", MealPlan.class);
        solo.clickOnButton("Add");
        solo.assertCurrentActivity("Not in Add Meal", AddMealActivity.class);

        ExpandableListView view = (ExpandableListView) solo.getView(R.id.expandable_meal_plan);
        int oldCount = view.getCount();

        // Set contents and Save
        solo.clickOnView(solo.getView(R.id.meal_date));
        solo.clickOnButton("OK");

        View view1 = solo.getView(Spinner.class, 0);
        solo.clickOnView(view1);
        solo.scrollToTop();
        solo.clickOnView(solo.getView(TextView.class, 1));

        View view2 = solo.getView(Spinner.class, 1);
        solo.clickOnView(view2);
        solo.scrollToTop();
        solo.clickOnView(solo.getView(TextView.class, 1));

        solo.enterText((EditText) solo.getView(R.id.meal_serving), "2");

        solo.clickOnButton("Save");

        solo.assertCurrentActivity("Not in Meal Planner", MealPlan.class);
        solo.sleep(200);
        int newCount = view.getCount();
        assertEquals(oldCount+1, newCount);
    }

    /**
     * This method tests the functionality to delete a meal. To be run when there exists at least
     * one meal.
     */
    @Test
    public void deleteMealTest() {
        solo.assertCurrentActivity("Not in Meal Planner", MealPlan.class);

        ExpandableListView view = (ExpandableListView) solo.getView(R.id.expandable_meal_plan);
        int oldCount = view.getChildCount();
        assertTrue(oldCount > 0);
        // Expand a list
        solo.clickInList(0);
        // Click on Delete
        solo.clickOnImage(0);

        solo.sleep(200);
        int newCount = view.getChildCount();
        assertEquals(oldCount-1, newCount);
    }
}