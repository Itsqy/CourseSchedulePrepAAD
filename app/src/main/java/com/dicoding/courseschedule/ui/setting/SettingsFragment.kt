package com.dicoding.courseschedule.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import com.dicoding.courseschedule.util.NightMode
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder
import java.util.Locale

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : Update theme based on value in ListPreference
        val themePreference = findPreference<ListPreference>(getString(R.string.pref_key_dark))
        val reminderPreference =findPreference<SwitchPreference>(getString(R.string.pref_key_notify))

        themePreference?.setOnPreferenceChangeListener { _, newValue ->
            updateTheme(NightMode.valueOf(newValue.toString().toUpperCase(Locale.US)).value)
            true
        }
        // TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference

        reminderPreference?.setOnPreferenceChangeListener { _, newValue ->
            val reminderEnabled = newValue as Boolean
            val context = requireContext()
            val reminder = DailyReminder()
            if (reminderEnabled) {
               reminder.setDailyReminder(context)
            } else {
               reminder.cancelAlarm(context)
            }

            true
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}