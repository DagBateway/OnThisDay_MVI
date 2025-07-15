package com.albertocamillo.onthisdaymvi.data.api

import com.albertocamillo.onthisdaymvi.data.model.WikimediaResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface WikimediaApi {
    @GET("feed/v1/wikipedia/en/onthisday/all/{month}/{day}")
    suspend fun getOnThisDay(
        @Path("month") month: String,
        @Path("day") day: String
    ): WikimediaResponse
}

object ApiClient {
    val api: WikimediaApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.wikimedia.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WikimediaApi::class.java)
    }
}