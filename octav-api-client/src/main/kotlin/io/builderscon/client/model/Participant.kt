package io.builderscon.client.model

import com.squareup.moshi.Json

data class Participant(
    val id: String,
    val nickname: String,
    @Json(name = "first_name") val firstName: String?,
    @Json(name = "last_name") val lastName: String?,
    @Json(name = "avatar_url") val avatarURL: String,
    val lang: String,
    val timezone: String?
)