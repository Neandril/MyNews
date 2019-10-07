package com.neandril.mynews.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v4.app.NotificationCompat
import com.neandril.mynews.R
import com.neandril.mynews.api.ApiCall
import com.neandril.mynews.controllers.activities.NotificationsActivity
import com.neandril.mynews.models.NYTSearchResultsModel
import com.neandril.mynews.models.NotificationRepositoryImplement
import com.neandril.mynews.models.NotifsCallback
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    /** Variables */
    private var mQueryItems : ArrayList<String> = arrayListOf() // Array of query items
    private lateinit var bDate : String
    private lateinit var eDate : String

    /** Calendar config */
    private val calendar = Calendar.getInstance()
    private val year = calendar.get(Calendar.YEAR)
    private val month = calendar.get(Calendar.MONTH)
    private val day = calendar.get(Calendar.DAY_OF_MONTH)
    private val mm = (month + 1).paddingZero()

    companion object {
        const val PREFS_QUERY = "prefs_query"
        const val PREFS_SECTION = "prefs_sections"
    }

    private var prefs: SharedPreferences? = null

    private val repository: NotificationRepositoryImplement by lazy {
        NotificationRepositoryImplement(ApiCall.getInstance())
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        bDate = "$year$mm${day.paddingZero()}" // Default beginDate (set to today)
        eDate = "$year$mm${day.paddingZero()}" // Default endDate (set to today)

        prefs = context?.getSharedPreferences(NotificationsActivity.PREFS_FILENAME, 0)
        val query = prefs?.getString(PREFS_QUERY, "")
        val section = prefs?.getString(PREFS_SECTION, "")
        mQueryItems.addAll(listOf(query.toString(), bDate, eDate, section.toString(), "0"))

        repository.getNotifsData(mQueryItems, object : NotifsCallback {
            /** Proceed */
            override fun onResponse(model: NYTSearchResultsModel?) {

                if (model?.results?.docs?.isNullOrEmpty() == true) {
                    sendNotification(context,"My News", context?.getString(R.string.notifsNoResultFound).toString())
                } else {
                    sendNotification(context,"My News", context?.getString(R.string.notifsResultFound, model?.results?.docs?.size).toString())
                }
            }
        })
    }

    /**
     * Function that create the notifications
     */
    fun sendNotification(context: Context?, title: String, message: String) {
        val notificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //If on Oreo then notification required a notification channel.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, "default")
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.mipmap.ic_launcher)

        notificationManager.notify(1, notification.build())
    }
}