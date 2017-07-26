package io.builderscon.conference2017.view;
import android.content.Context;
import android.databinding.BaseObservable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.Date;

import io.builderscon.conference2017.R;
import io.builderscon.client.model.Session;

public class SessionViewModel extends BaseObservable {

    private Session session;

    private String shortStime = "";

    private String formattedDate = "";

    private String title = "";

    private String speakerName = "";

    private String roomName = "";

    private String minutes = "";

    private int rowSpan = 1;

    private int colSpan = 1;

    private int titleMaxLines = 5;

    private int speakerNameMaxLines = 1;

    @DrawableRes
    private int backgroundResId;

    private boolean isClickable;

    private int normalSessionItemVisibility;


    SessionViewModel(@NonNull Session session, Context context) {
        this.session = session;
        this.shortStime = DateUtil.getHourMinute(session.getStartsOn());
        this.formattedDate = DateUtil.getMonthDate(session.getStartsOn());
        this.title = session.getTitle();

        if (session.getSpeaker() != null) {
            this.speakerName = session.getSpeaker().getNickname();
        }
        if (session.getRoom() != null) {
            this.roomName = session.getRoom().getName();
        }
        this.minutes = context.getString(R.string.session_minutes, session.getDuration()/60);

        decideRowSpan(session);
        this.normalSessionItemVisibility = View.VISIBLE;
    }

    private SessionViewModel(int rowSpan, int colSpan) {
        this.rowSpan = rowSpan;
        this.colSpan = colSpan;
        this.isClickable = false;
        this.backgroundResId = R.drawable.bg_empty_session;
        this.normalSessionItemVisibility = View.GONE;
    }

    static SessionViewModel createEmpty(int rowSpan) {
        return createEmpty(rowSpan, 1);
    }

    static SessionViewModel createEmpty(int rowSpan, int colSpan) {
        return new SessionViewModel(rowSpan, colSpan);
    }

    private void decideRowSpan(@NonNull Session session) {
        // Break time is over 30 min, but one row is good
        if (session.getDuration()/ 60  > 30) {
            this.rowSpan = this.rowSpan * 2;
            this.titleMaxLines = this.titleMaxLines * 2;
            this.speakerNameMaxLines = this.speakerNameMaxLines * 3;
        }
    }

    Date getStime() {
        return session.getStartsOn();
    }

//    public void showSessionDetail(@SuppressWarnings("unused") View view) {
//        if (navigator != null && session != null) {
//            navigator.navigateToSessionDetail(session, MainActivity.class);
//        }
//    }

    public boolean checkSession(@SuppressWarnings("UnusedParameters") View view) {
        return true;
    }

    public String getShortStime() {
        return shortStime;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public String getTitle() {
        return title;
    }

    public String getSpeakerName() {
        return speakerName;
    }

    String getRoomName() {
        return roomName;
    }

    public String getMinutes() {
        return minutes;
    }

    public int getRowSpan() {
        return rowSpan;
    }

    public int getColSpan() {
        return colSpan;
    }

    public int getBackgroundResId() {
        return backgroundResId;
    }

    public boolean isClickable() {
        return isClickable;
    }

    public int getTitleMaxLines() {
        return titleMaxLines;
    }

    public int getSpeakerNameMaxLines() {
        return speakerNameMaxLines;
    }

    public int getNormalSessionItemVisibility() {
        return normalSessionItemVisibility;
    }

}
