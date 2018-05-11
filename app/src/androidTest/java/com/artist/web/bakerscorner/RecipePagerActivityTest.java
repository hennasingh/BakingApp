package com.artist.web.bakerscorner;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.artist.web.bakerscorner.IdlingResource.SimpleIdlingResource;
import com.artist.web.bakerscorner.activities.RecipeListActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Created by User on 30-Apr-18.
 */

@RunWith(AndroidJUnit4.class)
public class RecipePagerActivityTest {

    private final static int RECIPE_LIST_SCROLL_POSITION = 1;
    private final static String RECIPE_NAME = "Brownies";

    @Rule
    public ActivityTestRule<RecipeListActivity> mActivityRule = new ActivityTestRule<>(RecipeListActivity.class);



    @Before
    public void registerIdlingResource() {
        IdlingRegistry.getInstance().register(SimpleIdlingResource.getIdlingResource());
    }


    @Test
    public void testRecipeNameAtPosition() {
        // Perform scroll action on Recipe RecyclerView list
        onView(withId(R.id.rv_recipes))
                .perform(RecyclerViewActions
                        .scrollToPosition(RECIPE_LIST_SCROLL_POSITION));

        // Check if recipe name as displayed on the specified position of Recipe RecyclerView list matches the given name
        onView(withText(RECIPE_NAME))
                .check(matches(isDisplayed()));
    }


    @Test
    public void launchRecipePagerActivityTest() {

        onView(withId(R.id.rv_recipes)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        Context targetContext = InstrumentationRegistry.getTargetContext();
        Boolean isTwoPane = targetContext.getResources().getBoolean(R.bool.two_pane);

        if (isTwoPane) {
            onView(withId(R.id.player_layout)).check(matches(isDisplayed()));
        } else {
            onView(withId(R.id.sliding_tabs)).check(matches(isDisplayed()));
        }

    }

    @After
    public void unregisterIdlingResource() {

        IdlingRegistry.getInstance().unregister(SimpleIdlingResource.getIdlingResource());
    }
}

