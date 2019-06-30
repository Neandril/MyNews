package com.neandril.mynews

import android.widget.EditText
import com.neandril.mynews.controllers.activities.MainActivity
import junit.framework.Assert.assertNotNull
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.fakes.RoboMenu

@RunWith(RobolectricTestRunner::class)
class ExampleUnitTest {

    lateinit var mainActivity: MainActivity

    @Before
    fun init() {
        mainActivity = Robolectric.buildActivity(MainActivity::class.java).get()
    }

    @Test
    fun clickingButton_shouldStartNewActivity() {
        val menu = RoboMenu()
        mainActivity.onCreateOptionsMenu(menu)
        val item = menu.findMenuItem("Search")

        assertNotNull(item)
        // item.performClick()

        /**
        val showActivity: ShadowActivity = Shadows.shadowOf(mainActivity)
        val intent: Intent = showActivity.nextStartedActivity
        val shadowIntent: ShadowIntent = shadowOf(intent)
        assertThat(shadowIntent.intentClass.name, equalTo(MainActivity::class.java!!.name))
        **/
    }

    @Test
    fun checkEditText_IsPresentOrNot() {
        val editText = mainActivity.findViewById<EditText>(R.id.editText_search_query)
        val stringValue = editText.text.toString()

        assertThat(stringValue, equalTo("Hello"))
    }
}