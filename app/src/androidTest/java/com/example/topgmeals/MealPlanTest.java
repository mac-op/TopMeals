package com.example.topgmeals;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.app.Activity;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.topgmeals.ingredientstorage.IngredientStorage;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * This class tests the functionality of {@link com.example.topgmeals.mealplan.MealPlan}
 * Please log in to an account that has at least one ingredient in {@link IngredientStorage}
 * or one recipe in {@link com.example.topgmeals.recipebook.RecipeBook}. Otherwise there's no meal to add.
 */
@RunWith(AndroidJUnit4.class)
public class MealPlanTest {
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


}
