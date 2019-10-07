package com.neandril.mynews

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.neandril.AssetReaderUtil
import com.neandril.mynews.controllers.activities.MainActivity
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

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private val mockServer = MockWebServer()
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
    fun before() {
        val mockDispatcher: Dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                Log.d("PATH", "PATH ${request.path}")
                return MockResponse().setResponseCode(200)
                    .setBody(AssetReaderUtil.asset(InstrumentationRegistry.getInstrumentation().context, "request.json"))
            }
        }
        mockServer.dispatcher = mockDispatcher
        mockServer.start(9000)

        mActivity = activityRule.activity
    }

    /**
     * Test that the tabs are visible
     */
    @Test
    @Throws (Exception::class)
    fun onActivityCreated_shouldDisplayTabs() {
        onView(withId(R.id.tabs)).check(matches(isDisplayed()))
    }

    /**
     * Test if the Search button opens the correct activity
     */
    @Test @Throws (Exception::class)
    fun buttonSearch_onClick_shouldOpenSearchActivity() {
        onView(withId(R.id.action_search)).check(matches(isDisplayed()))
        onView(withId(R.id.action_search)).perform(click())
        onView(withId(R.id.editText_search_query)).check(matches(isDisplayed()))
        onView(withId(R.id.notifications_switch)).check(matches(not(isDisplayed())))
    }

    /**
     * Test if the Help button opens the correct activity
     */
    @Test @Throws (Exception::class)
    fun buttonHelp_onClick_shouldOpenHelpActivity() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext())
        onView(withText(mActivity.getString(R.string.action_help))).perform(click())
        onView(withId(R.id.imageView)).check(matches(isDisplayed()))
    }

    /**
     * Test if the About button opens the correct activity
     */
    @Test @Throws (Exception::class)
    fun buttonAbout_onClick_shouldOpenAboutActivity() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext())
        onView(withText(mActivity.getString(R.string.action_about))).perform(click())
        onView(withId(R.id.imageView)).check(matches(isDisplayed()))
    }

    /**
     * Test if the Notification Button opens the correct activity
     */
    @Test @Throws(Exception::class)
    fun buttonNotifications_onClick_shouldOpenNotificationsActivity() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext())
        onView(withText(mActivity.getString(R.string.notifications))).perform(click())
        onView(withId(R.id.editText_search_query)).check(matches(isDisplayed()))
        onView(withId(R.id.notifications_switch)).check(matches(isDisplayed()))
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }
}