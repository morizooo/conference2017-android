package io.builderscon.client.model

import com.squareup.moshi.Json

data class Track(
    val id: String,
    @Json(name = "room_id") val roomId: String,
    val name: String
)