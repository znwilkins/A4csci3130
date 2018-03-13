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

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.acme.a3csci3130.CreateBusinessInstrumentedTests.ADDRESS;
import static com.acme.a3csci3130.CreateBusinessInstrumentedTests.BUSINESS_NUMBER;
import static com.acme.a3csci3130.CreateBusinessInstrumentedTests.BUSINESS_NAME;
import static com.acme.a3csci3130.CreateBusinessInstrumentedTests.BUSINESS_TYPE;
import static com.acme.a3csci3130.CreateBusinessInstrumentedTests.PROVINCE_TERRITORY;
import static com.acme.a3csci3130.CreateBusinessInstrumentedTests.BAD_ADDRESS;
import static com.acme.a3csci3130.CreateBusinessInstrumentedTests.BAD_BUSINESS_NUMBER;
import static com.acme.a3csci3130.CreateBusinessInstrumentedTests.BAD_BUSINESS_NAME_1;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * This class contains tests which assert the correct functionality of
 * the DetailViewActivity.
 */
@RunWith(AndroidJUnit4.class)
public class DetailViewInstrumentedTests {

    private static final String UID = "TESTING_UID_12345";
    private static final Business BUSINESS = new Business(UID, BUSINESS_NUMBER, BUSINESS_NAME,
            BUSINESS_TYPE, ADDRESS, PROVINCE_TERRITORY);
    private MyApplicationData appData;

    @Rule
    public ActivityTestRule<DetailViewActivity> createDetailActivityRule =
            new ActivityTestRule<>(DetailViewActivity.class);

    @Before
    public void init() {
        appData = (MyApplicationData) createDetailActivityRule.getActivity().getApplication();
        appData.firebaseDBInstance = FirebaseDatabase.getInstance();
        appData.firebaseReference = appData.firebaseDBInstance.getReference("businesses");

        appData.firebaseReference.child(UID).setValue(BUSINESS);

        Intent intent = new Intent();
        intent.putExtra("Business", BUSINESS);
        createDetailActivityRule.launchActivity(intent);

        createDetailActivityRule.getActivity().receivedBusiness = new Business(UID, BUSINESS_NUMBER,
                BUSINESS_NAME, BUSINESS_TYPE, ADDRESS, PROVINCE_TERRITORY);
    }

    @Test
    public void changeNumber() {
        onView(withId(R.id.number)).perform(
                clearText(),
                typeText(BUSINESS_NUMBER),
                closeSoftKeyboard()
        );

        onView(withId(R.id.updateButton)).perform(click());

        assertTrue(createDetailActivityRule.getActivity().isFinishing());
    }

    @Test
    public void changeName() {
        onView(withId(R.id.name)).perform(
                clearText(),
                typeText(BUSINESS_NAME),
                closeSoftKeyboard()
        );

        onView(withId(R.id.updateButton)).perform(click());

        assertTrue(createDetailActivityRule.getActivity().isFinishing());
    }

    @Test
    public void changeAddress() {
        onView(withId(R.id.address)).perform(
                clearText(),
                typeText(ADDRESS),
                closeSoftKeyboard()
        );

        onView(withId(R.id.updateButton)).perform(click());

        assertTrue(createDetailActivityRule.getActivity().isFinishing());
    }

    @Test
    public void badNumber() {
        onView(withId(R.id.number)).perform(
                clearText(),
                typeText(BAD_BUSINESS_NUMBER),
                closeSoftKeyboard()
        );

        onView(withId(R.id.updateButton)).perform(click());

        assertFalse(createDetailActivityRule.getActivity().isFinishing());
    }

    @Test
    public void badName() {
        onView(withId(R.id.name)).perform(
                clearText(),
                typeText(BAD_BUSINESS_NAME_1),
                closeSoftKeyboard()
        );

        onView(withId(R.id.updateButton)).perform(click());

        assertFalse(createDetailActivityRule.getActivity().isFinishing());
    }

    @Test
    public void badAddress() {
        onView(withId(R.id.address)).perform(
                clearText(),
                typeText(BAD_ADDRESS),
                closeSoftKeyboard()
        );

        onView(withId(R.id.updateButton)).perform(click());

        assertFalse(createDetailActivityRule.getActivity().isFinishing());
    }

    @Test
    public void deleteBusiness() {
        onView(withId(R.id.deleteButton)).perform(click());

        assertTrue(createDetailActivityRule.getActivity().isFinishing());
    }

    @After
    public void teardown() {
        appData.firebaseReference.child(UID).removeValue();
    }

}
