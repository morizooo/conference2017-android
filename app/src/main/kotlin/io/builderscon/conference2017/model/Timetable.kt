package io.builderscon.conference2017.model

import io.builderscon.client.model.Schedule
import io.builderscon.client.model.Session

data class Timetable(val schedule: Schedule, val sessions: List<Session>)