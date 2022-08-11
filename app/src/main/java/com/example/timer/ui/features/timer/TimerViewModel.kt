package com.example.timer.ui.features.timer

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.timer.util.convertDateToLongWithHour
import com.example.timer.util.convertLongToTimeWithHour
import java.util.*

class TimerViewModel:ViewModel() {

    var timer = Timer()

    var startTime = 0L
    var nowTime = 0L
    var timeLeft = 0L
    var allTime = 0L

    val leftText = mutableStateOf("Start")
    val rightText = mutableStateOf("Cancel")
    val isStarted = mutableStateOf(false)
    val timeInString = mutableStateOf("00:00:00")
    val progress = mutableStateOf(1.0f)

    fun onStartClick(){
        isStarted.value = true
        leftText.value = "Pause"

        timer = Timer()
        startTime = System.currentTimeMillis()
        timeLeft = convertDateToLongWithHour(timeInString.value + ".000")
        allTime = convertDateToLongWithHour(timeInString.value + ".000")

        timer.schedule(object : TimerTask() {
            override fun run() {
                nowTime = System.currentTimeMillis()
                timeLeft = allTime - (nowTime - startTime) + 12600000
                timeInString.value = convertLongToTimeWithHour(allTime - (nowTime - startTime))
                progress.value =  timeLeft.toFloat() / (allTime.toFloat() + 12600000)
                if (timeLeft <= 0){
                    onCancelClick()
                }
            }
        }, 0, 1)

    }

    fun onPauseClick(){
        leftText.value = "Resume"

        timer.purge()
        timer.cancel()
    }

    fun onResumeClick(){
        leftText.value = "Pause"

        timer = Timer()
        startTime = System.currentTimeMillis()
        val allTimeCopy = allTime -( allTime - timeLeft + 12600000 )

        timer.schedule(object : TimerTask() {
            override fun run() {
                nowTime = System.currentTimeMillis()
                timeLeft = allTimeCopy - (nowTime - startTime) + 12600000
                timeInString.value = convertLongToTimeWithHour(allTimeCopy - (nowTime - startTime))
                progress.value =  timeLeft.toFloat() / (allTime.toFloat() + 12600000)
                if (timeLeft <= 0){
                    onCancelClick()
                }
            }
        }, 0, 1)
    }

    fun onCancelClick(){
        isStarted.value = false
        leftText.value = "Start"

        timer.purge()
        timer.cancel()
        timeInString.value = "00:00:00"
    }
}