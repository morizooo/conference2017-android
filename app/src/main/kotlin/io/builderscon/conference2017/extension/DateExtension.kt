package io.builderscon.conference2017.extension

import java.text.SimpleDateFormat
import java.util.*

private fun getJstFormat(format: String): SimpleDateFormat = SimpleDateFormat(format).apply {
    timeZone = TimeZone.getTimeZone("Asia/Tokyo")
}

fun Date.getHourMinute(): String {
    return getJstFormat("kk:mm").format(this)
}

fun Date.getFormatDate(): String {
    return getJstFormat("yyyyMMdd").format(this)
}

fun Date.getLongFormatDate(): String {
    return getJstFormat("yyyy/MM/dd kk:mm").format(this)
}

fun Date.toMD(): String {
    return getJstFormat("M/d").format(this)
}

// for display
val closingDate = "2017/08/05 16:50"
val ignoreSameTimes = listOf("2017/08/04 11:30", "2017/08/05 15:50")
val ignoreTmpTimes = listOf("2017/08/04 11:00", "2017/08/05 15:20")

fun Date.needAddEmptyTime(): Boolean {
    return this.getLongFormatDate() == closingDate
}

fun Date.ignoreSameTimes(): Boolean {
    return ignoreSameTimes.contains(this.getLongFormatDate())
}

fun Date.ignoreTmpTimes(): Boolean {
    return ignoreTmpTimes.contains(this.getLongFormatDate())
}

fun Date.needsAdjustColSpan(): Boolean {
    return this.getFormatDate() == "20170803"
}