package com.neandril.mynews

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import com.neandril.mynews.controllers.activities.MainActivity
import com.neandril.mynews.controllers.activities.WebviewActivity
import com.neandril.mynews.models.ArticleCallback
import com.neandril.mynews.models.ArticleRepositoryInt
import com.neandril.mynews.models.NYTModel
import com.neandril.mynews.views.adapter.DataAdapter
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.core.inject
import org.koin.test.KoinTest

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleInstrumentedTest: KoinTest {

    private lateinit var mActivity: MainActivity
    private val repository : ArticleRepositoryInt by inject()

    /**
     * Setup the Tests Rules
     */
    @Rule
    @JvmField
    var activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Before
    fun before() {
        mActivity = activityRule.activity
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun check() {
        val activityMonitor = InstrumentationRegistry.getInstrumentation().addMonitor(WebviewActivity::class.java.name, null, false)

        repository.getTopStoriesData(object : ArticleCallback {
            override fun onResponse(model: NYTModel?) {
                Espresso.onView(ViewMatchers.withId(R.id.topStories_RecyclerView)).perform(
                    RecyclerViewActions.actionOnItemAtPosition<DataAdapter.ViewHolder>(0,
                        ViewActions.click()
                    ))
                val nextActivity = InstrumentationRegistry.getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000)
                if (nextActivity == null) {
                    Espresso.onView(ViewMatchers.withId(R.id.webview))
                        .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                    }
                }
        })
    }

    /**
    @Test
    fun isRead() {
        repository.getTopStoriesData(object : ArticleCallback {
            override fun onResponse(model: NYTModel?) {
                Espresso.onView(ViewMatchers.withId(R.id.topStories_RecyclerView)).perform(
                    RecyclerViewActions.actionOnItemAtPosition<DataAdapter.ViewHolder>(0,
                        ViewActions.click()
                    ))
                Espresso.onView(ViewMatchers.withId(R.id.webview))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            }
        })
    }
    **/
}
