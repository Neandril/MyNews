package com.neandril.mynews.api

import com.neandril.mynews.models.NYTModel
import retrofit2.http.GET
import retrofit2.Call

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

    @GET("topstories/v2/home.json")
    fun topStories(): Call<NYTModel>
}