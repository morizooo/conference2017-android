package io.builderscon.client.model

import com.squareup.moshi.Json
import khronos.plus
import khronos.seconds
import java.util.*

data class Session(
        val id: String,
        val room: Room,
        val speaker: Participant,
        val title: String,
        val abstract: String,
        val memo: String?,
        @Json(name = "starts_on") val startsOn: Date,
        val duration: Int,
        @Json(name = "material_level") val materialLevel: Level,
        @Json(name = "spoken_language") val spokenLanguage: String,
        @Json(name = "slide_language") val slideLanguage: String
) {
    fun endsOn(): Date {
        return startsOn + duration.seconds
    }
}

enum class Level {
    @Json(name = "beginner") BEGINNER,
    @Json(name = "intermediate") INTERMEDIATE,
    @Json(name = "advanced") ADVANCED
}




