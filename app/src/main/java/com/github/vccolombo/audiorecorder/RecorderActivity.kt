package com.github.vccolombo.audiorecorder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.vccolombo.audiorecorder.ui.recorder.RecorderFragment

class RecorderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recorder_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, RecorderFragment.newInstance())
                .commitNow()
        }
    }

}
