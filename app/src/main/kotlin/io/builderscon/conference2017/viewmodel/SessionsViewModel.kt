package io.builderscon.conference2017.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.text.TextUtils
import android.view.View
import io.builderscon.client.model.Room
import io.builderscon.client.model.Session
import io.builderscon.client.model.Track
import io.builderscon.conference2017.infra.date.getLongFormatDate
import io.builderscon.conference2017.model.repository.TimetableRepository
import java.util.*

class SessionsViewModel : BaseObservable() {

    private val timetableRepository = TimetableRepository()

    var rooms: List<Room> = listOf()
    var sTimes: List<Date> = listOf()
    var tracksMap: Map<String, String> = mapOf()

    fun getSessions(context: Context, position: Int): List<SessionViewModel> {
        val timetables = timetableRepository.read()
        val sessions = timetables[position].sessions
        val tracks = timetables[position].tracks
        this.tracksMap = extractTracksMap(tracks)
        this.rooms = extractRooms(sessions)
        this.sTimes = extractStimes(sessions)
        val viewModels = sessions.map { session -> SessionViewModel(session, context) }.toList()

        return adjustViewModels(viewModels)
    }

    private fun adjustViewModels(sessionViewModels: List<SessionViewModel>): List<SessionViewModel> {
        // Prepare sessions map
        val sessionMap = LinkedHashMap<String, SessionViewModel>()
        for (viewModel in sessionViewModels) {
            var roomName = viewModel.roomName
            if (TextUtils.isEmpty(roomName)) {
                roomName = rooms[0].name
            }
            sessionMap.put(generateStimeRoomKey(viewModel.getStime(), roomName), viewModel)
        }

        val adjustedViewModels = ArrayList<SessionViewModel>()

        sTimes.forEach { sTime ->
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
                    val empty = SessionViewModel.createEmpty(1)
                    sameTimeViewModels.add(empty)
                }
            }

            val copiedTmpViewModels = ArrayList(sameTimeViewModels)
            sameTimeViewModels.forEach {
                val rowSpan = it.rowSpan
                if (rowSpan < maxRowSpan) {
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

    private fun extractStimes(sessions: List<Session>): List<Date> {
        return sessions.map { it.startsOn }.sorted().distinct()
    }

    private fun extractRooms(sessions: List<Session>): List<Room> {
        return sessions.map { it.room }
                .filterNotNull()
                .sortedBy { tracksMap[it.id] }
                .distinct()
    }


    val loadingVisibility: Int
        @Bindable
        get() {
            if (this.rooms.isEmpty()) {
                return View.VISIBLE
            } else {
                return View.GONE
            }
        }
}
