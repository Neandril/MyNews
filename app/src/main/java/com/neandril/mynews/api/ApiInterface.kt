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
    @GET("mostpopular/v2/mostviewed/all-sections/7.json")
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
        @Query("facet_fields") facet_fields: String,
        @Query("page") page: Int,
        @Query("sort") sort: String
    ): Call<NYTSearchResultsModel>
}