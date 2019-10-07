package com.neandril.mynews

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.neandril.AssetReaderUtil
import com.neandril.mynews.controllers.activities.MainActivity
import com.neandril.mynews.controllers.activities.ResultsActivity
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * UI Tests for SearchActivity and NotificationsActivity
 */

@RunWith(AndroidJUnit4::class)
class OptionsActivityTest {

    private val mockServer = MockWebServer()
    private lateinit var mActivity: MainActivity

    /**
     * Setup the Tests Rules
     */
    @Rule
    @JvmField
    var activityRule = ActivityTestRule<MainActivity>(
        MainActivity::class.java
    )

    /**
     * Init the activity
     */
    @Before
    fun before() {
        mActivity = activityRule.activity
    }

    /**
     * Test search activity behavior
     */
    @Test
    @Throws (Exception::class)
    fun searchActivity_inputsFilled_shouldDisplayNextActivityOrAnError() {
        val mockDispatcher: Dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                Log.d("PATH", "PATH ${request.path}")
                return MockResponse().setResponseCode(200)
                    .setBody(AssetReaderUtil.asset(InstrumentationRegistry.getInstrumentation().context, "search_results.json"))
            }
        }

        mockServer.dispatcher = mockDispatcher
        mockServer.start(9000)

        onView(withId(R.id.action_search)).check(matches(isDisplayed()))
        onView(withId(R.id.action_search)).perform(click())
        onView(withId(R.id.editText_search_query)).check(matches(isDisplayed()))
        onView(withId(R.id.notifications_switch)).check(matches(not(isDisplayed())))

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
    }

    /**
     * Test NotificationsActivity behavior
     */
    @Test
    fun notificationSwitch_onEnabled_shouldDisplaysWarningMessageOrEnableNotifications() {
        Espresso.openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext())
        onView(withText(mActivity.getString(R.string.notifications))).perform(click())
        onView(withId(R.id.editText_search_query)).check(matches(isDisplayed()))
        onView(withId(R.id.notifications_switch)).check(matches(isDisplayed()))

        Thread.sleep(5000)

        /** Try to enable the switch : it can't be enabled */
        onView(withId(R.id.notifications_switch)).perform(click())
        onView(withId(R.id.notifications_switch)).check(matches(not(isChecked())))

        /** Input "someText" on the field, and check a box : the switch can now be enabled */
        onView(withId(R.id.editText_search_query)).perform(replaceText("someText"))
        onView(withId(R.id.checkbox_arts)).perform(click())
        onView(withId(R.id.notifications_switch)).perform(click())
        onView(withId(R.id.notifications_switch)).check(matches(isChecked()))
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }
}