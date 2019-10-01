package com.neandril.mynews

import android.content.SharedPreferences
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.util.Log
import com.neandril.AssetReaderUtil
import com.neandril.mynews.controllers.activities.MainActivity
import com.neandril.mynews.views.adapter.DataAdapter
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ReadTest {

    private val mockServer = MockWebServer()
    lateinit var prefs: SharedPreferences

    companion object {
        private const val PREFS_FILENAME = "com.neandril.mynews.prefs" // Pref filename
        private const val PREFS_URL = "prefs_url" // Url of article
    }

    @Rule
    @JvmField
    var activityRule = ActivityTestRule<MainActivity>(
        MainActivity::class.java, false, false
    )

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

    }

    @Test
    fun shouldDisplayTopStoriesTab() {
        activityRule.launchActivity(null)
        prefs = activityRule.activity.getSharedPreferences(PREFS_FILENAME, 0)
        onView(withId(R.id.topStories_RecyclerView)).check(matches(
            isDisplayed()))
    }

    @Test
    fun shouldUrlOfClickedItem_isCorrectlyStoredInSharedPreferences() {
        /** Run the activity */
        activityRule.launchActivity(null)

        prefs = activityRule.activity.getSharedPreferences(PREFS_FILENAME, 0)

        Thread.sleep(2000)
        onView(withId(R.id.topStories_RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<DataAdapter.ViewHolder>(0, click()))

        onView(withId(R.id.webview)).check(matches(isDisplayed()))
        onView(isRoot()).perform(ViewActions.pressBack())
        onView(withId(R.id.topStories_RecyclerView)).check(matches(isDisplayed()))

        // prefs = activityRule.activity.getSharedPreferences(PREFS_FILENAME,0)
        val savedUrl = prefs.getString(PREFS_URL, "{}")

        assertEquals(
            "[\"https://www.nytimes.com/2019/09/19/us/politics/intelligence-whistle-blower-complaint-trump.html\"]",
            savedUrl)
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
        prefs.edit().clear().commit()
    }

}