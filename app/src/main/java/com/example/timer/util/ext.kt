package com.example.timer.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("mm:ss.SSS")
    return format.format(date)
}

@SuppressLint("SimpleDateFormat")
fun convertDateToLong(date: String): Long {
    val df = SimpleDateFormat("mm:ss.SSS")
    return df.parse(date).time
}

@SuppressLint("SimpleDateFormat")
fun convertLongToTimeWithHour(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("HH:mm:ss")
    return format.format(date)
}

@SuppressLint("SimpleDateFormat")
fun convertDateToLongWithHour(date: String): Long {
    val df = SimpleDateFormat("HH:mm:ss.SSS")
    return df.parse(date).time
}

fun deleteMillis(date: String):String{
    return date.split(".")[0]
}