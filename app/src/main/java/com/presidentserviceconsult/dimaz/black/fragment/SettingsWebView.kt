package com.presidentserviceconsult.dimaz.black.fragment

import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.webkit.URLUtil
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.presidentserviceconsult.dimaz.black.shared.SharedPrefs

class SettingsWebView(val context:Activity,val goWhite:()->Unit) {

    companion object {
        const val REQUEST_CODE_STORAGE_PERMISSION = 100
    }
    inner class MyWeb : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            if (!(url?.startsWith("https://") ?: false)) {
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(
                        context,
                        "there is no app on your phone",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                return true;
            } else {
                SharedPrefs(context.applicationContext).setUrl(url ?: "")
                return false;
            }


            return super.shouldOverrideUrlLoading(view, url)

        }

        override fun onReceivedHttpError(
            view: WebView?,
            request: WebResourceRequest?,
            errorResponse: WebResourceResponse?
        ) {
            if (errorResponse?.statusCode == 404) {
                goWhite()
            }
        }


    }

    fun checkStoragePermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    fun requestStoragePermission() {
        ActivityCompat.requestPermissions(
            context,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_CODE_STORAGE_PERMISSION
        )
    }

     fun downloadFile(url: String, contentDisposition: String?, mimeType: String?) {
         val request = DownloadManager.Request(Uri.parse(url))

         request.setMimeType(mimeType)

         contentDisposition?.let { disposition ->
             request.addRequestHeader("Content-Disposition", disposition)
         }

         val fileName = URLUtil.guessFileName(url, contentDisposition, mimeType)
         val downloadDirectory =
             Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

         if (!downloadDirectory.exists()) {
             downloadDirectory.mkdirs()
         }

         val destinationUri = Uri.withAppendedPath(
             Uri.fromFile(downloadDirectory),
             fileName
         )

         request.setDestinationUri(destinationUri)
         val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

         downloadManager.enqueue(request)

     }


    val webChromeClient = object : WebChromeClient() {
        private val FILE_CHOOSER_RESULT_CODE = 1



        override fun onShowFileChooser(
            webView: WebView,
            filePathCallback: ValueCallback<Array<Uri>>,
            fileChooserParams: FileChooserParams
        ): Boolean {

            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*" // You can change the file type as needed
            context.startActivityForResult(
                Intent.createChooser(intent, "File Chooser"),
                FILE_CHOOSER_RESULT_CODE
            )
            return true
        }
    }


}


