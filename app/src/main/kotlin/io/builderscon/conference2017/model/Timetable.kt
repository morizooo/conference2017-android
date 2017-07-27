package io.builderscon.conference2017.model

import io.builderscon.client.model.Schedule
import io.builderscon.client.model.Session
import io.builderscon.client.model.Track

data class Timetable(val schedule: Schedule, val tracks: List<Track>, val sessions: List<Session>)