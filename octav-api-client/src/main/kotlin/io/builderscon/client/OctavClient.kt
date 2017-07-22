package io.builderscon.client

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Rfc3339DateJsonAdapter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

object OctavClient {
    fun client(): OctavService {
        val moshi = Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .add(KotlinJsonAdapterFactory())
            .build()

        val builder = Retrofit.Builder()
            .baseUrl("https://api.builderscon.io/v2/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return builder.create(OctavService::class.java)
    }
}