package io.builderscon.conference2017.view;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.builderscon.client.model.Room;
import io.builderscon.client.model.Session;
import io.builderscon.client.model.Track;
import io.builderscon.conference2017.model.Timetable;
import io.builderscon.conference2017.model.repository.TimetableRepository;

public class SessionsViewModel extends BaseObservable {

    private TimetableRepository timetableRepository = new TimetableRepository();

    private List<Room> rooms;

    private List<Date> stimes;

    private Map<String, String> tracksMap;

    public List<SessionViewModel> getSessions(Context context, int position) {
        List<Timetable> timetables = timetableRepository.read();
        List<Session> sessions = timetables.get(position).getSessions();
        List<Track> tracks = timetables.get(position).getTracks();
        this.tracksMap = extractTracksMap(tracks);
        this.rooms = extractRooms(sessions);
        this.stimes = extractStimes(sessions);
        List<SessionViewModel> viewModels = Stream.of(sessions)
                .map(session -> new SessionViewModel(session, context))
                .toList();

        return adjustViewModels(viewModels);
    }

    private List<SessionViewModel> adjustViewModels(List<SessionViewModel> sessionViewModels) {
        // Prepare sessions map
        final Map<String, SessionViewModel> sessionMap = new LinkedHashMap<>();
        for (SessionViewModel viewModel : sessionViewModels) {
            String roomName = viewModel.getRoomName();
            if (TextUtils.isEmpty(roomName)) {
                // In the case of Welcome talk and lunch time, set dummy room
                roomName = rooms.get(0).getName();
            }
            sessionMap.put(generateStimeRoomKey(viewModel.getStime(), roomName), viewModel);
        }

        final List<SessionViewModel> adjustedViewModels = new ArrayList<>();

        for (Date stime : stimes) {
            final List<SessionViewModel> sameTimeViewModels = new ArrayList<>();
            int maxRowSpan = 1;
            for (int i = 0, size = rooms.size(); i < size; i++) {
                Room room = rooms.get(i);
                SessionViewModel viewModel = sessionMap.get(generateStimeRoomKey(stime, room.getName()));
                if (viewModel != null) {
                    sameTimeViewModels.add(viewModel);
                    if (viewModel.getRowSpan() > maxRowSpan) {
                        maxRowSpan = viewModel.getRowSpan();
                    }
                } else {
                    SessionViewModel empty = SessionViewModel.createEmpty(1);
                    sameTimeViewModels.add(empty);
                }
            }

            List<SessionViewModel> copiedTmpViewModels = new ArrayList<>(sameTimeViewModels);
            for (SessionViewModel tmpViewModel : sameTimeViewModels) {
                int rowSpan = tmpViewModel.getRowSpan();
                if (rowSpan < maxRowSpan) {
                    // Fill for empty cell
                    copiedTmpViewModels.add(SessionViewModel.createEmpty(maxRowSpan - rowSpan));
                }
            }

            adjustedViewModels.addAll(copiedTmpViewModels);
        }

        return adjustedViewModels;
    }

    private String generateStimeRoomKey(@NonNull Date stime, @NonNull String roomName) {
        return DateUtil.getLongFormatDate(stime) + "_" + roomName;
    }

    private Map<String, String> extractTracksMap(List<Track> tracks) {
        return Stream.of(tracks).collect(Collectors.toMap(Track::getRoomId, Track::getName));
    }

    private List<Date> extractStimes(List<Session> sessions) {
        return Stream.of(sessions)
                .map(Session::getStartsOn)
                .sorted()
                .distinct()
                .toList();
    }

    private List<Room> extractRooms(List<Session> sessions) {
        return Stream.of(sessions)
                .map(Session::getRoom)
                .filter(room -> room != null)
                .sorted((lhs, rhs) -> tracksMap.get(lhs.getId()).compareTo(tracksMap.get(rhs.getId())))
                .distinct()
                .toList();
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Date> getStimes() {
        return stimes;
    }

    public Map<String, String> getTracksMap() {
        return tracksMap;
    }


    @Bindable
    public int getLoadingVisibility() {
        if (this.rooms == null) {
            return View.VISIBLE;
        } else {
            return View.GONE;
        }
    }
}
