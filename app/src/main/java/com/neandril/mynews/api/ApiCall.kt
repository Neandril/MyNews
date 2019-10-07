package com.neandril.mynews.api

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.neandril.mynews.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiCall {

    /** ApiInterface instance  */
    private var mInstance: ApiInterface? = null

    /** Returns the ApiInterface instance  */
    fun getInstance(): ApiInterface? {
        if (mInstance == null) {
            mInstance = getRetrofit().create(ApiInterface::class.java)
        }
        return mInstance
    }

    /**
     * Create Retrofit call
     */
    private fun getRetrofit(): Retrofit {
        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

        // Create the interceptor
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        // Pass the api key as parameter
        val apiKeyInterceptor = Interceptor { chain ->
            var request = chain.request()
            val url = request.url.newBuilder().addQueryParameter("api-key", BuildConfig.API_KEY).build()
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }

        // Call HttpClient with interceptors
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(interceptor)  // Enable debugging
            .build()

        // Return retrofit call
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}