package kr.kau.nyangmal3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kr.kau.nyangmal3.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    lateinit var binding: ActivityChatBinding

    // 데이터 베이스 객체 만들기
    val database = Firebase.database
    // 파이어 베이스의 키 값이 chat인 위치로 들어가기
    val chatRef = database.getReference("chat")

    private lateinit var sendRoom: String
    private lateinit var receiveRoom:String

    private lateinit var messgeList: ArrayList<CMessageData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 나중에 로그인 만들면 다시 적기
        sendRoom = "보낸사람 uid" + " 받는사람 uid"
        receiveRoom = "받는사람 uid" + " 보낸사람 uid"

        messgeList = ArrayList()
        val messageAdapter:CMessageAdapter = CMessageAdapter(this,messgeList)

        binding.recyclerMessages.layoutManager = LinearLayoutManager(this)
        binding.recyclerMessages.adapter = messageAdapter

        // 전송 버튼을 눌렀을 때
        binding.btnSubmit.setOnClickListener {

            // 입력한 메세지를 메세지 변수로 넣음
            val message = binding.editText.text.toString()
            val messageOb = CMessageData(message)

            // 데이터 저장
            chatRef.child(sendRoom).child("message").push()
                .setValue(messageOb).addOnSuccessListener { // 성공하면 받는 쪽에도 같이 저장해둠 -> 메세지 받아야되니까
                    chatRef.child(receiveRoom).child("message").push()
                        .setValue(messageOb)
                }
            binding.editText.setText("")
        }

        // 파이어베이스에서 데이터 가져오기
        chatRef.child("sendRoom").child("message")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messgeList.clear()
                    for(postSnapshat in snapshot.children){
                        val message = postSnapshat.getValue(CMessageData::class.java)
                        messgeList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }
}