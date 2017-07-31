package io.builderscon.conference2017.viewmodel

import android.content.Intent
import android.databinding.BaseObservable
import android.support.annotation.DrawableRes
import android.view.View
import io.builderscon.client.model.Session
import io.builderscon.conference2017.R
import io.builderscon.conference2017.extension.getHourMinute
import io.builderscon.conference2017.extension.getLongFormatDate
import io.builderscon.conference2017.extension.needsAdjustColSpan
import io.builderscon.conference2017.view.activity.SessionDetailActivity
import java.util.*

class SessionViewModel(private val session: Session?) : BaseObservable() {

    private var shortStime = ""

    private var title = ""

    internal var roomName = ""

    private var minutes = ""

    private var avatarURL = ""

    internal var rowSpan = 1

    private var colSpan = 1

    private var titleMaxLines = 5

    @DrawableRes
    private var backgroundResId: Int = 0

    private var isClickable = true

    init {
        session?.let {
            this.shortStime = it.startsOn.getHourMinute()
            this.title = it.title
            this.avatarURL = it.speaker.avatarURL
            this.roomName = it.room.name
            this.minutes = "(" + it.duration / 60 + " 分)"
            if (it.startsOn.needsAdjustColSpan()) this.colSpan = 2

            decideRowSpan(it)
        }

    }

    private fun decideRowSpan(session: Session) {
        if (session.duration / 60 > 30) {
            this.rowSpan = this.rowSpan * 2
            this.titleMaxLines = this.titleMaxLines * 2
        }
    }

    fun getStime(): Date? {
        return session?.startsOn
    }

    fun showSessionDetail(view: View) {
        val intent = Intent(view.context, SessionDetailActivity::class.java)
        session?.let {
            intent.putExtra("abstract", session.abstract)
            intent.putExtra("avatarURL", session.speaker.avatarURL)
            intent.putExtra("speakerName", session.speaker.nickname)
            intent.putExtra("title", session.title)
            intent.putExtra("start", session.startsOn.getLongFormatDate())
            intent.putExtra("minutes", (session.duration / 60).toString() + "分")
            intent.putExtra("roomName", roomName)
            intent.putExtra("materialLevel", getLevelTag(session.materialLevel.toString()))
        }
        view.context.startActivity(intent)
    }

    // TODO Refactor
    private fun getLevelTag(level: String): String {
        if (level == "INTERMEDIATE") return "中級者"
        if (level == "ADVANCED") return "上級者"
        return "初級者"
    }

    fun getShortStime(): String {
        return shortStime
    }

    fun getTitle(): String {
        return title
    }

    fun getMinutes(): String {
        return minutes
    }

    fun getRowSpan(): Int {
        return rowSpan
    }

    fun getColSpan(): Int {
        return colSpan
    }

    fun getBackgroundResId(): Int {
        return backgroundResId
    }

    fun isClickable(): Boolean {
        return isClickable
    }

    fun getTitleMaxLines(): Int {
        return titleMaxLines
    }

    fun getAvatarURL(): String {
        return avatarURL
    }

    companion object SessionViewModel {
        fun createEmpty(rowSpan: Int): io.builderscon.conference2017.viewmodel.SessionViewModel {
            return createEmpty(rowSpan, 1)
        }

        fun createEmpty(rowSpan: Int, colSpan: Int): io.builderscon.conference2017.viewmodel.SessionViewModel {
            val empty = SessionViewModel(null)
            return empty.apply {
                empty.rowSpan = rowSpan
                empty.colSpan = colSpan
                isClickable = false
                avatarURL = ""
                backgroundResId = R.drawable.bg_empty_session
            }
        }
    }

}

