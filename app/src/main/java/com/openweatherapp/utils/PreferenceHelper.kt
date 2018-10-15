package com.openweatherapp.utils

import android.content.Context
import android.preference.PreferenceManager
import com.gitprofile.utils.Constants

/**
 * Created by vishal on 10/14/2018.
 * Manage Preference for application
 */
class PreferencesHelper(context: Context) {

    companion object {
        private val UNIT = Constants.APP_PREF.unit
    }

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    // Save Unit for api.
    var unit = preferences.getString(UNIT, Constants.APP_PREF.metric)
        set(value) = preferences.edit().putString(UNIT, value).apply()
}