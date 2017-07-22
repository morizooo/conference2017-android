package io.builderscon.client.model

import com.squareup.moshi.Json

data class Sponsor(
    val id: String,
    val name: String,
    @Json(name = "logo_url") val logoURL: String,
    @Json(name = "url") val linkURL: String,
    @Json(name = "group_name") val groupName: String
)