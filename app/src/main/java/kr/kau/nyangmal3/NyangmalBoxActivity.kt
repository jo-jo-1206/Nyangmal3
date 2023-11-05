package kr.kau.nyangmal3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.kau.nyangmal3.databinding.ActivityNyangmalBoxBinding

//냥말박스: 익명편지함 냥말오면 쌓이고? 읽고 삭제 가능, 냥말보내기는 친구리스트에서 구현?(팝업), 50자 정도.
//메모장 비슷하게??? db는 나중에 한다치고, 채팅이랑 비슷한가???
class NyangmalBoxActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNyangmalBoxBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNyangmalBoxBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //backib눌렀을때 다시 홈액티비티로 전환됨.
        binding.backIb.setOnClickListener {
            val nextintent = Intent(this, HomeActivity::class.java)
            startActivity(nextintent)
        }
    }
}