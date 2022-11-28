package com.example.topgmeals;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.app.Activity;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.topgmeals.login.MainActivity;
import com.example.topgmeals.login.MainOptions;
import com.example.topgmeals.login.MainRegisterUser;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Ignore;
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
    public void logInTest(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        //Enter email
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "testNewemail@gmail.com");
        //Enter password
        solo.enterText((EditText) solo.getView(R.id.password), "randompass");
        //Click on Create Account
        solo.clickOnButton("LOGIN");

        // Now in Main Options
        solo.assertCurrentActivity("Not in Main Options", MainOptions.class);
    }

    /**
     * This method tests the ability to create a new account. The account email and password should
     * be changed every time before this test is done.
     */
    @Test
    @Ignore("This test has been confirmed to work. Please change the email and password to run again")
    public void registerTest(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnView((TextView) solo.getView(R.id.register));
        solo.assertCurrentActivity("Wrong Activity", MainRegisterUser.class);

        solo.enterText((EditText) solo.getView(R.id.registerName), "Mock Name");
        solo.enterText((EditText) solo.getView(R.id.registerEmailAddress), "mail@gmail.com");
        solo.enterText((EditText) solo.getView(R.id.registerPassword), "123456");
        solo.clickOnButton("CREATE ACCOUNT");

        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

    }
}
