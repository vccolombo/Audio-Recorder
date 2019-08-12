package com.github.vccolombo.audiorecorder.ui.recorder

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.github.vccolombo.audiorecorder.databinding.RecorderFragmentBinding
import timber.log.Timber

// TODO: Implement audio playback
// TODO: Cloud upload
// TODO: MARKERS
class RecorderFragment : Fragment() {

    private val RECORD_AUDIO_PERMISSION = 200
    private val WRITE_PERMISSION = 300

    companion object {
        fun newInstance() = RecorderFragment()
    }

    private val viewModel by viewModel<RecorderViewModel>()

    private lateinit var binding: RecorderFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RecorderFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED
        ) {
            Timber.d("Record permission not granted")
            requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), RECORD_AUDIO_PERMISSION)
        }

        if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            Timber.d("Write permission not granted")
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), WRITE_PERMISSION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            RECORD_AUDIO_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Timber.d("Record Permission granted")
                } else {
                    Timber.d("Record Permission not granted")
                    // TODO: Handle permission not granted
                    activity?.finish()
                }
            }
            WRITE_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Timber.d("Write Permission granted")
                } else {
                    Timber.d("Write Permission not granted")
                    // TODO: Handle permission not granted
                    activity?.finish()
                }
            }
        }
    }

}
