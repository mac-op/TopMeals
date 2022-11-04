package com.example.topgmeals;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This class tests the log in process. Please log out of your account before conducting this test.
 */
@RunWith(AndroidJUnit4.class)
public class LogInTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, true, true);

    /**
     * This method is called before all tests and creates a {@link Solo} instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo =new Solo(getInstrumentation(), rule.getActivity());
    }

    /**
     * This method gets the Activity.
     * @throws Exception
     */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    /**
     * Enter credentials to create an account. After that user will be taken to Main Options menu.
     */
    @Test
    public void LogIn(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        //Enter email
        solo.enterText((EditText) solo.getView(R.id.main_email), "testemail@gmail.com");
        //Enter password
        solo.enterText((EditText) solo.getView(R.id.main_password), "randompass");
        //Click on Create Account
        solo.clickOnButton("Create Account");

        // Now in Main Options
        solo.assertCurrentActivity("Not in Main Options",MainOptions.class);
    }
}
