package com.neandril.mynews

import android.content.Context
import com.neandril.mynews.utils.AlarmReceiver
import org.junit.Test
import java.util.*

class AlarmNotifsTest {

    var context: Context? = null
    var alarmReceiver: AlarmReceiver? = null
    private var mQueryItems : ArrayList<String> = arrayListOf()

    @Test
    fun sendNotification() {
        mQueryItems.addAll(listOf("query", "20190912", "20190912", "arts", "0"))
        alarmReceiver?.sendNotification(context, "Test", "Result found")
    }
}