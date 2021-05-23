package com.example.taskmaster;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.core.internal.deps.guava.collect.Iterables;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public final ActivityScenarioRule<MainActivity> main = new ActivityScenarioRule<>
            (MainActivity.class);

    @Test
    public void storeUserName() {
        onView(withText("My Tasks")).check(matches(isDisplayed()));
        onView(withId(R.id.all_task)).check(matches(withText("ALL TASK")));
        onView(withId(R.id.add_task)).check(matches(withText("ADD TASK")));
        onView(withId(R.id.titleOne)).check(matches(withText("TASK ONE")));
        onView(withId(R.id.titleTwo)).check(matches(withText("TASK TWO")));
        onView(withId(R.id.titleThree)).check(matches(withText("TASK THREE")));
        onView(withId(R.id.setting)).check(matches(withText("SETTING")));
        onView(allOf(withId(R.id.modelTitle),withId(R.id.modelBody),withId(R.id.modelState)));
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
        onView(withId(R.id.username)).check(matches(isDisplayed()));

    }

    @Test
    public void settingButton(){
        onView(withId(R.id.setting)).perform(click());
        onView(withId(R.id.username)).perform(replaceText("osama"));

        onView(withId(R.id.save_name)).perform(click());
        onView(withId(R.id.usernamemain)).check(matches(withText("osama's Tasks")));
    }


    @Test
    public void recyclerViewTest(){
        onView(ViewMatchers.withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.title)).check(matches(withText("ghgh")));
    }













}