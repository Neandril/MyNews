package com.neandril.mynews

import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.View

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import android.support.test.espresso.NoMatchingViewException
import android.support.test.espresso.ViewAssertion
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat


/**
 * Helper class to match RecyclerView items by Danny Roa
 * https://github.com/dannyroa/espresso-samples/blob/master/RecyclerView/app/src/androidTest/java/com/dannyroa/espresso_samples/recyclerview/RecyclerViewMatcher.java
 * Created by dannyroa on 5/10/15.
 */
class RecyclerViewItemCountAssertion(private val expectedCount: Int) : ViewAssertion {

    override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter
        assertThat(adapter!!.itemCount, `is`(expectedCount))
    }
}