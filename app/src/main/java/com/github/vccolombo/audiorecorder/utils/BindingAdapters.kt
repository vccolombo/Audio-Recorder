package com.github.vccolombo.audiorecorder.utils

import android.os.SystemClock
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import timber.log.Timber

// TODO: Remove this feature when UI overhaul
@BindingAdapter("recordingText")
fun setRecordingText(view: TextView, isRecording: Boolean) {
    if (isRecording) view.text = "Recording"
    else view.text = "Not recording"
}

// TODO: Fix this to take pause into account
@BindingAdapter("recordingTimer")
fun recordingTimer(view: Chronometer, isRecording: Boolean) {
    if (isRecording) {
        view.base = SystemClock.elapsedRealtime()
        view.start()
    } else {
        view.stop()
    }
}
