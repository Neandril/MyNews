package com.neandril.mynews

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import com.neandril.mynews.controllers.activities.MainActivity
import com.neandril.mynews.models.ArticleRepositoryInt
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleInstrumentedTest : KoinTest {

    private val repository : ArticleRepositoryInt by inject()
    private lateinit var mActivity: MainActivity

    /**
     * Setup the Tests Rules
     */
    @Rule @JvmField
    var activityRule = ActivityTestRule<MainActivity>(
        MainActivity::class.java
    )

    @Before
    fun before() {
        mActivity = activityRule.activity
    }

    @Test
    fun check() {
        onView(withContentDescription("MOST POPULAR")).perform(click())

        Thread.sleep(2000)
        onView(withId(R.id.mostPopular_RecyclerView)).check(matches(isDisplayed()))

        /**
        repository.getTopStoriesData(object : ArticleCallback {
            override fun onResponse(model: NYTModel?) {

            }
        })
        **/

        // Returns the number of item found
        // onView(withId(R.id.topStories_RecyclerView)).check(RecyclerViewItemCountAssertion(1))
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}
