package com.presidentserviceconsult.dimaz.white

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.presidentserviceconsult.dimaz.black.fragment.SettingsWebView
import com.presidentserviceconsult.dimaz.databinding.WhiteFragmentBinding


class WhiteFragment : Fragment() {

val binding by lazy {
    WhiteFragmentBinding.inflate(layoutInflater)
}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("test_cloaca_5","status apps: WHITE")

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun checkStoragePermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ContextCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

     fun requestStoragePermission() {
        ActivityCompat.requestPermissions(
            this.requireActivity(),
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            SettingsWebView.REQUEST_CODE_STORAGE_PERMISSION
        )
    }

     fun downloadFile(url: String, contentDisposition: String?, mimeType: String?) {
        // Implement file download logic here
        // You can use DownloadManager or any other method to download the file
    }




}