package com.neandril.mynews

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.*
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.MenuItem
import android.widget.CheckBox
import com.neandril.mynews.controllers.activities.MainActivity
import com.neandril.mynews.models.*
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
class MainActivityTest : KoinTest {


    /**
     * Setup the Tests Rules
     */
    @Rule
    @JvmField
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

    /**
     * Test that the tabs are visible
     */
    @Test
    fun tabsVisible() {
        onView(
            allOf(
                withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                withId(R.id.tabs)
            )
        ).check(matches(isDisplayed()))
    }

    /**
     * Test if the Search button opens the correct activity
     */
    @Test
    @Throws(Exception::class)
    fun buttonSearch_OpensSearchActivity() {
        onView(withId(R.id.action_search)).check(matches(isDisplayed()))
        onView(withId(R.id.action_search)).perform(click())
        onView(withId(R.id.editText_search_query)).check(matches(isDisplayed()))
        onView(withId(R.id.notifications_switch)).check(matches(not(isDisplayed())))
    }

    /**
     * Test if the Help button opens the correct activity
     */
    @Test
    @Throws(Exception::class)
    fun buttonHelp_OpensHelpActivity() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext())

        onData(Matchers.`is`(Matchers.instanceOf<MenuItem>(MenuItem::class.java)))
            .atPosition(1)
            .onChildView(
                withText(activityRule.activity.getString(R.string.action_help))
            )
            .perform(click())

//        onView(withText(activityRule.activity.getString(R.string.action_help))).perform(click())
        onView(withId(R.id.imageView)).check(matches(isDisplayed()))
    }

    /**
     * Test if the About button opens the correct activity
     */
    @Test
    @Throws(Exception::class)
    fun buttonAbout_OpensAboutActivity() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext())

        onData(Matchers.`is`(Matchers.instanceOf<MenuItem>(MenuItem::class.java)))
            .atPosition(2)
            .onChildView(
                withText(activityRule.activity.getString(R.string.action_about))
            )
            .perform(click())

//        onView(withText(activityRule.activity.getString(R.string.action_about))).perform(click())
        onView(withId(R.id.imageView)).check(matches(isDisplayed()))
    }

    /**
     * Test if the Notification Button opens the correct activity
     */
    @Test
    @Throws(Exception::class)
    fun buttonNotifications_OpensNotificationsActivity() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext())
        onData(Matchers.`is`(Matchers.instanceOf<MenuItem>(MenuItem::class.java)))
            .atPosition(0)
            .onChildView(
                withText(activityRule.activity.getString(R.string.action_notifications))
            )
            .perform(click())

//        onView(withText(activityRule.activity.getString(R.string.notifications))).perform(click())
        onView(withHint(R.string.search_query_term)).check(matches(isDisplayed()))
        onView(allOf(instanceOf(CheckBox::class.java), withText(R.string.checkbox_arts))).check(
            matches(isDisplayed())
        )
    }

}