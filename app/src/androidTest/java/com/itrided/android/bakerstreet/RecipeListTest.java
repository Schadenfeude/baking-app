package com.itrided.android.bakerstreet;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeListTest {
    @Rule
    public ActivityTestRule<RecipeListActivity> activityRule =
            new ActivityTestRule<>(RecipeListActivity.class, true, true);

    /**
     * Check if the list of recipes is properly displayed
     */
    @Test
    public void checkListIsVisible() {
        onView(withId(R.id.recipes_rv)).check(matches(isDisplayed()));
    }
}
