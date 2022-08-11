package com.example.timer.di

import com.example.timer.ui.features.stopwatch.StopwatchViewModel
import com.example.timer.ui.features.timer.TimerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModules = module {
    viewModel { StopwatchViewModel() }
    viewModel { TimerViewModel() }
}