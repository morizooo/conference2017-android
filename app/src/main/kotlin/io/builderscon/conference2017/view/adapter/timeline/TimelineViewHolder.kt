package io.builderscon.conference2017.view.adapter.timeline

import android.support.v7.widget.RecyclerView
import android.view.View
import io.builderscon.client.model.Session

class TimelineViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    lateinit var session: Session

    fun bind(session: Session) {
        this.session = session
    }
}
