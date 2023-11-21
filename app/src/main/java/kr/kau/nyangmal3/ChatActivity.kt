package kr.kau.nyangmal3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kr.kau.nyangmal3.ViewModel.CMessageViewModel
import kr.kau.nyangmal3.databinding.ActivityChatBinding
// *** var ,val 구별하기

private lateinit var reciverUid: String
private val mauth: FirebaseAuth = FirebaseAuth.getInstance()

class ChatActivity : AppCompatActivity() {
    lateinit var binding: ActivityChatBinding

    private lateinit var adapter: CMessageAdapter
    val viewModel: CMessageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 어댑터
        adapter = CMessageAdapter(this)
        binding.recyclerMessages.layoutManager = LinearLayoutManager(this)
        binding.recyclerMessages.adapter = adapter
        observerData()

        // recevierName = intent.getStringExtra("friend_name").toString() 친구이름 띄어줄 필요 없으니까
        // 프렌즈어댑터에서 친구의 uid를 받아와야함 그래야 채팅어댑터에서 보여줄 화면을 결정할 수 있음
        reciverUid = intent.getStringExtra("uid").toString()

        val senderUid: String? = mauth.currentUser?.uid

        // 전송 버튼을 눌렀을 때
        binding.btnSubmit.setOnClickListener {
            val messageText = binding.edtMessage.text.toString()
            val currentTime = viewModel.getTime()
            val messageData = CMessageData(messageText,currentTime,senderUid)
            viewModel.addMessage(messageData)
            // 메세지 전송 후 텍스트칸 초기화
            binding.edtMessage.setText("")
        }

        // 뒤로가기 버튼
        binding.backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    // 뷰 모델에서 채팅데이터 가져와서 어댑터에 보여줘라
    fun observerData() {
        viewModel.fetchData().observe(this, Observer {
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
            // 채팅 칠 때 내가 친 채팅이 제일 아래로 가도록
            binding.recyclerMessages.scrollToPosition(adapter.itemCount - 1)
        })
    }

}