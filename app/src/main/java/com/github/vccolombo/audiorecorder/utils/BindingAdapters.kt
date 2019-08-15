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
fun setRecordingText(view: TextView, isRecording: MutableLiveData<Boolean>) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null) {
        isRecording.observe(parentActivity, Observer { value ->
            if (value == true) view.text = "Recording"
            else view.text = "Not recording"

        })
    }
}

// TODO: Fix this to take pause into account
@BindingAdapter("recordingTimer")
fun recordingTimer(view: Chronometer, isRecording: MutableLiveData<Boolean>) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null) {
        isRecording.observe(parentActivity, Observer { value ->
            if (value == true) {
                view.base = SystemClock.elapsedRealtime()
                view.start()
            } else {
                view.stop()
            }
        })
    }
}
