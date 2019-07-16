package com.neandril.mynews.views

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.neandril.mynews.R
import com.neandril.mynews.controllers.fragments.ScienceFragment
import com.neandril.mynews.controllers.fragments.MostPopularFragment
import com.neandril.mynews.controllers.fragments.TechnologyFragment
import com.neandril.mynews.controllers.fragments.TopStoriesFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_top_stories,
    R.string.tab_most_popular,
    R.string.tab_science,
    R.string.tab_technology
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> {
                TopStoriesFragment()
            }
            1 -> {
                MostPopularFragment()
            }
            2 -> {
                ScienceFragment()
            }
            3 -> {
                TechnologyFragment()
            }
            else -> null
        }
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        // return PlaceholderFragment.newInstance(position + 1)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 3 total pages.
        return 4
    }
}