package io.builderscon.client.model

import com.squareup.moshi.Json

data class Room(
    val id: String,
    @Json(name = "venue_id") val venueId: String,
    val name: String,
    val capacity: Int
)