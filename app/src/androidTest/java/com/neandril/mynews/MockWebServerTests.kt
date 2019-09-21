package com.neandril.mynews

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.util.Log
import com.neandril.AssetReaderUtil
import com.neandril.mynews.controllers.activities.MainActivity
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.hamcrest.core.AllOf.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class MockWebServerTests {

    private val mockServer = MockWebServer()
    private lateinit var mActivity: MainActivity

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var activityRule = ActivityTestRule<MainActivity>(
        MainActivity::class.java, false, false
    )

    @Before
    fun setUp() {

    }

    @Test
    fun shouldDisplayTopStoriesTab() {
        activityRule.launchActivity(null)
        onView(withId(R.id.topStories_RecyclerView)).check(matches(
            isDisplayed()))
    }

    @Test
    fun firstTest() {
        val mockDispatcher: Dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                Log.d("PATH", "PATH ${request.path}")
                return MockResponse().setResponseCode(200).setBody(AssetReaderUtil.asset(InstrumentationRegistry.getInstrumentation().context, "request.json"))
            }
        }

        mockServer.dispatcher = mockDispatcher
        mockServer.start(9000)

        activityRule.launchActivity(null)
        Thread.sleep(2000)
        onView(withText("Whistle-Blower’s Complaint Is Said to Involve Multiple Acts by Trump")).check(matches(
        isDisplayed()))
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }
}