package com.neandril.mynews

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.RecyclerView
import com.neandril.mynews.controllers.activities.MainActivity
import com.neandril.mynews.models.*
import com.neandril.mynews.views.adapter.DataAdapter
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.dsl.module
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

    fun getData(): NYTModel {
        val model = NYTModel()
        model.setArticles(
            arrayListOf(
                Article(
                    "This is the title",
                    "2019-07-20T05:10:00-04:00",
                    "politics"
                )
            )
        )
        return model
    }

    /**
     * Init the activity
     */
    @Before
    fun init() {
        loadKoinModules(module {
            single<ArticleRepositoryInt>(override = true) {
                object : ArticleRepositoryInt {
                    override fun getTopStoriesData(callback: ArticleCallback) {
                        callback.onResponse(getData())
                    }

                    override fun getMostPopularData(callback: ArticleCallback) {
                        callback.onResponse(getData())
                    }

                    override fun getScienceData(callback: ArticleCallback) {
                        callback.onResponse(getData())
                    }

                    override fun getTechnologyData(callback: ArticleCallback) {
                        callback.onResponse(getData())
                    }

                    override fun getTestData(callback: ArticleCallback) {
                        callback.onResponse(getData())
                    }

                }
            }
            single<SearchRepositoryInt>(override = true) {
                object : SearchRepositoryInt {
                    override fun getSearchData(query: ArrayList<String>, callback: SearchCallback) {
                        callback.onResponse(NYTSearchResultsModel("", "", DocDatas(listOf())))
                    }
                }
            }

        })
    }

    @Test
    fun check() {
        Thread.sleep(2000)
        onView(withId(R.id.topStories_RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<DataAdapter.ViewHolder>(0, click()))
        Thread.sleep(2000)
        onView(withId(R.id.webview)).check(matches(isDisplayed()))
        pressBack()
        onView(withId(R.id.topStories_RecyclerView)).check(matches(isDisplayed()))

        // TODO: Check layout background color of item at position 0

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
