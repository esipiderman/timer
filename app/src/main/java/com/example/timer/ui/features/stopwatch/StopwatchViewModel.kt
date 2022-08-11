package com.example.timer.ui.features.stopwatch

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.timer.model.data.StopwatchLap
import com.example.timer.util.convertDateToLong
import com.example.timer.util.convertLongToTime
import java.util.*

class StopwatchViewModel : ViewModel() {

    var startTime = 0L
    var lapTime = 0L
    var nowTime = 0L

    val leftText = mutableStateOf("Start")
    val rightText = mutableStateOf("Lap")

    val stopwatch = mutableStateOf("00:00.00")

    val lapTimeString = mutableStateOf("00:00.00")
    val lapList = mutableStateOf(listOf<StopwatchLap>())

    var timer = Timer()

    fun onStartClick() {
        leftText.value = "Stop"
        rightText.value = "Lap"

        timer = Timer()
        startTime = System.currentTimeMillis()
        lapTime = System.currentTimeMillis()

        timer.schedule(object : TimerTask() {
            override fun run() {
                nowTime = System.currentTimeMillis()
                stopwatch.value = convertLongToTime(nowTime - startTime - 1800000L)
                lapTimeString.value = convertLongToTime(nowTime - lapTime - 1800000L)
            }
        }, 0, 1)
    }

    fun onStopClick() {
        leftText.value = "Resume"
        rightText.value = "Reset"

        timer.cancel()
        timer.purge()
    }

    fun onResumeClick() {
        leftText.value = "Stop"
        rightText.value = "Lap"

        timer = Timer()

        startTime = System.currentTimeMillis()
        lapTime = System.currentTimeMillis()

        val resumeTime = convertDateToLong(stopwatch.value)
        val resumeTimeLap = convertDateToLong(lapTimeString.value)

        timer.schedule(object : TimerTask() {
            override fun run() {
                nowTime = System.currentTimeMillis()
                stopwatch.value = convertLongToTime(nowTime - startTime - 1800000L + resumeTime - 1800000L)
                lapTimeString.value = convertLongToTime(nowTime - lapTime - 1800000L + resumeTimeLap - 1800000L)
            }
        }, 0, 1)

    }

    fun onResetClick() {
        leftText.value = "Start"
        rightText.value = "Lap"
        stopwatch.value = "00:00.00"
        lapTimeString.value = "00:00.00"

        nowTime = 0L
        lapTime = 0L
        lapList.value = listOf()

        timer.cancel()
        timer.purge()
    }

    fun onLapClick() {

        lapList.value += StopwatchLap(
            (lapList.value.size + 1).toString(),
            lapTimeString.value,
            stopwatch.value
        )
        lapTime = nowTime
    }

}