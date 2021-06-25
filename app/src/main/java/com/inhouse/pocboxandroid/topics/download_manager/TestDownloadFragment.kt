package com.inhouse.pocboxandroid.topics.download_manager

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.inhouse.pocboxandroid.databinding.FragmentTestDownloadBinding
import kotlinx.coroutines.*

const val TAG = "TestDownloadFragment"

// best source of large file url:
// https://ocw.mit.edu/courses/mathematics/18-03sc-differential-equations-fall-2011/unit-i-first-order-differential-equations/geometric-methods/
const val DOWNLOAD_URL =
    "https://ia801900.us.archive.org/26/items/MIT7.016F18/MIT7_016F18_lec01_300k.mp4"
//    "http://ia802305.us.archive.org/7/items/MIT18.03S06/mit-ocw-18.03-lec1-05feb2003-220k.mp4"
//    "https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview125/v4/b7/f7/f1/b7f7f145-c824-33c1-ed57-7e991a20d440/mzaf_15742476855522572910.plus.aac.p.m4a"

class TestDownloadFragment : Fragment() {
    private lateinit var binding: FragmentTestDownloadBinding
    private lateinit var downloadManager: DownloadManager
    private val downloadUri: Uri by lazy {
        Uri.parse(DOWNLOAD_URL)
    }
    private val viewModel: TestDownloadViewModel by lazy {
        ViewModelProvider(this).get(TestDownloadViewModel::class.java)
    }

    private val coroutineExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            coroutineScope.launch(Dispatchers.Main) {
                Log.e(TAG, "show error message")
                Toast.makeText(requireContext(), "Encountered an error!", Toast.LENGTH_SHORT).show()
            }

            GlobalScope.launch { println("Caught $throwable") }
        }
    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job + coroutineExceptionHandler)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTestDownloadBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        downloadManager =
            requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        binding.btnDownload.setOnClickListener { downloadData() }

        binding.btnStop.setOnClickListener {
            downloading = false
            downloadManager.remove(currentDownloadId)
            binding.linearProgressIndicator.progress = 0
            binding.linearProgressIndicator.requestLayout()
        }
    }

    private fun dummyProgress(progressBar: ProgressBar) {
        var progressStatus = 0
        coroutineScope.launch(Dispatchers.IO) {
            while (progressStatus < 100) {
                progressStatus += 1
                withContext(Dispatchers.Main) {
                    progressBar.progress = progressStatus
                }
                Thread.sleep(200)
            }
        }
    }

    private var currentDownloadId: Long = -1
    private fun downloadData() {
        val downloadRequest = DownloadManager.Request(downloadUri)
        downloadRequest.setTitle("Mp4 download")
        downloadRequest.setDescription("A 30 seconds mp4 is to be downloaded from itunes store")
        downloadRequest.setDestinationInExternalFilesDir(
            requireContext(),
            Environment.DIRECTORY_MUSIC,
            "dummy.mp4"
        )

        currentDownloadId = downloadManager.enqueue(downloadRequest)
        Log.d(TAG, "download = $currentDownloadId")
        setupProgress(currentDownloadId)
        requireContext().registerReceiver(downloadBroadcastReceiver, intentFilter)
    }

    var downloading = true
    private fun setupProgress(downloadId: Long) {
        val downloadQuery = DownloadManager.Query().setFilterById(downloadId)

        val progressBar = binding.linearProgressIndicator
        progressBar.apply {
            max = 100
        }
        coroutineScope.launch(Dispatchers.IO) {
            var progressStatus: Int
            while (downloading) {
                val c = downloadManager.query(downloadQuery)
                if (c.moveToFirst()) {
                    val totalSizeIndex: Int =
                        c.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)
                    val totalSize = c.getInt(totalSizeIndex)
                    val downloadedIndex: Int =
                        c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)
                    val downloaded = c.getInt(downloadedIndex)
                    Log.d(TAG, "(downloaded / totalSize) = ($downloaded / $totalSize)")
                    if (totalSize > 0) {
                        progressStatus = (downloaded * 100L / totalSize).toInt()
                        Log.d(TAG, "progressStatus = $progressStatus")
                        withContext(Dispatchers.Main) {
                            binding.tvProgress.text =
                                String.format("%s / %s", progressStatus, 100)
                            progressBar.progress = progressStatus
                        }
                    }
                }
            }
        }
    }

    val intentFilter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
    private val downloadBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            //check if the broadcast message is for our enqueued download
            val referenceId = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            Log.d(TAG, "downloadBroadcastReceiver onReceive $referenceId")
            downloading = false
        }
    }
}