package com.example.taskmaster;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class TaskDetailTest extends TestCase {

    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<>(MainActivity.class);

    public void setUp() throws Exception {
        super.setUp();
    }

    public void taskDetailTest(){
        onView(withId(R.id.title)).check(matches(isDisplayed()));
        onView(withId(R.id.state)).check(matches(isDisplayed()));
        onView(withId(R.id.description)).check(matches(isDisplayed()));
    }

    public void tearDown() throws Exception {
    }
}