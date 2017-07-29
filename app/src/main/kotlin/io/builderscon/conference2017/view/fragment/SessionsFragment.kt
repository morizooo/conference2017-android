package io.builderscon.conference2017.view.fragment

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.os.SystemClock
import android.support.annotation.LayoutRes
import android.support.annotation.UiThread
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import io.builderscon.client.model.Room
import io.builderscon.conference2017.R
import io.builderscon.conference2017.databinding.FragmentSessionsBinding
import io.builderscon.conference2017.databinding.ViewSessionCellBinding
import io.builderscon.conference2017.extension.getScreenWidth
import io.builderscon.conference2017.view.customview.TouchlessTwoWayView
import io.builderscon.conference2017.viewmodel.SessionViewModel
import io.builderscon.conference2017.viewmodel.SessionsViewModel
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import org.lucasr.twowayview.TwoWayLayoutManager
import org.lucasr.twowayview.widget.DividerItemDecoration
import org.lucasr.twowayview.widget.SpannableGridLayoutManager
import java.util.*


class SessionsFragment : Fragment() {
    internal var viewModel = SessionsViewModel()

    lateinit var binding: FragmentSessionsBinding

    lateinit var adapter: SessionsFragment.SessionsAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        binding = FragmentSessionsBinding.inflate(inflater!!, container, false)
        binding.viewModel = viewModel

        initView()
        return binding.getRoot()
    }

    override fun onResume() {
        super.onResume()
        showSessions()
    }

    private fun showSessions() {
        launch(UI) {
            val sessions = async(CommonPool) {
                viewModel.getSessions(arguments.getInt("tabIndex"))
            }.await()
            renderSessions(sessions)
        }
    }

    private fun initView() {

        binding.recyclerView.setHasFixedSize(true)

        val divider = ResourcesCompat.getDrawable(resources, R.drawable.divider, null)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(divider))

        adapter = SessionsAdapter(activity)
        binding.recyclerView.adapter = adapter

        val clickCanceller = ClickGestureCanceller(activity, binding.recyclerView)

        binding.root.setOnTouchListener { _, event ->
            clickCanceller.sendCancelIfScrolling(event)

            val e = MotionEvent.obtain(event)
            e.setLocation(e.x + binding.root.scrollX, e.y - binding.headerRow.height)
            binding.recyclerView.forceToDispatchTouchEvent(e)
            false
        }

    }

    private fun renderSessions(adjustedSessionViewModels: List<SessionViewModel>) {
        val sTimes = viewModel.sTimes
        val rooms = viewModel.rooms
        val tracks = viewModel.tracksMap

        var sessionsTableWidth = activity.getScreenWidth()
        val minWidth = resources.getDimension(R.dimen.session_table_min_width).toInt()
        if (rooms.size > 2 && sessionsTableWidth < minWidth) {
            sessionsTableWidth = minWidth
        }
        binding.recyclerView.minimumWidth = sessionsTableWidth

        val lm = SpannableGridLayoutManager(
                TwoWayLayoutManager.Orientation.VERTICAL, rooms.size, sTimes.size)
        binding.recyclerView.layoutManager = lm

        renderHeaderRow(rooms, tracks)
        adapter.reset(adjustedSessionViewModels)
    }

    private fun renderHeaderRow(rooms: List<Room>, tracks: Map<String, String>) {
        if (binding.headerRow.childCount == 0) {
            for ((id) in rooms) {
                val view = LayoutInflater.from(activity).inflate(R.layout.view_sessions_header_cell, null)
                val txtRoomName = view.findViewById<TextView>(R.id.txt_room_name)
                txtRoomName.text = tracks[id]?.replace("(", "\n(")
                val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f)
                txtRoomName.layoutParams = params
                binding.headerRow.addView(view)
            }
        }
    }

    private class ClickGestureCanceller internal constructor(context: Context, targetView: TouchlessTwoWayView) {

        private val gestureDetector: GestureDetector

        init {
            gestureDetector = GestureDetector(context, object : GestureDetector.OnGestureListener {
                private var ignoreMotionEventOnScroll = false

                override fun onDown(motionEvent: MotionEvent): Boolean {
                    ignoreMotionEventOnScroll = true
                    return false
                }

                override fun onScroll(motionEvent: MotionEvent, motionEvent1: MotionEvent, v: Float, v1: Float): Boolean {

                    // Send cancel event for item clicked when horizontal scrolling.
                    if (ignoreMotionEventOnScroll) {
                        val now = SystemClock.uptimeMillis()
                        val cancelEvent = MotionEvent.obtain(now, now,
                                MotionEvent.ACTION_CANCEL, 0.0f, 0.0f, 0)
                        targetView.forceToDispatchTouchEvent(cancelEvent)
                        ignoreMotionEventOnScroll = false
                    }

                    return false
                }

                override fun onShowPress(motionEvent: MotionEvent) {
                    // Do nothing
                }

                override fun onSingleTapUp(motionEvent: MotionEvent): Boolean {
                    // Do nothing
                    return false
                }

                override fun onLongPress(motionEvent: MotionEvent) {}

                override fun onFling(motionEvent: MotionEvent, motionEvent1: MotionEvent, v: Float, v1: Float): Boolean {
                    return false
                }
            })
        }

        internal fun sendCancelIfScrolling(event: MotionEvent) {
            gestureDetector.onTouchEvent(event)
        }
    }

    inner class SessionsAdapter internal constructor(context: Context) : ArrayRecyclerAdapter<SessionViewModel, BindingHolder<ViewSessionCellBinding>>(context) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<ViewSessionCellBinding> {
            return BindingHolder(context, parent, R.layout.view_session_cell)
        }

        override fun onBindViewHolder(holder: BindingHolder<ViewSessionCellBinding>, position: Int) {
            val viewModel = getItem(position)
            holder.binding.viewModel = viewModel
            holder.binding.executePendingBindings()
        }
    }


}

class BindingHolder<out T : ViewDataBinding>(context: Context, parent: ViewGroup, @LayoutRes layoutResId: Int) : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(layoutResId, parent, false)) {
    val binding: T = DataBindingUtil.bind<T>(itemView)
}


abstract class ArrayRecyclerAdapter<T, VH : RecyclerView.ViewHolder> private constructor(val context: Context, private val list: MutableList<T>) : RecyclerView.Adapter<VH>() {

    constructor(context: Context) : this(context, ArrayList<T>()) {}

    @UiThread
    fun reset(items: Collection<T>) {
        clear()
        addAll(items)
        notifyDataSetChanged()
    }

    private fun clear() {
        list.clear()
    }

    private fun addAll(items: Collection<T>) {
        list.addAll(items)
    }

    fun getItem(position: Int): T {
        return list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

