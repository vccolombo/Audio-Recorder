package com.github.vccolombo.audiorecorder.ui.recorder

import android.media.MediaRecorder
import android.os.Environment
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber
import java.io.IOException
import java.io.File.separator
import android.os.Environment.DIRECTORY_DCIM
import android.os.Environment.getExternalStorageDirectory
import java.io.File


class RecorderViewModel : ViewModel() {
    private var recording = MutableLiveData<Boolean>(false)
    private var recorder: MediaRecorder? = null

    fun onRecord() {
        Timber.d("Record button clicked")
        if (recording.value == true) stopRecording()
        else startRecording()
    }

    private fun startRecording() {
        Timber.d("Start recording")
        recording.value = true
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setAudioSamplingRate(128000)
            setAudioEncodingBitRate(96000)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setOutputFile(Environment.getExternalStorageDirectory()
                .absolutePath + "/AudioRecorder/myaudio.mp3" )
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)

            try {
                prepare()
            } catch (e: IOException) {
                Timber.e("prepare() failed: %s", e.message)
            }
            start()
        }

    }

    private fun stopRecording() {
        Timber.d("Stop recording")
        recorder?.apply {
            stop()
            reset()
            release()
        }
        recorder = null

        recording.value = false
    }
}
