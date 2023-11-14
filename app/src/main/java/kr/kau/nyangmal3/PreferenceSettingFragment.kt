package kr.kau.nyangmal3

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentManager
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager

class PreferenceSettingFragment:PreferenceFragmentCompat {

    constructor() : super()

    lateinit var prefs: SharedPreferences
    var messagePreference: Preference? = null
    var dark_modePreference: Preference? = null
    var soundListPreference: Preference? = null
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting, rootKey)

        prefs = preferenceManager.sharedPreferences!!

        if (rootKey == null) {
            // 객체 초기화
            messagePreference = findPreference("message")
            soundListPreference = findPreference("sound_list")
            dark_modePreference = findPreference("dark_mode")

            //prefs = requireActivity().getSharedPreferences("message", Context.MODE_PRIVATE)

            if (prefs.getString("sound_list", "") != "") {
                // ListPreference의 summary를 sound_list key에 해당하는 값으로 설정
                soundListPreference?.summary = prefs.getString("sound_list", "냥냥")
            }
        }

    }

    val prefListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences: SharedPreferences?, key: String? ->
            // key는 xml에 등록된 key에 해당
            when (key) {
                "message" -> {
                    val value = prefs.getBoolean("message", false)
                    Toast.makeText(activity, "메세지 알림 성공", Toast.LENGTH_SHORT).show()
                }

                "sound_list" -> {
                    val summary = prefs.getString("sound_list", "냥냥")
                    soundListPreference?.summary = summary
                }

                "dark_mode" -> {
                    val value = prefs.getBoolean("dark_mode", false)
                    if (value) {
                        Toast.makeText(activity, "다크모드 성공", Toast.LENGTH_SHORT).show()
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        Log.d("ThemeChange", "Dark mode applied")
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        Toast.makeText(activity, "다크모드 해제", Toast.LENGTH_SHORT).show()
                        Log.d("ThemeChange", "Dark mode  not applied")


                    }
                }
            }
        }

    override fun onResume() {
        super.onResume()
        prefs.registerOnSharedPreferenceChangeListener(prefListener)
    }

    override fun onPause() {
        super.onPause()
        prefs.unregisterOnSharedPreferenceChangeListener(prefListener)
    }

}
