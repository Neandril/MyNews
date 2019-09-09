package com.neandril.mynews

import android.content.Context
import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.neandril.mynews.controllers.activities.MainActivity
import com.neandril.mynews.controllers.activities.WebviewActivity
import com.neandril.mynews.utils.MyWorker
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ReadTest {

    private lateinit var mActivity: MainActivity
    private lateinit var articleUrl: String
    private var intent: Intent? = null
    private var targetContext: Context? = null

    var activityMonitor = getInstrumentation().addMonitor(
        WebviewActivity::class.java.name,
        null,
        false
    )


    @Rule
    @JvmField
    var activityRule = ActivityTestRule<WebviewActivity>(
        WebviewActivity::class.java
    )

    @Before
    fun before() {

        targetContext = getInstrumentation().targetContext

        articleUrl = "https://www.nytimes.com/2019/09/09/realestate/gruyere-switzerland-golf-resort-development.html"

        intent = Intent(targetContext, WebviewActivity::class.java)
        intent?.putExtra("URL", articleUrl)
    }

    @After
    fun tearDown() {
        activityRule.finishActivity()
    }

    @Test
    fun backButton() {
        val activityMonitor = getInstrumentation().addMonitor(
            MainActivity::class.java.name, null, false)
        activityRule.launchActivity(intent)

        onView(withId(R.id.webview)).check(matches(isDisplayed()))
        pressBack()

        val nextActivity = getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000)

        if(nextActivity == null){
            onView(withId(R.id.topStories_RecyclerView)).check(matches(isDisplayed()))
        }


        /**
        onView(withContentDescription("Navigate Up")).perform(click())
        onView(withId(R.id.topStories_RecyclerView)).check(matches(isDisplayed()))
        **/
    }

}