package io.builderscon.conference2017.extension

import khronos.toDate
import khronos.toString
import java.util.*

fun Date.getHourMinute(): String {
    return this.toString("kk:mm")
}

fun Date.getFormatDate(): String {
    return this.toString("yyyyMMdd")
}

fun Date.getLongFormatDate(): String {
    return this.toString("yyyy/MM/dd/ kk:mm")
}

fun Date.toMD(): String {
    return this.toString("M/d")
}

// for display
val closingDate = "2017-08-05 16:50".toDate("yyyy-MM-dd hh:mm")
val ignoreSameTimes = listOf("2017-08-04 11:30".toDate("yyyy-MM-dd hh:mm")
        , "2017-08-05 15:50".toDate("yyyy-MM-dd hh:mm"))
val ignoreTmpTimes = listOf("2017-08-04 11:00".toDate("yyyy-MM-dd hh:mm")
        , "2017-08-05 15:20".toDate("yyyy-MM-dd hh:mm"))

fun Date.needAddEmptyTime(): Boolean {
    return this == closingDate
}

fun Date.ignoreSameTimes(): Boolean {
    return ignoreSameTimes.contains(this)
}

fun Date.ignoreTmpTimes(): Boolean {
    return ignoreTmpTimes.contains(this)
}

fun Date.needsAdjustColSpan(): Boolean {
    return this.getFormatDate() == "20170803"
}