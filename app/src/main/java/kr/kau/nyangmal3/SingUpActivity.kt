package kr.kau.nyangmal3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kr.kau.nyangmal3.databinding.ActivitySingUpBinding

class SingUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingUpBinding

    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCancel.setOnClickListener {
            val intent: Intent = Intent(this@SingUpActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        // 인증 초기화
        auth = Firebase.auth

        binding.btnSignUp.setOnClickListener {
            val email = binding.edtTxtEmail.text.toString().trim()
            val password = binding.edtTxtPassword.text.toString().trim()

            signUp(email, password)
        }
    }


    /*
    회원가입
     */
    private fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) { // 성공
                    Toast.makeText(this, "회원가입 완료", Toast.LENGTH_SHORT).show()

                    val intent: Intent = Intent(this@SingUpActivity, HomeActivity::class.java)
                    startActivity(intent)

                    finish() // 액티비티 종료
                } else { // 실패
                    Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
                }
            }
    }
}