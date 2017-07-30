package io.builderscon.conference2017.viewmodel

import android.databinding.BaseObservable
import io.builderscon.client.model.Room
import io.builderscon.client.model.Session
import io.builderscon.client.model.Track
import io.builderscon.conference2017.extension.getLongFormatDate
import io.builderscon.conference2017.extension.ignoreSameTimes
import io.builderscon.conference2017.extension.ignoreTmpTimes
import io.builderscon.conference2017.extension.needAddEmptyTime
import io.builderscon.conference2017.model.repository.TimetableRepository
import java.util.*

class SessionsViewModel : BaseObservable() {

    private val timetableRepository = TimetableRepository()

    var rooms: List<Room> = emptyList()
    var sTimes: List<Date> = emptyList()
    var tracksMap: Map<String, String> = emptyMap()

    suspend fun getSessions(position: Int): List<SessionViewModel> {
        val timetables = timetableRepository.read()
        val sessions = timetables[position].sessions
        val tracks = timetables[position].tracks
        this.tracksMap = extractTracksMap(tracks)
        this.rooms = extractRooms(sessions)
        this.sTimes = extractSTimes(sessions)
        val viewModels = sessions.map { session -> SessionViewModel(session) }.toList()

        return adjustViewModels(viewModels)
    }

    private fun adjustViewModels(sessionViewModels: List<SessionViewModel>): List<SessionViewModel> {
        val sessionMap = LinkedHashMap<String, SessionViewModel>()
        for (viewModel in sessionViewModels) {
            val roomName = viewModel.roomName
            val mapKey = viewModel.getStime()?.let { generateSTimeRoomKey(it, roomName) }
            mapKey?.let { sessionMap.put(it, viewModel) }
        }

        val adjustedViewModels = ArrayList<SessionViewModel>()

        sTimes.forEach { sTime ->
            if (sTime.needAddEmptyTime()) {
                val empty = SessionViewModel.createEmpty(1)
                adjustedViewModels.add(empty)
            }
            val sameTimeViewModels = ArrayList<SessionViewModel>()
            var maxRowSpan = 1
            (0 until rooms.size).forEach { index ->
                val (_, _, name) = rooms[index]
                val viewModel = sessionMap[generateSTimeRoomKey(sTime, name)]
                viewModel?.let { vm ->
                    sameTimeViewModels.add(viewModel)
                    if (vm.rowSpan > maxRowSpan) {
                        maxRowSpan = vm.rowSpan
                    }
                } ?: run {
                    if (!sTime.ignoreSameTimes()) {
                        sameTimeViewModels.add(SessionViewModel.createEmpty(1))
                    }
                }
            }

            val copiedTmpViewModels = ArrayList(sameTimeViewModels)
            sameTimeViewModels.forEach {
                val rowSpan = it.rowSpan
                if (rowSpan < maxRowSpan && !sTime.ignoreTmpTimes()) {
                    // Fill for empty cell
                    copiedTmpViewModels.add(SessionViewModel.createEmpty(maxRowSpan - rowSpan))
                }
            }

            adjustedViewModels.addAll(copiedTmpViewModels)
        }
        return adjustedViewModels
    }

    private fun generateSTimeRoomKey(sTime: Date, roomName: String): String {
        return sTime.getLongFormatDate() + "_" + roomName
    }

    private fun extractTracksMap(tracks: List<Track>): Map<String, String> {
        return tracks.map { Pair(it.roomId, it.name) }.toMap()
    }

    private fun extractSTimes(sessions: List<Session>): List<Date> {
        return sessions.map { it.startsOn }.sorted().distinct()
    }

    private fun extractRooms(sessions: List<Session>): List<Room> {
        return sessions.map { it.room }
                .filterNotNull()
                .sortedBy { tracksMap[it.id] }
                .distinct()
    }
}
