package com.github.vccolombo.audiorecorder.module

import com.github.vccolombo.audiorecorder.ui.recorder.RecorderViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { RecorderViewModel() }
}