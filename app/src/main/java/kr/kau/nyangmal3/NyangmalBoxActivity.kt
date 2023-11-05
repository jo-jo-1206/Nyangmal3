package kr.kau.nyangmal3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

//냥말박스: 익명편지함 냥말오면 쌓이고? 읽고 삭제 가능, 냥말보내기는 친구리스트에서 구현?(팝업), 50자 정도.
//메모장 비슷하게??? db는 나중에 한다치고, 채팅이랑 비슷한가???
class NyangmalBoxActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nyangmal_box)
    }
}