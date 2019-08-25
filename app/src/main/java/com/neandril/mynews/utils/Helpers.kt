package com.neandril.mynews.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Helpers {

    private var prefs: SharedPreferences? = null

    val PREFS_FILENAME = "com.neandril.mynews.prefs" // Pref filename
    val PREFS_URL = "prefs_url" // Bool : position of the switch
    val gson = Gson()

    fun saveData(url: MutableList<String>) {
        val editor = prefs?.edit()
        val json = gson.toJson(url)
        editor?.putString(PREFS_URL, json)
        editor?.apply()

        Log.e("saveData", "saved : $json")
    }

    fun retrieveData(context: Context) : MutableList<String> {
        prefs = context.getSharedPreferences(PREFS_FILENAME, 0)
        val json = prefs?.getString(PREFS_URL, "[]")

        val urlArrayList: ArrayList<String>
        urlArrayList = if (json == null || json.isEmpty()) {
            ArrayList()
        } else {
            val type = object : TypeToken<MutableList<String>>() {

            }.type
            gson.fromJson(json, type)
        }
        Log.e("retrieveData", "retrieved : $json")
        return urlArrayList
    }
}