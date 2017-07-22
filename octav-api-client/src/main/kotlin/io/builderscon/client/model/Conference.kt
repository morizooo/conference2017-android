package io.builderscon.client.model

import com.squareup.moshi.Json

data class Conference(
    val id: String,
    @Json(name = "dates") val schedules: List<Schedule>,
    @Json(name = "administrators") val administrators: List<Participant>,
    val venues: List<Venue>,
    val sponsors: List<Sponsor>,
    val tracks: List<Track>
)