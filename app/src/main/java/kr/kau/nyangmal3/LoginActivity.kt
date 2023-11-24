package kr.kau.nyangmal3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kr.kau.nyangmal3.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        // 다크모드 설정값을 앱이 시작할때 계속 유지되도록 함
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val isDarkModeEnabled = prefs.getBoolean("dark_mode", false)
        if (isDarkModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edtTxtEmail.text.toString()
            val password = binding.edtTxtPassword.text.toString()

            login(email, password)
        }

        binding.btnSignUp.setOnClickListener {
            val intent: Intent = Intent(this@LoginActivity, SingUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) { // 로그인 성공
                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                    
                    val intent: Intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)

                    finish() // 액티비티 종료
                } else { // 로그인 실패
                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                    Log.d("Login", "Error: ${task.exception}")
                }
            }
    }

}