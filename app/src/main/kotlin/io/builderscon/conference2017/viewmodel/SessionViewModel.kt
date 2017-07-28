//package io.builderscon.conference2017.viewmodel
//
//import android.content.Context
//import android.content.Intent
//import android.databinding.BaseObservable
//import android.support.annotation.DrawableRes
//import android.util.Log
//import android.view.View
//import io.builderscon.client.model.Session
//import io.builderscon.conference2017.R
//import io.builderscon.conference2017.infra.date.getHourMinute
//import io.builderscon.conference2017.infra.date.getLongFormatDate
//import io.builderscon.conference2017.view.activity.SessionDetailActivity
//import java.util.*
//
//class SessionViewModel(private val session: Session, context: Context) : BaseObservable(), ISessionViewModel {
//
//    private val shortStime: String
//
//    private val title: String
//
//    private val roomName: String
//
//    private val minutes: String
//
//    private var avatarURL: String
//
//    override val rowSpan: Int = if (session.duration / 60 > 30) 2 else 1
//
//    private var colSpan: Int = 1
//
//    private var titleMaxLines = 5
//
//    @DrawableRes
//    private var backgroundResId: Int = 0
//
//    override val isClickable: Boolean = true
//
//    init {
//        this.shortStime = session.startsOn.getHourMinute()
//        this.title = session.title
//        this.avatarURL = session.speaker.avatarURL
//        this.roomName = session.room.name
//        this.minutes = context.getString(R.string.session_minutes, session.duration / 60)
//        decideRowSpan(session)
//    }
//
//    private fun decideRowSpan(session: Session) {
//        if (session.duration / 60 > 30) {
//            this.titleMaxLines = this.titleMaxLines * 2
//        }
//    }
//
//    internal fun getStime(): Date {
//        return session.startsOn
//    }
//
//    fun showSessionDetail(view: View) {
//        val intent = Intent(view.context, SessionDetailActivity::class.java)
//        intent.putExtra("abstract", session.abstract)
//        intent.putExtra("avatarURL", session.speaker.avatarURL)
//        intent.putExtra("speakerName", session.speaker.nickname)
//        intent.putExtra("title", session.title)
//        intent.putExtra("start", session.startsOn.getLongFormatDate())
//        view.context.startActivity(intent)
//        Log.i("showSessionDetail", session.title)
//    }
//
//    fun getShortStime(): String {
//        return shortStime
//    }
//
//    fun getTitle(): String {
//        return title
//    }
//
//    internal fun getRoomName(): String {
//        return roomName
//    }
//
//    fun getMinutes(): String {
//        return minutes
//    }
//
//    fun getColSpan(): Int {
//        return colSpan
//    }
//
//    fun getBackgroundResId(): Int {
//        return backgroundResId
//    }
//
//    fun getTitleMaxLines(): Int {
//        return titleMaxLines
//    }
//
//    fun getAvatarURL(): String {
//        return avatarURL
//    }
//
//    fun setAvatarURL(avatarURL: String) {
//        this.avatarURL = avatarURL
//    }
//}
//
//class EmptySessionViewModel(override val rowSpan: Int, private val colSpan: Int): ISessionViewModel, BaseObservable() {
//
//    constructor(rowSpan: Int): this(rowSpan, 1)
//
//    private val avatarURL: String = ""
//
//    @DrawableRes
//    private val backgroundResId: Int = R.drawable.bg_empty_session
//
//    override val isClickable: Boolean = false
//
//}
//
//interface ISessionViewModel {
//    val rowSpan: Int
//    val isClickable: Boolean
//}