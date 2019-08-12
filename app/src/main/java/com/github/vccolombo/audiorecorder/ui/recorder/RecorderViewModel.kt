package com.github.vccolombo.audiorecorder.ui.recorder

import android.media.MediaRecorder
import android.os.Environment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import timber.log.Timber
import java.io.IOException
import kotlin.coroutines.CoroutineContext


class RecorderViewModel : ViewModel() {

    private val job = Job()
    private val coroutineContext: CoroutineContext = Dispatchers.Default + job
    private val scope = CoroutineScope(coroutineContext)

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
            setAudioSamplingRate(44100)
            setAudioEncodingBitRate(96000)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setOutputFile(Environment.getExternalStorageDirectory()
                .absolutePath + "/AudioRecorder/myaudio.mp3" ) // TODO: Change file name
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)

            try {
                prepare()
            } catch (e: IOException) {
                Timber.e("prepare() failed: %s", e.message)
            }
            start()
        }

        // Audio amplitude for display
        scope.launch {
            repeat(1_000_000_000) {
                delay(100L)
                val amplitude = recorder?.maxAmplitude
                Timber.d("""Repeat $it: $amplitude""")
            }
        }

    }

    private fun pauseRecording() {
        TODO()
    }

    // TODO: Change file name when finishing recording
    // TODO: Check if killing the app while recording is safe
    private fun stopRecording() {
        Timber.d("Stop recording")

        // Stop audio amplitude coroutine
        job.cancel()

        recorder?.apply {
            stop()
            reset()
            release()
        }
        recorder = null

        recording.value = false
    }
}
