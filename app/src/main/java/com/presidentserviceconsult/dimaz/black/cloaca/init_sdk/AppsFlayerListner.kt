package com.presidentserviceconsult.dimaz.black.cloaca.init_sdk


import android.util.Log
import com.appsflyer.AppsFlyerConversionListener


class AppsFlayerListner(
    val initFireBase: (map: Map<String, String>) -> Unit,
    val faceBookInit: () -> Unit,
) : AppsFlyerConversionListener {

    override fun onConversionDataSuccess(data: MutableMap<String, Any>?) {
        Log.d("test_cloaca_step_2", "${data ?: mapOf()}")
        if (!InitSDK.responseReceived) {
            InitSDK.responseReceived = true
            initFireBase((data as Map<String,String>?)?: mapOf("" to ""))
        }
    }

    override fun onConversionDataFail(error: String?) {
        if (!InitSDK.responseReceived) {
            Log.d("test_cloaca_step_2", "")
            InitSDK.responseReceived = true
            faceBookInit()

        }

    }

    override fun onAppOpenAttribution(data: MutableMap<String, String>?) {
        if (!InitSDK.responseReceived) {
            Log.d("test_cloaca_step_2", "")
            InitSDK.responseReceived = true
            faceBookInit()
        }
    }

    override fun onAttributionFailure(error: String?) {
        if (!InitSDK.responseReceived) {
            Log.d("test_cloaca_step_2", "")
            InitSDK.responseReceived = true
            faceBookInit()
        }
    }

}