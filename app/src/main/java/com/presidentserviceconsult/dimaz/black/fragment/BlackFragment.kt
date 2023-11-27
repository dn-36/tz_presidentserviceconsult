package com.presidentserviceconsult.dimaz.black.fragment


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.presidentserviceconsult.dimaz.R
import com.presidentserviceconsult.dimaz.activity.MainActivity
import com.presidentserviceconsult.dimaz.black.shared.SharedPrefs
import com.presidentserviceconsult.dimaz.databinding.BlackFragmentBinding


class BlackFragment : Fragment() {
    val binding by lazy { BlackFragmentBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val webviewSettings = SettingsWebView(this.requireActivity(),
            { findNavController().navigate(R.id.action_loading_to_white) })

        Handler(Looper.getMainLooper()).postDelayed({
            binding.zug.isVisible = false

        }, 1500L)


        (requireContext() as MainActivity).goBack = {}

        val client = WebChromeClient()
        binding.vebview.setWebChromeClient(client)
        binding.vebview.setDownloadListener { url, userAgent, contentDisposition, mimeType, contentLength ->
            if (webviewSettings.checkStoragePermission()) {
                webviewSettings.downloadFile(url, contentDisposition, mimeType)
            } else {
                webviewSettings.requestStoragePermission()
            }
        }
        val argFinalUrl = arguments?.getString("final_url")
        if (SharedPrefs(requireContext().applicationContext).getUrl().isNotBlank()) {
            SharedPrefs(requireContext().applicationContext).setStatus(SharedPrefs.STATUS_GO_TO_BLACK)
            SharedPrefs(requireContext().applicationContext).setUrl(argFinalUrl ?: "")
        }
        with(binding.vebview) {
            (requireContext() as MainActivity).goBack = { goBack() }
            settings.loadsImagesAutomatically = true
            webViewClient = webviewSettings.MyWeb()
             webChromeClient = webviewSettings.webChromeClient
            settings.allowFileAccess = true
            settings.mixedContentMode = 0
            settings.javaScriptEnabled = true
            settings.setDomStorageEnabled(true);
            settings.setAllowContentAccess(true);
            settings.setAllowFileAccess(true);
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.domStorageEnabled = true
            loadUrl(argFinalUrl ?: "")
        }
        Log.d("test_cloaca_5", "status apps: BLACK\n final url:${argFinalUrl}")


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SettingsWebView.REQUEST_CODE_STORAGE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }

}