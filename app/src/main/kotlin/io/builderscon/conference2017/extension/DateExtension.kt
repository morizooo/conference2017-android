package io.builderscon.conference2017.extension

import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*

private val FORMAT_KKMM = "kk:mm"
private val FORMAT_YYYYMMDD = "yyyyMMdd"
private val FORMAT_YYYYMMDDKKMM = "yyyyMMMdkkmm"

// TODO use khronos
fun Date.getHourMinute(): String {
    val pattern = DateFormat.getBestDateTimePattern(Locale.getDefault(), FORMAT_KKMM)
    return SimpleDateFormat(pattern, Locale.getDefault()).format(this)
}

fun Date.getFormatDate(): String {
    val pattern = DateFormat.getBestDateTimePattern(Locale.getDefault(), FORMAT_YYYYMMDD)
    return SimpleDateFormat(pattern, Locale.getDefault()).format(this)
}

fun Date.getLongFormatDate(): String {
    val pattern = DateFormat.getBestDateTimePattern(Locale.getDefault(), FORMAT_YYYYMMDDKKMM)
    return SimpleDateFormat(pattern, Locale.getDefault()).format(this)
}