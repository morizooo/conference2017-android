package io.builderscon.conference2017.viewmodel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.text.TextUtils
import android.view.View
import com.annimon.stream.Stream
import io.builderscon.client.model.Room
import io.builderscon.client.model.Session
import io.builderscon.client.model.Track
import io.builderscon.conference2017.infra.date.getLongFormatDate
import io.builderscon.conference2017.model.repository.TimetableRepository
import java.util.*

class SessionsViewModel : BaseObservable() {

    private val timetableRepository = TimetableRepository()

    var rooms: List<Room> = listOf()

    var stimes: List<Date> = listOf()

    var tracksMap: Map<String, String> = mapOf()

    fun getSessions(context: Context, position: Int): List<ISessionViewModel> {
        val timetables = timetableRepository.read()
        val sessions = timetables[position].sessions
        val tracks = timetables[position].tracks
        this.tracksMap = extractTracksMap(tracks)
        this.rooms = extractRooms(sessions)
        this.stimes = extractStimes(sessions)
        val viewModels = Stream.of(sessions)
                .map<SessionViewModel> { session -> SessionViewModel(session, context) }
                .toList()

        return adjustViewModels(viewModels)
    }

    private fun adjustViewModels(sessionViewModels: List<SessionViewModel>): List<ISessionViewModel> {
        // Prepare sessions map
        val sessionMap = LinkedHashMap<String, SessionViewModel>()
        for (viewModel in sessionViewModels) {
            var roomName = viewModel.getRoomName()
            if (TextUtils.isEmpty(roomName)) {
                // In the case of Welcome talk and lunch time, set dummy room
                roomName = rooms[0].name
            }
            sessionMap.put(generateStimeRoomKey(viewModel.getStime(), roomName), viewModel)
        }

        val adjustedViewModels = ArrayList<ISessionViewModel>()

        stimes.forEach { stime ->
            val sameTimeViewModels = ArrayList<ISessionViewModel>()
            var maxRowSpan = 1
            (0 until rooms.size).forEach { index ->
                val (_, _, name) = rooms[index]
                val viewModel = sessionMap[generateStimeRoomKey(stime, name)]
                viewModel?.let { vm ->
                    sameTimeViewModels.add(viewModel)
                    if (vm.rowSpan > maxRowSpan) {
                        maxRowSpan = vm.rowSpan
                    }
                } ?: run {
                    val empty = EmptySessionViewModel(1)
                    sameTimeViewModels.add(empty)
                }
            }

            val copiedTmpViewModels = ArrayList(sameTimeViewModels)
            sameTimeViewModels.forEach {
                val rowSpan = it.rowSpan
                if (rowSpan < maxRowSpan) {
                    // Fill for empty cell
                    copiedTmpViewModels.add(EmptySessionViewModel(maxRowSpan - rowSpan))
                }
            }

            adjustedViewModels.addAll(copiedTmpViewModels)
        }
        for (stime in stimes) {
            val sameTimeViewModels = ArrayList<ISessionViewModel>()
            var maxRowSpan = 1
            var i = 0
            val size = rooms.size
            while (i < size) {
                val (_, _, name) = rooms[i]
                val viewModel = sessionMap[generateStimeRoomKey(stime, name)]
                viewModel?.let { vm ->
                    sameTimeViewModels.add(viewModel)
                    if (vm.rowSpan > maxRowSpan) {
                        maxRowSpan = vm.rowSpan
                    }
                } ?: run {
                    val empty = EmptySessionViewModel(1)
                    sameTimeViewModels.add(empty)
                }
                i++
            }

            val copiedTmpViewModels = ArrayList(sameTimeViewModels)
            sameTimeViewModels
                    .map { it.rowSpan }
                    .filter { it < maxRowSpan }
                    .mapTo(// Fill for empty cell
                            copiedTmpViewModels) { EmptySessionViewModel(maxRowSpan - it) }

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
