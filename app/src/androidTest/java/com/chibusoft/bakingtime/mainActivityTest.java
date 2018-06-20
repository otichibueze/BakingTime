package com.chibusoft.bakingtime;

/**
 * Created by EBELE PC on 6/17/2018.
 */
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.chibusoft.bakingtime.RecyclerViewItemCountAssertion.withItemCount;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.anything;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import static com.chibusoft.bakingtime.RecyclerViewItemCountAssertion.withItemCount;
//your.package.RecyclerViewItemCountAssertion.withItemCount;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import timber.log.Timber;



@RunWith(AndroidJUnit4.class)
@LargeTest
public class mainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        // To prove that the test fails, omit this call:
        IdlingRegistry.getInstance().register(mIdlingResource);
       // IdlingRegistry.getInstance().register(Espresso  EspressoIdlingResource.getIdlingResource());
       // Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void retrives_Data()
    {
       // onView(withId(R.id.rv_baking)).perform()
       // onView(withId(R.id.rv_baking)).check(withItemCount(3));

//        onView(withId(R.id.recyclerView)).check(new RecyclerViewItemCountAssertion(5));
//        onView(withId(R.id.recyclerView)).check(new RecyclerViewItemCountAssertion(greaterThan(5));
//        onView(withId(R.id.recyclerView)).check(new RecyclerViewItemCountAssertion(lessThan(5));
    }

    @Test
    public void clickItem_onMainActivity()
    {

        onView(withId(R.id.rv_baking)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.img_text)));

       // onView(withId(R.id.rv_baking)).perform(click(0,)));

        //onView(withId(R.id.img_text)).check(matches(withText("Nutella Pie")));

        //onData(anything()).inAdapterView(withId(R.id.rv_baking)).atPosition(0).on

       //onView(withId(R.id.rv_baking)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        //onView(withId(R.id.rv_baking)).check(matches(hasDescendant(withText("Some text"))));

       // onView(nthChildOf(withId(R.id.rv_baking), 0).check(matches(hasDescendant(withText("Some text"))));

//        onView(withId(R.id.rv_baking)).perform(
//                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.)));
       }


    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
          // Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }


    //this is used to get count of items in recycle view


//    @Test
//    public void clickItem_onMainActivity()
//    {
//        onView(withId(R.id.rv_baking)).check(matches(hasDescendant(withText("Some text"))));
//
//        onView(nthChildOf(withId(R.id.rv_baking), 0).check(matches(hasDescendant(withText("Some text"))));
//
//        onView(withId(R.id.rv_baking)).perform(
//                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.)));
 //   }
}
