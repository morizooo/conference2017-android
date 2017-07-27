package io.builderscon.conference2017.model.repository

import io.builderscon.client.model.Session
import io.builderscon.conference2017.infra.file.FileConferenceDAO
import io.builderscon.conference2017.infra.file.FileSessionApiDAO
import io.builderscon.conference2017.model.Timetable
import java.text.SimpleDateFormat

class TimetableRepository {
    val fmt = SimpleDateFormat("yyyyMMdd")

    fun read(): List<Timetable> {
        val conferenceRepository = FileConferenceDAO()
        val sessionRepository = FileSessionApiDAO()

        val conference = conferenceRepository.findAll()
        val sessions = sessionRepository.findAll()

        val tracks = conference?.tracks ?: emptyList()

        return conference?.schedules?.map { schedule ->

            val sessions: List<Session> = sessions?.filter {
                fmt.format(it.startsOn).equals(fmt.format(schedule.open))
            } ?: emptyList()

            Timetable(schedule, tracks, sessions)
        } ?: emptyList()
    }

}