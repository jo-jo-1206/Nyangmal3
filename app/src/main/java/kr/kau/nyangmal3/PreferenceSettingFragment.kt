package kr.kau.nyangmal3

import android.content.Context
import android.content.Intent
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PreferenceSettingFragment:PreferenceFragmentCompat {
    lateinit var auth: FirebaseAuth

    private lateinit var DbRef: DatabaseReference

    constructor() : super()
    private val mauth: FirebaseAuth = FirebaseAuth.getInstance()
    val currentUser: String? = mauth.currentUser?.uid

    lateinit var prefs: SharedPreferences
    var messagePreference: Preference? = null
    var dark_modePreference: Preference? = null
    var soundListPreference: Preference? = null
    var logoutPreference: Preference? = null
    var accountPreference: Preference? = null
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting, rootKey)
        auth = Firebase.auth
        prefs = preferenceManager.sharedPreferences!!




        if (rootKey == null) {
            // 객체 초기화
            messagePreference = findPreference("message")
            soundListPreference = findPreference("sound_list")
            dark_modePreference = findPreference("dark_mode")
            logoutPreference = findPreference("logout")
            accountPreference = findPreference("account")

            //prefs = requireActivity().getSharedPreferences("message", Context.MODE_PRIVATE)

            if (prefs.getString("sound_list", "") != "") {
                // ListPreference의 summary를 sound_list key에 해당하는 값으로 설정
                soundListPreference?.summary = prefs.getString("sound_list", "냥냥")
            }

            // 로그아웃 기능
            logoutPreference?.setOnPreferenceClickListener {
                auth.signOut()
                Toast.makeText(activity, "로그아웃됨", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(),LoginActivity::class.java)
                startActivity(intent)
                true
            }
            DbRef= Firebase.database.reference // db 초기화

            // 내 계정
            DbRef.child("user").child(currentUser!!).child("email")
                .addValueEventListener(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        accountPreference?.summary = snapshot.value.toString()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
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
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        Toast.makeText(activity, "다크모드 성공", Toast.LENGTH_SHORT).show()

                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        Toast.makeText(activity, "다크모드 해제", Toast.LENGTH_SHORT).show()

                    }
                    prefs.edit().putBoolean("dark_mode", value).apply()
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
