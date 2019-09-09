package com.neandril.mynews

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isEnabled
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.neandril.mynews.controllers.activities.NotificationsActivity
import com.neandril.mynews.utils.MyWorker
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class NotificationsTest {

    private lateinit var mActivity: NotificationsActivity

    /**
     * Setup the Tests Rules
     */
    @Rule
    @JvmField
    var activityRule = ActivityTestRule<NotificationsActivity>(
        NotificationsActivity::class.java
    )

    @Before
    fun before() {
        mActivity = activityRule.activity
    }

    @After
    fun tearDown() {
        WorkManager.getInstance().cancelAllWorkByTag("TestWorker")
        mActivity.finish()
    }

    @Test
    fun notificationSwitch_displaysWarningMessage_orEnableNotifications() {
        val activityMonitor = InstrumentationRegistry.getInstrumentation().addMonitor(
            MyWorker::class.java.name, null, false)

        /** Input "someText" on the field */
        onView(withId(R.id.editText_search_query)).perform(replaceText("someText"))

        /** Enable the switch */
        onView(withId(R.id.notifications_switch)).perform(click())

        val nextActivity = InstrumentationRegistry.getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000)

        /** Make sure at least one checkbox is checked */
        if(nextActivity == null){
            onView(withId(R.id.checkbox_politics)).perform(click())
            onView(withId(R.id.notifications_switch)).perform(click())
        }

        val worker = PeriodicWorkRequest
            .Builder(MyWorker::class.java, 1, TimeUnit.DAYS)
            .addTag("MyTag")
            .build()

        WorkManager.getInstance().enqueue(worker)

        onView(withId(R.id.notifications_switch)).check(matches(isEnabled()))
    }

    @Test
    fun workeManager_NotificationSent() {
        val activityMonitor = InstrumentationRegistry.getInstrumentation().addMonitor(
            MyWorker::class.java.name, null, false)

        /** Configure notifications */
        onView(withId(R.id.editText_search_query)).perform(replaceText("trump"))
        onView(withId(R.id.checkbox_politics)).perform(click())
        onView(withId(R.id.notifications_switch)).perform(click())

        /** Build the worker immediately */
        val worker = PeriodicWorkRequest
            .Builder(MyWorker::class.java, 1, TimeUnit.DAYS)
            .addTag("TestWorker")
            .build()

        WorkManager.getInstance().enqueue(worker)

        val nextActivity = InstrumentationRegistry.getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000)

        if(nextActivity == null){
            onView(withId(R.id.notifications_switch)).check(matches(isEnabled()))
        }
    }
}