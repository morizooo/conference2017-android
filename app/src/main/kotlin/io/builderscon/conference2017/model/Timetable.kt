package io.builderscon.conference2017.model

import io.builderscon.client.model.Schedule

data class Timetable(val schedule: Schedule, val tracks: List<Track>?)