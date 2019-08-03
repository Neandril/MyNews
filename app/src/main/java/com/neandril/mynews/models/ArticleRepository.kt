package com.neandril.mynews.models

import android.accessibilityservice.GestureDescription
import android.app.AlertDialog
import android.util.Log
import android.widget.Toast
import com.neandril.mynews.api.ApiInterface
import com.neandril.mynews.controllers.activities.ResultsActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface ArticleRepositoryInt {
    fun getTopStoriesData(callback: ArticleCallback)
    fun getMostPopularData(callback: ArticleCallback)
    fun getScienceData(callback: ArticleCallback)
    fun getTechnologyData(callback: ArticleCallback)
}

interface SearchRepositoryInt {
    fun getSearchData(callback: SearchCallback)
}

class ArticleRepositoryImplement(private val service: ApiInterface?): ArticleRepositoryInt {
    override fun getTopStoriesData(callback: ArticleCallback) {
        service?.topStories()?.enqueue(object : Callback<NYTModel> {
            /** Handle responses */
            override fun onResponse(call: Call<NYTModel>?, response: Response<NYTModel>?) {
                callback.onResponse(response?.body())
            }

            /** Handle failure */
            override fun onFailure(call: Call<NYTModel>?, t: Throwable?) {
                callback.onResponse(null)
            }
        })
    }

    override fun getMostPopularData(callback: ArticleCallback) {
        service?.mostPopular()?.enqueue(object : Callback<NYTModel> {
            /** Handle responses */
            override fun onResponse(call: Call<NYTModel>?, response: Response<NYTModel>?) {
                callback.onResponse(response?.body())
            }

            /** Handle failure */
            override fun onFailure(call: Call<NYTModel>?, t: Throwable?) {
                callback.onResponse(null)
            }
        })
    }

    override fun getScienceData(callback: ArticleCallback) {
        service?.science()?.enqueue(object : Callback<NYTModel> {
            /** Handle responses */
            override fun onResponse(call: Call<NYTModel>?, response: Response<NYTModel>?) {
                callback.onResponse(response?.body())
            }

            /** Handle failure */
            override fun onFailure(call: Call<NYTModel>?, t: Throwable?) {
                callback.onResponse(null)
            }
        })
    }

    override fun getTechnologyData(callback: ArticleCallback) {
        service?.technology()?.enqueue(object : Callback<NYTModel> {
            /** Handle responses */
            override fun onResponse(call: Call<NYTModel>?, response: Response<NYTModel>?) {
                callback.onResponse(response?.body())
            }

            /** Handle failure */
            override fun onFailure(call: Call<NYTModel>?, t: Throwable?) {
                callback.onResponse(null)
            }
        })
    }
}

class SearchRepositoryImplement(private val service: ApiInterface?, private val query : ArrayList<String>): SearchRepositoryInt {
    override fun getSearchData(callback: SearchCallback) {
        Log.e("Repository", "Query : " + query[0] + " bDate" + query[1] + " eDate" + query[2] + " section" + query[3])
        service?.articleSearch(query[0], query[1], query[2], query[3], "newest")?.enqueue(object : Callback<NYTSearchResultsModel> {
            /** Handle responses */
            override fun onResponse(call: Call<NYTSearchResultsModel>?, response: Response<NYTSearchResultsModel>?) {
                Log.e("Repository", response?.body().toString())
                callback.onResponse(response?.body())
            }

            /** Handle failure */
            override fun onFailure(call: Call<NYTSearchResultsModel>?, t: Throwable?) {
                Log.e("Repository", t?.message)
                callback.onResponse(null)
            }
        })
    }
}

interface ArticleCallback {
    fun onResponse(model: NYTModel?)
}

interface SearchCallback {
    fun onResponse(model: NYTSearchResultsModel?)
}