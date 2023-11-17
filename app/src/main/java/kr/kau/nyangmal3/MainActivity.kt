package kr.kau.nyangmal3

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import kr.kau.nyangmal3.databinding.ActivityMainBinding

var gCanUseCamera: Boolean = false

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            // 카메라가 사용 가능함을 전역변수로 알리기?
            binding.txtCanUseCamera.setText("! 카메라 사용 가능 !")
            gCanUseCamera = true
        } else {
            binding.txtCanUseCamera.setText("...카메라 사용 불가능...")
            gCanUseCamera = false
        }

        // 홈버튼 클릭 시 홈으로 이동
        binding.btnHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        // 챗버튼 클릭 시 챗으로 이동
        binding.btnChat.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }
    }
}