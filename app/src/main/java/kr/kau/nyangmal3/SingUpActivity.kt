package kr.kau.nyangmal3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kr.kau.nyangmal3.databinding.ActivitySingUpBinding

class SingUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingUpBinding

    lateinit var auth: FirebaseAuth

    private lateinit var DbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCancel.setOnClickListener {
            val intent: Intent = Intent(this@SingUpActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        auth = Firebase.auth // 인증 초기화

        DbRef= Firebase.database.reference // db 초기화

        binding.btnSignUp.setOnClickListener {
            val name = binding.edtTxtName.text.toString().trim()
            val email = binding.edtTxtEmail.text.toString().trim()
            val password = binding.edtTxtPassword.text.toString().trim()
            val passwordCheck = binding.edtTxtPasswordConfirm.text.toString().trim()

            if (password.equals(passwordCheck)) {
                signUp(name, email, password)
            } else {
                Toast.makeText(this, "비밀번호가 맞지 않습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }


    /*
    회원가입
     */
    private fun signUp(name: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) { // 성공
                    Toast.makeText(this, "회원가입 완료", Toast.LENGTH_SHORT).show()

                    val intent: Intent = Intent(this@SingUpActivity, HomeActivity::class.java)
                    startActivity(intent)

                    addUserToDatabase(name, email, auth.currentUser?.uid!!)

                    finish() // 액티비티 종료
                } else { // 실패
                    Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, uID: String) {
        DbRef.child("user").child(uID).setValue(User(name, email, uID));
    }
}