package com.wardwar.mybisnisindoneisa.utils

import android.content.Context
import android.preference.PreferenceManager

class PreferencesHelper(val context: Context) {
    companion object {
        val DEVELOP_MODE = false
        private val DEVICE_TOKEN = "data.source.prefs.DEVICE_TOKEN"
        private val DEVICE_EMAIL = "data.source.prefs.DEVICE_EMAIL"
        private val DEVICE_NAME = "data.source.prefs.DEVICE_NAME"
    }

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    var deviceToken = preferences.getString(DEVICE_TOKEN, "")
        set(value) = preferences.edit().putString(DEVICE_TOKEN, value).apply()

    var deviceEmail = preferences.getString(DEVICE_EMAIL, "")
        set(value) = preferences.edit().putString(DEVICE_EMAIL, value).apply()

    var deviceName = preferences.getString(DEVICE_NAME, "")
        set(value) = preferences.edit().putString(DEVICE_NAME, value).apply()
}