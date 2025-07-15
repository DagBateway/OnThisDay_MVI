package com.albertocamillo.onthisdaymvi.data.api

import com.albertocamillo.onthisdaymvi.data.model.WikimediaResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

// ============================
// WikimediaApi.kt
// ============================
/**
 * Defines the Retrofit interface for the Wikimedia "On This Day" API.
 * This interface exposes one function, [getOnThisDay], which fetches historical data
 * (births, deaths, holidays, events) for a given day (month and day path parameters).
 *
 * Retrofit uses this to generate the necessary HTTP calls, and it returns
 * a [WikimediaResponse] object that matches the API response structure.
 */

interface WikimediaApi {
    @GET("feed/v1/wikipedia/en/onthisday/all/{month}/{day}")
    suspend fun getOnThisDay(
        @Path("month") month: String,
        @Path("day") day: String
    ): WikimediaResponse
}

// ============================
// ApiClient.kt
// ============================
/**
 * Provides a lazily initialised singleton instance of the [WikimediaApi] using Retrofit.
 *
 * It sets the base URL for Wikimedia API and configures a Gson converter
 * to parse JSON responses automatically into Kotlin data classes.
 *
 * This object abstracts Retrofit setup so the rest of the app can just use [ApiClient.api].
 */

object ApiClient {
    val api: WikimediaApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.wikimedia.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WikimediaApi::class.java)
    }
}