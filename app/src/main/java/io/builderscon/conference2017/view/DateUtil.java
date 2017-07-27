package io.builderscon.conference2017.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    private static final String FORMAT_KKMM = "kk:mm";

    private static final String FORMAT_YYYYMMDDKKMM = "yyyyMMMdkkmm";

    private DateUtil() {
        throw new AssertionError("no instance!");
    }

    @NonNull
    public static String getHourMinute(Date date) {
        String pattern = DateFormat.getBestDateTimePattern(Locale.getDefault(), FORMAT_KKMM);
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(date);

    }

    @NonNull
    public static String getLongFormatDate(@Nullable Date date) {
        if (date == null) {
            return "";
        }

        String pattern = DateFormat.getBestDateTimePattern(Locale.getDefault(), FORMAT_YYYYMMDDKKMM);
        return new SimpleDateFormat(pattern, Locale.getDefault()).format(date);

    }
}
