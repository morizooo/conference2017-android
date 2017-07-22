package io.builderscon.client.model

import com.squareup.moshi.Json


data class Venue(
    val id: String,
    val name: String,
    val address: String,
    @Json(name = "place_id") val placeId: String,
    val url: String,
    //        val location: Pair<Double, Double>,
    val rooms: List<Room>
)