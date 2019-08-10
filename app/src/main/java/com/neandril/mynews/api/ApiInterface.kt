package com.neandril.mynews.api

import com.bumptech.glide.load.engine.bitmap_recycle.IntegerArrayAdapter
import com.neandril.mynews.models.NYTModel
import com.neandril.mynews.models.NYTSearchResultsModel
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query

/**
 * Interface for the NYT APIs
 */
interface ApiInterface {

    companion object {
        // NYT API Key
        const val API_KEY = "aavEEACpR0OtOS8i70CKwCsNJorFyfFf"
        // API base URL
        const val API_BASE_URL = "https://api.nytimes.com/svc/"
    }

    /**
     * TopStories API
     */
    @GET("topstories/v2/world.json")
    fun topStories(): Call<NYTModel>

    @GET("topstories/v2/science.json")
    fun science(): Call<NYTModel>

    @GET("topstories/v2/technology.json")
    fun technology(): Call<NYTModel>

    /**
     * MostPopular API
     */
    @GET("mostpopular/v2/viewed/1.json")
    fun mostPopular(): Call<NYTModel>

    /**
     * Article Search API
     */
    @GET("search/v2/articlesearch.json")
    fun articleSearch(
        @Query("q") query: String,
        @Query("begin_date") beginDate: String,
        @Query("end_date") endDate: String,
        @Query("fq") sections: String,
        @Query("page") page: Int,
        @Query("sort") sort: String
    ): Call<NYTSearchResultsModel>
}