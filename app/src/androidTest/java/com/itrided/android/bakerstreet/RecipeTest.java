package com.itrided.android.bakerstreet;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.itrided.android.bakerstreet.data.model.Ingredient;
import com.itrided.android.bakerstreet.data.model.Recipe;
import com.itrided.android.bakerstreet.data.model.Step;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.itrided.android.bakerstreet.RecipeActivity.EXTRA_RECIPE;

/**
 * Instrumented test to check if the activity of a single recipe, once opened, shows
 * the list of steps
 *
 * @author Gregorio Palamà
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeTest {
    private final static int INGREDIENT_QUANTITY = 1;
    private final static String INGREDIENT_MEASURE = "KG";
    private final static String INGREDIENT_NAME = "Sugar";

    private final static int STEP_ID = 0;
    private final static String STEP_DESCRIPTION = "Recipe Introduction";
    private final static String STEP_VIDEO = "";
    private final static String STEP_THUMB = "";

    private final static int RECIPE_ID = 0;
    private final static String RECIPE_NAME = "Sugar in a bowl";
    private final static int RECIPE_SERVINGS = 1;
    private final static String RECIPE_THUMB = "";

    @Rule
    public ActivityTestRule<RecipeActivity> activityRule =
            new ActivityTestRule<>(RecipeActivity.class, true, false);

    @Before
    public void init() {
        final Ingredient ingredient = new Ingredient(INGREDIENT_QUANTITY, INGREDIENT_MEASURE, INGREDIENT_NAME);
        final Step step = new Step(STEP_ID, STEP_DESCRIPTION, STEP_DESCRIPTION, STEP_VIDEO, STEP_THUMB);
        final Recipe recipe = new Recipe(RECIPE_ID, RECIPE_NAME, Collections.singletonList(ingredient),
                Collections.singletonList(step), RECIPE_SERVINGS, RECIPE_THUMB);

        final Intent intent = new Intent();
        intent.putExtra(EXTRA_RECIPE, recipe);
        activityRule.launchActivity(intent);
    }

    @Test
    public void checkListIsVisible() {
        onView(withId(R.id.steps_rv))
                .check(matches(atPosition(0, isDisplayed())));
    }

    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                return viewHolder != null && itemMatcher.matches(viewHolder.itemView);
            }
        };
    }
}
