package io.builderscon.client

import io.builderscon.client.model.Conference
import io.builderscon.client.model.Session
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OctavService {
    @GET("conference/lookup")
    fun conference(@Query("id") id: String, @Query("lang") lang: String): Call<Conference>

    @GET("session/list")
    fun sessions(@Query("conference_id") id: String, @Query("lang") lang: String): Call<List<Session>>
}