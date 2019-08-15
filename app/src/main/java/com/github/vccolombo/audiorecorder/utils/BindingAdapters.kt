package com.github.vccolombo.audiorecorder.utils

import android.os.SystemClock
import android.widget.Chronometer
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

@BindingAdapter("recordingText")
fun setRecordingText(view: TextView, isRecording: MutableLiveData<Boolean>) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null) {
        isRecording.observe(parentActivity, Observer { value ->
            if (value == true) view.text = "Recording"
            else view.text = "Not recording"

        })
    }
}

@BindingAdapter("recordingTimer")
fun recordingTimer(view: Chronometer, isRecording: MutableLiveData<Boolean>) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null) {
        isRecording.observe(parentActivity, Observer { value ->
            if (value == true) {
                view.base = SystemClock.elapsedRealtime()
                view.start()
            }
            else {
                view.stop()
            }
        })
    }
}