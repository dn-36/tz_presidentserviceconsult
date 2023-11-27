package com.presidentserviceconsult.dimaz.black.shared

import android.content.Context

class SharedPrefs(context: Context) {

    private val pref = context.getSharedPreferences("databaseInfo", Context.MODE_PRIVATE)

    companion object {
        const val STATUS_UNKNOWN = "UNKNOWN"
        const val STATUS_GO_TO_WHITE = "OPEN_GAME"
        const val STATUS_GO_TO_BLACK = "OPEN_URL"

        const val STATUS = "STATUS"
        const val URL = "URL"

    }


    fun setStatus(status: String) {
        pref.edit().putString(STATUS, status).apply()
    }

    fun getStatus(): String {
        return pref.getString(STATUS, STATUS_UNKNOWN) ?: STATUS_UNKNOWN
    }

    fun setUrl(url: String) {
        pref.edit().putString(URL, url).apply()
    }

    fun getUrl(): String {
        return pref.getString(URL, "")?:""
    }
}

