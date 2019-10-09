package com.neandril.mynews.models

import android.util.Log
import com.neandril.mynews.api.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val CODE_401 = "Unauthorized"
private const val CODE_429 = "Too many requests"
private const val ON_FAILURE = "An error happened"

interface ArticleRepositoryInt {
    fun getTopStoriesData(callback: ArticleCallback)
    fun getMostPopularData(callback: ArticleCallback)
    fun getScienceData(callback: ArticleCallback)
    fun getTechnologyData(callback: ArticleCallback)
}

interface SearchRepositoryInt {
    fun getSearchData(query: ArrayList<String>, callback: SearchCallback)
}

interface NotificationsRepositoryInt {
    fun getNotifsData(query: ArrayList<String>, callback: NotifsCallback)
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

class SearchRepositoryImplement(private val service: ApiInterface?): SearchRepositoryInt {
    override fun getSearchData(query: ArrayList<String> ,callback: SearchCallback) {
        Log.e("Repository", "Query : " + query[0] + " bDate " + query[1] + " eDate " + query[2] + " section " + query[3] + " page " + query[4])
        service?.articleSearch(query[0], query[1], query[2],query[3],"news_desk", query[4].toInt(), "newest")?.enqueue(object : Callback<NYTSearchResultsModel> {
            /** Handle responses */
            override fun onResponse(call: Call<NYTSearchResultsModel>?, response: Response<NYTSearchResultsModel>?) {
                when {
                    response?.code() == 200 -> // Success
                        callback.onResponse(response.body())
                    response?.code() == 401 -> // Unauthorized
                        callback.onError(CODE_401)
                    response?.code() == 429 -> // Too many requests
                        callback.onError(CODE_429)
                }
            }

            /** Handle failure */
            override fun onFailure(call: Call<NYTSearchResultsModel>?, t: Throwable?) {
                callback.onError(t?.message ?: ON_FAILURE)
            }
        })
    }
}

class NotificationRepositoryImplement(private val service: ApiInterface?): NotificationsRepositoryInt {
    override fun getNotifsData(query: ArrayList<String>, callback: NotifsCallback) {
        service?.articleSearch(query[0], query[1], query[2], query[3], "news_desk", query[4].toInt(), "newest")?.enqueue(object : Callback<NYTSearchResultsModel> {
            /** Handle responses */
            override fun onResponse(call: Call<NYTSearchResultsModel>?, response: Response<NYTSearchResultsModel>?) {
                callback.onResponse(response?.body())
            }

            /** Handle failure */
            override fun onFailure(call: Call<NYTSearchResultsModel>?, t: Throwable?) {

            }
        })
    }
}


interface ArticleCallback {
    fun onResponse(model: NYTModel?)
}

interface SearchCallback {
    fun onResponse(model: NYTSearchResultsModel?)
    fun onError(message: String)
}

interface NotifsCallback {
    fun onResponse(model: NYTSearchResultsModel?)
}