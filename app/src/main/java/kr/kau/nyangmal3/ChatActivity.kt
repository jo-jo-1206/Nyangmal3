package kr.kau.nyangmal3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import kr.kau.nyangmal3.ViewModel.CMessageViewModel
import kr.kau.nyangmal3.databinding.ActivityChatBinding

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

        adapter = CMessageAdapter(this)
        binding.recyclerMessages.layoutManager = LinearLayoutManager(this)
        binding.recyclerMessages.adapter = adapter

        val senderUid: String = mauth.currentUser!!.uid
        var reciveUid = intent.getStringExtra("uid").toString()
        viewModel.setReciveUid(reciveUid)
        observerData()

        /* 메세지 전송 버튼 */
        binding.btnSubmit.setOnClickListener {
            val messageText = binding.edtMessage.text.toString()
            val currentTime = viewModel.getTime()
            val messageData = CMessageData(messageText,currentTime,senderUid!!,"")
            if(messageText.isNotEmpty()){
                viewModel.addMessage(messageData)
            }
            // 메세지 전송 후 텍스트칸 초기화
            binding.edtMessage.setText("")
        }

        /* 뒤로가기 버튼 */
        binding.btnBack.setOnClickListener {
            finish();
        }

        /* 이미지 보내기 버튼 */
        binding.btnSendImg.setOnClickListener {
            sendPicture()
        }
    }

    fun observerData() {
        viewModel.fetchData().observe(this, Observer {newData ->
            adapter.setListData(newData)
            binding.recyclerMessages.scrollToPosition(adapter.itemCount - 1)
        })
    }

    fun sendPicture(){
        getContent.launch("image/*")
    }
}