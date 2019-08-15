package com.github.vccolombo.audiorecorder.ui.recorder

import android.media.MediaRecorder
import android.os.CountDownTimer
import android.os.Environment
import android.widget.Chronometer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.vccolombo.audiorecorder.utils.PATH_RECORDINGS_DIRECTORY
import kotlinx.coroutines.*
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.util.*
import kotlin.coroutines.CoroutineContext


class RecorderViewModel : ViewModel() {

    private val job = Job()
    private val coroutineContext: CoroutineContext = Dispatchers.Main + job
    private val scope = CoroutineScope(coroutineContext)

    var recording: MutableLiveData<Boolean> = MutableLiveData(false)
    var started: MutableLiveData<Boolean> = MutableLiveData(false)
    var paused: MutableLiveData<Boolean> = MutableLiveData(false)

    private var recorder: MediaRecorder? = null

    // TODO: check what happens when there is no space to save
    fun startRecording() {
        // Create directory to save the audios if not created before
        // TODO: Make a better directory creation?
        val dir = File(PATH_RECORDINGS_DIRECTORY)
        if (!dir.exists()) dir.mkdirs()

        Timber.d("Start recording")
        recording.value = true
        started.value = true
        paused.value = false

        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setAudioSamplingRate(44100)
            setAudioEncodingBitRate(96000)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setOutputFile(PATH_RECORDINGS_DIRECTORY + Calendar.getInstance().time + ".mp3")
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)

            try {
                prepare()
            } catch (e: IOException) {
                Timber.e("prepare() failed: %s", e.message)
            }
            start()
        }

        // TODO: Actually display it
        // Audio amplitude for display
        scope.launch {
            repeat(1_000_000_000) {
                delay(300L)
                val amplitude = recorder?.maxAmplitude
            }
        }

    }

    fun pauseRecording() {
        Timber.d("Pause recording")
        paused.value = true
        recording.value = false

        recorder?.pause()
    }

    fun continueRecording() {
        Timber.d("Continue recording")
        recording.value = true
        paused.value = false

        recorder?.resume()
    }

    // TODO: Change file name when finishing recording
    // TODO: Check if killing the app while recording is safe (might be because of onCleared() is implemented)
    fun stopRecording() {
        Timber.d("Stop recording")

        // Stop audio amplitude coroutine
        scope.coroutineContext.cancelChildren()

        recorder?.apply {
            stop()
            reset()
            release()
        }
        recorder = null

        recording.value = false
        paused.value = false
        started.value = false
    }

    override fun onCleared() {
        job.cancel()

        recorder?.apply {
            stop()
            reset()
            release()
        }
        recorder = null

        super.onCleared()
    }
}
