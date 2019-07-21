package com.neandril.mynews.models

import android.view.View
import android.widget.Toast
import com.neandril.mynews.api.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface ArticleRepositoryInt {
    fun getTopStoriesData(callback: ArticleCallback)
}

class ArticleRepositoryImplement(val service: ApiInterface?): ArticleRepositoryInt {
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
}

interface ArticleCallback {
    fun onResponse(model: NYTModel?)
}