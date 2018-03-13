package com.acme.a3csci3130;

import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.anything;

/**
 * This class contains tests which assert correct functionality
 * of the Main Activity.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTests {

    public ActivityTestRule<MainActivity> mainActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init() {
        Intents.init();
        mainActivityRule.launchActivity(new Intent());
    }

    @Test
    public void createNewBusinessButton() {
        onView(withId(R.id.submitButton)).perform(click());
        intended(hasComponent(CreateBusinessActivity.class.getName()));
    }

    @Test
    public void readBusiness() {
        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(0).perform(click());
        intended(hasComponent(DetailViewActivity.class.getName()));
    }

    @After
    public void teardown() {
        Intents.release();
    }

}
