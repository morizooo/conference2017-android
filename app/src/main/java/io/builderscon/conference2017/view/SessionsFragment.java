package io.builderscon.conference2017.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.lucasr.twowayview.TwoWayLayoutManager;
import org.lucasr.twowayview.widget.DividerItemDecoration;
import org.lucasr.twowayview.widget.SpannableGridLayoutManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.builderscon.client.model.Room;
import io.builderscon.conference2017.R;
import io.builderscon.conference2017.databinding.FragmentSessionsBinding;
import io.builderscon.conference2017.databinding.ViewSessionCellBinding;
import io.builderscon.conference2017.view.customview.TouchlessTwoWayView;


public class SessionsFragment extends Fragment {

    SessionsViewModel viewModel = new SessionsViewModel();

    private FragmentSessionsBinding binding;

    private SessionsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        binding = FragmentSessionsBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);

        initView();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        showSessions();
    }

    private int getScreenWidth() {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    private void showSessions() {
        this.renderSessions(viewModel.getSessions(getActivity(), getArguments().getInt("tabIndex")));
    }

    private void initView() {

        binding.recyclerView.setHasFixedSize(true);

        final Drawable divider = ResourcesCompat.getDrawable(getResources(), R.drawable.divider, null);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(divider));

        adapter = new SessionsAdapter(getActivity());
        binding.recyclerView.setAdapter(adapter);

        final ClickGestureCanceller clickCanceller = new ClickGestureCanceller(getActivity(), binding.recyclerView);

        binding.root.setOnTouchListener((v, event) -> {
            clickCanceller.sendCancelIfScrolling(event);

            MotionEvent e = MotionEvent.obtain(event);
            e.setLocation(e.getX() + binding.root.getScrollX(), e.getY() - binding.headerRow.getHeight());
            binding.recyclerView.forceToDispatchTouchEvent(e);
            return false;
        });

        ViewUtil.addOneTimeOnGlobalLayoutListener(binding.headerRow, () -> {
            if (binding.headerRow.getHeight() > 0) {
                binding.recyclerView.getLayoutParams().height = binding.root.getHeight() - binding.border.getHeight()
                        - binding.headerRow.getHeight();
                binding.recyclerView.requestLayout();
                return true;
            } else {
                return false;
            }
        });
    }

    private void renderSessions(List<SessionViewModel> adjustedSessionViewModels) {
        List<Date> stimes = viewModel.getStimes();
        List<Room> rooms = viewModel.getRooms();
        Map<String, String> tracks = viewModel.getTracksMap();

        int sessionsTableWidth = getScreenWidth();
        int minWidth = (int) getResources().getDimension(R.dimen.session_table_min_width);
        if (rooms.size() > 2 && sessionsTableWidth < minWidth) {
            sessionsTableWidth = minWidth;
        }
        binding.recyclerView.setMinimumWidth(sessionsTableWidth);

        RecyclerView.LayoutManager lm = new SpannableGridLayoutManager(
                TwoWayLayoutManager.Orientation.VERTICAL, rooms.size(), stimes.size());
        binding.recyclerView.setLayoutManager(lm);

        renderHeaderRow(rooms, tracks);
        adapter.reset(adjustedSessionViewModels);
    }

    private void renderHeaderRow(List<Room> rooms, Map<String, String> tracks) {
        if (binding.headerRow.getChildCount() == 0) {
            for (Room room : rooms) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_sessions_header_cell, null);
                TextView txtRoomName = view.findViewById(R.id.txt_room_name);
                txtRoomName.setText(tracks.get(room.getId()));
                LinearLayout.LayoutParams params =
                        new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
                txtRoomName.setLayoutParams(params);
                binding.headerRow.addView(view);
            }
        }
    }

    private static class ClickGestureCanceller {

        private GestureDetector gestureDetector;

        ClickGestureCanceller(final Context context, final TouchlessTwoWayView targetView) {
            gestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
                private boolean ignoreMotionEventOnScroll = false;

                @Override
                public boolean onDown(MotionEvent motionEvent) {
                    ignoreMotionEventOnScroll = true;
                    return false;
                }

                @Override
                public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

                    // Send cancel event for item clicked when horizontal scrolling.
                    if (ignoreMotionEventOnScroll) {
                        final long now = SystemClock.uptimeMillis();
                        MotionEvent cancelEvent = MotionEvent.obtain(now, now,
                                MotionEvent.ACTION_CANCEL, 0.0f, 0.0f, 0);
                        targetView.forceToDispatchTouchEvent(cancelEvent);
                        ignoreMotionEventOnScroll = false;
                    }

                    return false;
                }

                @Override
                public void onShowPress(MotionEvent motionEvent) {
                    // Do nothing
                }

                @Override
                public boolean onSingleTapUp(MotionEvent motionEvent) {
                    // Do nothing
                    return false;
                }

                @Override
                public void onLongPress(MotionEvent motionEvent) {
                }

                @Override
                public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                    return false;
                }
            });
        }

        void sendCancelIfScrolling(MotionEvent event) {
            gestureDetector.onTouchEvent(event);
        }
    }

    public class SessionsAdapter extends ArrayRecyclerAdapter<SessionViewModel, BindingHolder<ViewSessionCellBinding>> {

        SessionsAdapter(@NonNull Context context) {
            super(context);
        }

        @Override
        public BindingHolder<ViewSessionCellBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BindingHolder<>(getContext(), parent, R.layout.view_session_cell);
        }

        @Override
        public void onBindViewHolder(BindingHolder<ViewSessionCellBinding> holder, int position) {
            SessionViewModel viewModel = getItem(position);
            holder.binding.setViewModel(viewModel);
            holder.binding.executePendingBindings();
        }
    }

}

class BindingHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {

    final T binding;

    BindingHolder(@NonNull Context context, @NonNull ViewGroup parent, @LayoutRes int layoutResId) {
        super(LayoutInflater.from(context).inflate(layoutResId, parent, false));
        binding = DataBindingUtil.bind(itemView);
    }
}


abstract class ArrayRecyclerAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    private final List<T> list;

    final Context context;

    ArrayRecyclerAdapter(@NonNull Context context) {
        this(context, new ArrayList<>());
    }


    private ArrayRecyclerAdapter(@NonNull Context context, @NonNull List<T> list) {
        this.context = context;
        this.list = list;
    }

    @UiThread
    void reset(Collection<T> items) {
        clear();
        addAll(items);
        notifyDataSetChanged();
    }

    private void clear() {
        list.clear();
    }

    private void addAll(Collection<T> items) {
        list.addAll(items);
    }

    T getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public Context getContext() {
        return context;
    }

}

