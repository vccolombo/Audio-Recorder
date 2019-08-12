package com.github.vccolombo.audiorecorder.utils

import android.content.ContextWrapper
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

// Todo: move this to a extensions file
fun View.getParentActivity(): AppCompatActivity?{
    var context = this.context
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

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