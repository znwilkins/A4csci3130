package com.acme.a3csci3130;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.database.FirebaseDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertFalse;
import static org.hamcrest.CoreMatchers.anything;
import static org.junit.Assert.assertTrue;

/**
 * This class contains tests which assert the correct functionality of
 * the CreateBusinessActivity.
 */
@RunWith(AndroidJUnit4.class)
public class CreateBusinessInstrumentedTests {

    public static final String ADDRESS = "1732 Argyle Street, Halifax";
    public static final String BUSINESS_NUMBER = "123456789";
    public static final String BUSINESS_NAME = "Tuna Testing Inc.";
    public static final String BUSINESS_TYPE = "Distributor";
    public static final String PROVINCE_TERRITORY = "NS";

    /** This is 53 chars long, should be <50 */
    public static final String BAD_ADDRESS = "olg09tkg9ZOX8ozbvs9SAXbu7rr1lLmoseyJ0guC4A6jE2hCTVxpc";
    /** This is 3 chars long, should be 9 */
    public static final String BAD_BUSINESS_NUMBER = "123";
    /** This is 1 char long, should be at least 2 */
    public static final String BAD_BUSINESS_NAME_1 = ".";
    /** This is 53 chars long, should be <49 */
    public static final String BAD_BUSINESS_NAME_2 = BAD_ADDRESS;

    private MyApplicationData appData;
    private Business business;

    @Rule
    public ActivityTestRule<CreateBusinessActivity> createBusinessActivityRule =
            new ActivityTestRule<>(CreateBusinessActivity.class);

    @Before
    public void init() {
        appData = (MyApplicationData) createBusinessActivityRule.getActivity().getApplication();
        appData.firebaseDBInstance = FirebaseDatabase.getInstance();
        appData.firebaseReference = appData.firebaseDBInstance.getReference("businesses");
        createBusinessActivityRule.launchActivity(new Intent());
    }

    @Test
    public void inputBusiness() {
        onView(withId(R.id.number)).perform(typeText(BUSINESS_NUMBER), closeSoftKeyboard());
        onView(withId(R.id.name)).perform(typeText(BUSINESS_NAME), closeSoftKeyboard());
        onView(withId(R.id.type_spin)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.address)).perform(typeText(ADDRESS), closeSoftKeyboard());
        onView(withId(R.id.provTerr_spin)).perform(click());
        onData(anything()).atPosition(5).perform(click());

        onView(withId(R.id.submitButton)).perform(click());
        business = createBusinessActivityRule.getActivity().business;
        assertTrue(createBusinessActivityRule.getActivity().isFinishing());
    }

    @Test
    public void badNumber() {
        onView(withId(R.id.number)).perform(typeText(BAD_BUSINESS_NUMBER), closeSoftKeyboard());
        onView(withId(R.id.name)).perform(typeText(BUSINESS_NAME), closeSoftKeyboard());
        onView(withId(R.id.address)).perform(typeText(ADDRESS), closeSoftKeyboard());

        assertFalse(createBusinessActivityRule.getActivity().isFinishing());
    }

    @Test
    public void badAddress() {
        onView(withId(R.id.number)).perform(typeText(BUSINESS_NUMBER), closeSoftKeyboard());
        onView(withId(R.id.name)).perform(typeText(BUSINESS_NAME), closeSoftKeyboard());
        onView(withId(R.id.address)).perform(typeText(BAD_ADDRESS), closeSoftKeyboard());

        assertFalse(createBusinessActivityRule.getActivity().isFinishing());
    }

    @Test
    public void badName1() {
        onView(withId(R.id.number)).perform(typeText(BUSINESS_NUMBER), closeSoftKeyboard());
        onView(withId(R.id.name)).perform(typeText(BAD_BUSINESS_NAME_1), closeSoftKeyboard());
        onView(withId(R.id.address)).perform(typeText(ADDRESS), closeSoftKeyboard());

        assertFalse(createBusinessActivityRule.getActivity().isFinishing());
    }

    @Test
    public void badName2() {
        onView(withId(R.id.number)).perform(typeText(BUSINESS_NUMBER), closeSoftKeyboard());
        onView(withId(R.id.name)).perform(typeText(BAD_BUSINESS_NAME_2), closeSoftKeyboard());
        onView(withId(R.id.address)).perform(typeText(ADDRESS), closeSoftKeyboard());

        assertFalse(createBusinessActivityRule.getActivity().isFinishing());
    }

    @After
    public void teardown() {
        if (business != null)
            appData.firebaseReference.child(business.uid).removeValue();
    }

}
