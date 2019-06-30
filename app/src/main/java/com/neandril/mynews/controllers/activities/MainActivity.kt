package com.neandril.mynews.controllers.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.neandril.mynews.R
import com.neandril.mynews.views.SectionsPagerAdapter

class MainActivity : AppCompatActivity() {

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
            Toast.makeText(this, "Search clicked", Toast.LENGTH_LONG).show()
            val intent = Intent(this, OptionsActivity::class.java)
            startActivity(intent)
            true
        }
        R.id.action_notification -> {
            Toast.makeText(this, "Notifications clicked", Toast.LENGTH_LONG).show()
            val intent = Intent(this,NotificationsActivity::class.java)
            startActivity(intent)
            true
        }
        R.id.action_help -> {
            Toast.makeText(this, "Help clicked", Toast.LENGTH_LONG).show()
            true
        }
        R.id.action_about -> {
            Toast.makeText(this, "About clicked", Toast.LENGTH_LONG).show()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}
