package com.example.timer.ui

sealed class NavigationItems(var route: String, var title: String) {
    object Stopwatch : NavigationItems("stopwatch",  "Stopwatch")
    object Timer : NavigationItems("timer", "Timer")
}
