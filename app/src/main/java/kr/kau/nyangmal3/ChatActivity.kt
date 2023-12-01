package kr.kau.nyangmal3

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kr.kau.nyangmal3.Repository.CMessageRepository
import kr.kau.nyangmal3.ViewModel.CMessageViewModel
import kr.kau.nyangmal3.databinding.ActivityChatBinding
// *** var ,val 구별하기
private val mauth: FirebaseAuth = FirebaseAuth.getInstance()

class ChatActivity : AppCompatActivity() {
    lateinit var binding: ActivityChatBinding

    private lateinit var adapter: CMessageAdapter
    private val viewModel: CMessageViewModel by viewModels()
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            viewModel.updateImage(it)
        } ?: Log.d("iamge","선택안함")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 어댑터
        adapter = CMessageAdapter(this)
        binding.recyclerMessages.layoutManager = LinearLayoutManager(this)
        binding.recyclerMessages.adapter = adapter

        val senderUid: String? = mauth.currentUser?.uid
        var reciveUid = intent.getStringExtra("uid").toString()
        viewModel.setReciveUid(reciveUid)
        observerData()

        // 전송 버튼을 눌렀을 때
        binding.btnSubmit.setOnClickListener {
            val messageText = binding.edtMessage.text.toString()
            val currentTime = viewModel.getTime()
            val messageData = CMessageData(messageText,currentTime,senderUid,"")
            if(messageText!=""){
                viewModel.addMessage(messageData)
            }
            // 메세지 전송 후 텍스트칸 초기화
            binding.edtMessage.setText("")
        }

        // 뒤로가기 버튼
        binding.btnBack.setOnClickListener {
            finish();
        }

        binding.btnSendImg.setOnClickListener {
            sendPicture()
        }
    }
    // 뷰 모델에서 채팅데이터 가져와서 어댑터에 보여줘라
    fun observerData() {
        viewModel.fetchData().observe(this, Observer {newData ->
            adapter.setListData(newData)
            binding.recyclerMessages.scrollToPosition(adapter.itemCount - 1)
            adapter.notifyDataSetChanged()
        })
    }

    fun sendPicture(){
        getContent.launch("image/*")
    }
}