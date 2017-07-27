package io.builderscon.conference2017.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import java.util.Date;

import io.builderscon.client.model.Session;
import io.builderscon.conference2017.R;
import io.builderscon.conference2017.view.activity.SessionDetailActivity;

public class SessionViewModel extends BaseObservable {

    private Session session;

    private String shortStime = "";

    private String title = "";

    private String roomName = "";

    private String minutes = "";

    private String avatarURL = "";

    private int rowSpan = 1;

    private int colSpan = 1;

    private int titleMaxLines = 5;

    @DrawableRes
    private int backgroundResId;

    private boolean isClickable = true;

    SessionViewModel(@NonNull Session session, Context context) {
        this.session = session;
        this.shortStime = DateUtil.getHourMinute(session.getStartsOn());
        this.title = session.getTitle();

        this.avatarURL = session.getSpeaker().getAvatarURL();
        this.roomName = session.getRoom().getName();
        this.minutes = context.getString(R.string.session_minutes, session.getDuration() / 60);

        decideRowSpan(session);
    }

    private SessionViewModel(int rowSpan, int colSpan) {
        this.rowSpan = rowSpan;
        this.colSpan = colSpan;
        this.isClickable = false;
        this.avatarURL = "";
        this.backgroundResId = R.drawable.bg_empty_session;
    }

    static SessionViewModel createEmpty(int rowSpan) {
        return createEmpty(rowSpan, 1);
    }

    static SessionViewModel createEmpty(int rowSpan, int colSpan) {
        return new SessionViewModel(rowSpan, colSpan);
    }

    private void decideRowSpan(@NonNull Session session) {
        if (session.getDuration() / 60 > 30) {
            this.rowSpan = this.rowSpan * 2;
            this.titleMaxLines = this.titleMaxLines * 2;
        }
    }

    Date getStime() {
        return session.getStartsOn();
    }

    public void showSessionDetail(@SuppressWarnings("unused") View view) {
        Intent intent = new Intent(view.getContext(), SessionDetailActivity.class);
        intent.putExtra("abstract", session.getAbstract());
        intent.putExtra("avatarURL", session.getSpeaker().getAvatarURL());
        intent.putExtra("speakerName", session.getSpeaker().getNickname());
        intent.putExtra("title", session.getTitle());
        intent.putExtra("start", DateUtil.getLongFormatDate(session.getStartsOn()));
        view.getContext().startActivity(intent);
        Log.i("showSessionDetail", session.getTitle());
    }

    public String getShortStime() {
        return shortStime;
    }

    public String getTitle() {
        return title;
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

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

}
