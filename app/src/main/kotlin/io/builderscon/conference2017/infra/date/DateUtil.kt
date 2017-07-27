package io.builderscon.conference2017.infra.date

import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*

private val FORMAT_KKMM = "kk:mm"

private val FORMAT_YYYYMMDDKKMM = "yyyyMMMdkkmm"

fun Date.getHourMinute(): String {
    val pattern = DateFormat.getBestDateTimePattern(Locale.getDefault(), FORMAT_KKMM)
    return SimpleDateFormat(pattern, Locale.getDefault()).format(date)
}

fun Date.getLongFormatDate(): String {
    val pattern = DateFormat.getBestDateTimePattern(Locale.getDefault(), FORMAT_YYYYMMDDKKMM)
    return SimpleDateFormat(pattern, Locale.getDefault()).format(date)
}