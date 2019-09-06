package com.neandril.mynews

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.neandril.mynews.controllers.activities.ResultsActivity
import com.neandril.mynews.controllers.activities.SearchActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ResultsActivityTest {

    private lateinit var mActivity: SearchActivity

    /**
     * Setup the Tests Rules
     */
    @Rule
    @JvmField
    var activityRule = ActivityTestRule<SearchActivity>(
        SearchActivity::class.java
    )

    /**
     * Init the activity
     */
    @Before
    fun init() {
        mActivity = activityRule.activity
    }

    @Test
    @Throws (Exception::class)
    fun searchActivity_displaysWarningMessage_orNextActivity() {
        val activityMonitor = InstrumentationRegistry.getInstrumentation().addMonitor(ResultsActivity::class.java.name, null, false)

        /** Input "trump" for the search field */
        onView(withId(R.id.editText_search_query)).perform(replaceText("trump"))

        /** Click on search button */
        onView(withId(R.id.button_search)).perform(click())

        val nextActivity = InstrumentationRegistry.getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000)

        /** Make sure at least one checkbox is checked */
        if(nextActivity == null){
            onView(withId(R.id.checkbox_politics)).perform(click())
            onView(withId(R.id.button_search)).perform(click())
        }

        // Check nextActivity recyclerview
        onView(withId(R.id.results_recyclerView)).check(matches(isDisplayed()))
    }
}