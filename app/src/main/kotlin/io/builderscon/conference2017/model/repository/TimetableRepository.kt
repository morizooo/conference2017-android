package io.builderscon.conference2017.model.repository

import io.builderscon.client.model.Session
import io.builderscon.conference2017.extension.getFormatDate
import io.builderscon.conference2017.infra.ConferenceDAO
import io.builderscon.conference2017.infra.SessionDAO
import io.builderscon.conference2017.infra.api.ApiConferenceDAO
import io.builderscon.conference2017.infra.api.ApiSessionDAO
import io.builderscon.conference2017.infra.file.FileConferenceDAO
import io.builderscon.conference2017.infra.file.FileSessionDAO
import io.builderscon.conference2017.model.Timetable

class TimetableRepository {

    val conferenceRepository: ConferenceDAO = ApiConferenceDAO().let {
        if (it.id.isEmpty()) FileConferenceDAO() else ApiConferenceDAO()
    }

    val sessionRepository: SessionDAO = ApiSessionDAO().let {
        if (it.id.isEmpty()) FileSessionDAO() else ApiSessionDAO()
    }

    fun read(): List<Timetable> {
        if (cachedTimetable.isEmpty()) cachedTimetable = loadTimetable()
        return cachedTimetable
    }

    private fun loadTimetable(): List<Timetable> {
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

    companion object {
        private var cachedTimetable: List<Timetable> = emptyList()
    }

}