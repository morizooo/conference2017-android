package io.builderscon.conference2017.model.repository

import io.builderscon.client.model.Session
import io.builderscon.conference2017.extension.getFormatDate
import io.builderscon.conference2017.infra.file.FileConferenceDAO
import io.builderscon.conference2017.infra.file.FileSessionApiDAO
import io.builderscon.conference2017.model.Timetable

class TimetableRepository {

    fun read(): List<Timetable> {
        val conferenceRepository = FileConferenceDAO()
        val sessionRepository = FileSessionApiDAO()

        val conference = conferenceRepository.findAll()
        val sessions = sessionRepository.findAll()

        val tracks = conference?.tracks ?: emptyList()

        return conference?.schedules?.map { schedule ->
            val scheduledSessions: List<Session> = sessions?.filter {
                it.startsOn.getFormatDate() == schedule.open.getFormatDate()
            } ?: emptyList()

            Timetable(schedule, tracks, scheduledSessions)
        } ?: emptyList()
    }

}