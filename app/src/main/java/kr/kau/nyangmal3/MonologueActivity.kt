package kr.kau.nyangmal3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.kau.nyangmal3.databinding.ActivityMonologueBinding

class MonologueActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMonologueBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMonologueBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}