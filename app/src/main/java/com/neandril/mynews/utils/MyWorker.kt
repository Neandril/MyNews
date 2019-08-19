package com.neandril.mynews.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.support.annotation.NonNull
import android.support.v4.app.NotificationCompat
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.neandril.mynews.R
import com.neandril.mynews.api.ApiCall
import com.neandril.mynews.controllers.activities.NotificationsActivity
import com.neandril.mynews.models.NYTSearchResultsModel
import com.neandril.mynews.models.NotificationRepositoryImplement
import com.neandril.mynews.models.NotifsCallback
import java.util.*

class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    /** Variables */
    private var mQueryItems : ArrayList<String> = arrayListOf() // Array of query items
    private lateinit var bDate : String
    private lateinit var eDate : String

    companion object {
        const val PREFS_QUERY = "prefs_query"
        const val PREFS_SECTION = "prefs_sections"
    }

    var prefs: SharedPreferences? = null

    /** Calendar config */
    private val calendar = Calendar.getInstance()
    private val year = calendar.get(Calendar.YEAR)
    private val month = calendar.get(Calendar.MONTH)
    private val day = calendar.get(Calendar.DAY_OF_MONTH)
    private val mm = (month + 1).paddingZero()

    private val repository: NotificationRepositoryImplement by lazy {
        NotificationRepositoryImplement(ApiCall.getInstance())
    }

    @NonNull
    override fun doWork(): Result {
        bDate = "$year$mm${day.paddingZero()}" // Default beginDate (set to today)
        eDate = "$year$mm${day.paddingZero()}" // Default endDate (set to today)
        prefs = applicationContext.getSharedPreferences(NotificationsActivity.PREFS_FILENAME, 0)
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)

        if (currentHour == 14) {
            Log.e("Hour", "It's 14")
            getData()
        } else {
            Log.e("Hour", "It's not 14")
        }
        return Result.success()
    }

    private fun getData() {
        val query = prefs?.getString(PREFS_QUERY, "")
        val section = prefs?.getString(PREFS_SECTION, "")
        mQueryItems.addAll(listOf(query.toString(), bDate, eDate, section.toString(), "0"))

        Log.e("Worker", "Query : $mQueryItems")

        repository.getNotifsData(mQueryItems, object : NotifsCallback {
            /** Proceed */
            override fun onResponse(model: NYTSearchResultsModel?) {

                if (model?.results?.docs?.isNullOrEmpty() == true) {
                    Log.e("Worker", "No result : " + (model.results))
                    sendNotification("My News", applicationContext.getString(R.string.notifsNoResultFound))
                } else {
                    Log.e("Worker", "Result found : " + model?.results?.docs?.size)
                    sendNotification("My News", applicationContext.getString(R.string.notifsResultFound, model?.results?.docs?.size))
                }
            }
        })
    }

    /**
     * Function that create the notifications
     */
    fun sendNotification(title: String, message: String) {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //If on Oreo then notification required a notification channel.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, "default")
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.mipmap.ic_launcher)

        notificationManager.notify(1, notification.build())
    }
}