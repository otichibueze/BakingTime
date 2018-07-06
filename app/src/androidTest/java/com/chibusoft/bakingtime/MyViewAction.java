package com.chibusoft.bakingtime;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;

import org.hamcrest.Matcher;

/**
 * Created by EBELE PC on 6/20/2018.
 * This is used to click item inside recycler view
 */

public class MyViewAction {

    public static ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }

}

//      useage on test class
//    onView(withId(R.id.rv_conference_list)).perform(
//        RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id. bt_deliver)));