package com.neandril.mynews

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.neandril.mynews.controllers.activities.MainActivity
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var mActivity: MainActivity

    /**
     * Setup the Tests Rules
     */
    @Rule @JvmField
    var activityRule = ActivityTestRule<MainActivity>(
        MainActivity::class.java
    )

    /**
     * Init the activity
     */
    @Before
    fun init() {
        mActivity = activityRule.activity
    }

    /**
     * Test that the tabs are visible
     */
    @Test
    @Throws (Exception::class)
    fun tabsVisible() {
        onView(withId(R.id.tabs)).check(matches(isDisplayed()))
    }

    /**
     * Test if the Search button runs the correct activity
     */
    @Test @Throws (Exception::class)
    fun buttonSearch_OpenSearchActivity() {
        onView(withId(R.id.action_search))
            .check(matches(isDisplayed()))
        onView(withId(R.id.action_search))
            .perform(click())
        onView(withId(R.id.editText_search_query))
            .check(matches(isDisplayed()))
        onView(withId(R.id.notifications_switch))
            .check(matches(not(isDisplayed())))
    }

    /**
     * Test if the Notification Button opens the correct activity
     */
    @Test @Throws(Exception::class)
    fun buttonNotifications_OpenNotificationsActivity() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext())
        onView(withText(mActivity.getString(R.string.notifications))).perform(click())
    }
}