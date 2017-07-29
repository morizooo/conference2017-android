package io.builderscon.conference2017.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import android.text.TextUtils
import io.builderscon.client.model.Room
import io.builderscon.client.model.Session
import io.builderscon.client.model.Track
import io.builderscon.conference2017.extension.getLongFormatDate
import io.builderscon.conference2017.model.repository.TimetableRepository
import khronos.toDate
import java.util.*

class SessionsViewModel : BaseObservable() {

    private val timetableRepository = TimetableRepository()

    var rooms: List<Room> = emptyList()
    var sTimes: List<Date> = emptyList()
    var tracksMap: Map<String, String> = emptyMap()

    suspend fun getSessions(context: Context, position: Int): List<SessionViewModel> {
        val timetables = timetableRepository.read()
        val sessions = timetables[position].sessions
        val tracks = timetables[position].tracks
        this.tracksMap = extractTracksMap(tracks)
        this.rooms = extractRooms(sessions)
        this.sTimes = extractSTimes(sessions)
        val viewModels = sessions.map { session -> SessionViewModel(session, context) }.toList()

        return adjustViewModels(viewModels)
    }

    private fun adjustViewModels(sessionViewModels: List<SessionViewModel>): List<SessionViewModel> {
        val sessionMap = LinkedHashMap<String, SessionViewModel>()
        for (viewModel in sessionViewModels) {
            var roomName = viewModel.roomName
            if (TextUtils.isEmpty(roomName)) {
                roomName = rooms[0].name
            }
            sessionMap.put(generateStimeRoomKey(viewModel.stime, roomName), viewModel)
        }

        val adjustedViewModels = ArrayList<SessionViewModel>()

        val closingDate = "2017-08-05 16:50".toDate("yyyy-MM-dd hh:mm")
        val ignoreSameTimes = listOf("2017-08-04 11:30".toDate("yyyy-MM-dd hh:mm")
                , "2017-08-05 15:50".toDate("yyyy-MM-dd hh:mm"))
        val ignoreTmpTimes = listOf("2017-08-04 11:00".toDate("yyyy-MM-dd hh:mm")
                , "2017-08-05 15:20".toDate("yyyy-MM-dd hh:mm"))

        sTimes.forEach { sTime ->
            if (sTime == closingDate) {
                val empty = SessionViewModel.createEmpty(1)
                adjustedViewModels.add(empty)
            }
            val sameTimeViewModels = ArrayList<SessionViewModel>()
            var maxRowSpan = 1
            (0 until rooms.size).forEach { index ->
                val (_, _, name) = rooms[index]
                val viewModel = sessionMap[generateStimeRoomKey(sTime, name)]
                viewModel?.let { vm ->
                    sameTimeViewModels.add(viewModel)
                    if (vm.rowSpan > maxRowSpan) {
                        maxRowSpan = vm.rowSpan
                    }
                } ?: run {
                    if (!ignoreSameTimes.contains(sTime)) {
                        sameTimeViewModels.add(SessionViewModel.createEmpty(1))
                    }
                }
            }

            val copiedTmpViewModels = ArrayList(sameTimeViewModels)
            sameTimeViewModels.forEach {
                val rowSpan = it.rowSpan
                if (rowSpan < maxRowSpan && !ignoreTmpTimes.contains(sTime)) {
                    // Fill for empty cell
                    copiedTmpViewModels.add(SessionViewModel.createEmpty(maxRowSpan - rowSpan))
                }
            }

            adjustedViewModels.addAll(copiedTmpViewModels)
        }
        return adjustedViewModels
    }

    private fun generateStimeRoomKey(stime: Date, roomName: String): String {
        return stime.getLongFormatDate() + "_" + roomName
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
