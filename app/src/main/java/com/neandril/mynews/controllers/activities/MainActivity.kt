package com.neandril.mynews.controllers.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.neandril.mynews.R
import com.neandril.mynews.utils.Helpers
import com.neandril.mynews.views.SectionsPagerAdapter
import com.neandril.mynews.views.adapter.DataAdapter

class MainActivity : AppCompatActivity() {

    companion object {
        const val PREFS_FILENAME = "com.neandril.mynews.prefs"
        const val PREFS_TOGGLE = "prefs_toggle"
    }

    var prefs: SharedPreferences? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.app_name)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        prefs = this.getSharedPreferences(PREFS_FILENAME, 0)
        val notifs = prefs!!.getBoolean(PREFS_TOGGLE, false)

        Log.e("MainActivity", "Notifications status : $notifs")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    // actions on click menu items
    /**
     * Handle click on the menu items
     */
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_search -> {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
            true
        }
        R.id.action_notification -> {
            val intent = Intent(this,NotificationsActivity::class.java)
            startActivity(intent)
            true
        }
        R.id.action_help -> {
            val intent = Intent(this, HelpActivity::class.java)
            startActivity(intent)
            true
        }
        R.id.action_about -> {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    /**
     * Finish activity when reaching the last fragment.
     */
    /**
    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 1) {
            fragmentManager.popBackStack()
        } else {
            finish()
        }
    }
    **/
}
