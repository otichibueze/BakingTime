package com.chibusoft.bakingtime;

/**
 * Created by EBELE PC on 6/17/2018.
 */

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.chibusoft.bakingtime.RecyclerViewItemCountAssertion.withItemCount;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;



@RunWith(AndroidJUnit4.class)
@LargeTest
public class DisplayTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private CountingIdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        // To prove that the test fails, omit this call:
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    public static RecyclerMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerMatcher(recyclerViewId);
    }


    @Test
    public void retrives_Data()
    {
        //Confirm Recycler view item count with RecyclerViewItemCountAssertion
        onView(withId(R.id.rv_baking)).check(withItemCount(4));
    }

    @Test
    public void recyclerview_posiiton()
    {
        //Checking that first item retrieved correctly
        onView(withRecyclerView(R.id.rv_baking).atPositionOnView(0, R.id.bake_text)).check(matches(withText("Nutella Pie")));
    }

    @Test
    public void steps_display()
    {
        //Make click on item , at example at position 1 "Brownies"
        onView(ViewMatchers.withId(R.id.rv_baking)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        //Steps fragment displayed on another activity
        onView(withId(R.id.rv_steps)).check(matches(isDisplayed()));
    }

    @Test
    public void each_step_display()
    {
        //Make click on item , at example at position 1 "Brownies"
        onView(ViewMatchers.withId(R.id.rv_baking)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        //Steps fragment displayed on another activity
        onView(withId(R.id.rv_steps)).check(matches(isDisplayed()));

        //Make sure click on item at example at position 1
        onView(ViewMatchers.withId(R.id.rv_steps)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        //Steps fragment display on another activity
        onView(withId(R.id.step_description)).check(matches(isDisplayed()));

    }


    @Test
    public void ingredient_display()
    {
        //Make click on item , at example at position 1 "Brownies"
        onView(ViewMatchers.withId(R.id.rv_baking)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        //Steps fragment displayed on another activity
        onView(withId(R.id.rv_steps)).check(matches(isDisplayed()));

        //click on button
        onView((withId(R.id.ingredient_text))).perform(click());

        //Steps fragment displaced on another activity
        onView(withId(R.id.rv_ingredients)).check(matches(isDisplayed()));
    }



    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }

}
