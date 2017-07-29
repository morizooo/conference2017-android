package io.builderscon.conference2017.extension

import khronos.toString
import java.util.*

fun Date.getHourMinute(): String {
    return this.toString("kk:mm")
}

fun Date.getFormatDate(): String {
    return this.toString("yyyyMMdd")
}

fun Date.getLongFormatDate(): String {
    return this.toString("yyyyMMMdkkmm")
}

fun Date.toMD(): String {
    return this.toString("M/d")
}