package io.builderscon.conference2017.model.repository

import io.builderscon.client.model.Session
import io.builderscon.conference2017.model.Track
import io.builderscon.conference2017.model.Timetable
import io.builderscon.conference2017.infra.file.FileConferenceDAO
import io.builderscon.conference2017.infra.file.FileSessionApiDAO
import java.text.SimpleDateFormat

class TimetableRepository {
    val fmt = SimpleDateFormat("yyyyMMdd")

    fun read(): List<Timetable>? {
        val conferenceRepository = FileConferenceDAO()
        val sessionRepository = FileSessionApiDAO()

        val conference = conferenceRepository.findAll()
        val sessions = sessionRepository.findAll()

        return conference?.schedules?.map { schedule ->

            val tracks: Map<String, List<Session>>? = sessions?.filter {
                fmt.format(it.startsOn).equals(fmt.format(schedule.open))
            }?.groupBy { it.room.name }
            Timetable(schedule, tracks?.map { Track(it.key, it.value) })
        }
    }

}