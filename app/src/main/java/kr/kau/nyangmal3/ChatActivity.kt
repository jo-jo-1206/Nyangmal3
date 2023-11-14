package kr.kau.nyangmal3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kr.kau.nyangmal3.ViewModel.CMessageViewModel
import kr.kau.nyangmal3.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    lateinit var binding: ActivityChatBinding

    private lateinit var adapter: CMessageAdapter
    val viewModel: CMessageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CMessageAdapter(this)
        binding.recyclerMessages.layoutManager = LinearLayoutManager(this)
        binding.recyclerMessages.adapter = adapter
        observerData()

        // 전송 버튼을 눌렀을 때
        binding.btnSubmit.setOnClickListener {
            val messageText = binding.edtMessage.text.toString()
            val currentTime = viewModel.getTime()
            val messageData = CMessageData(messageText,currentTime)
            viewModel.addMessage(messageData)
            // 메세지 전송 후 텍스트칸 초기화
            binding.edtMessage.setText("")
            Log.d("chat", "chattinf test")

        }

        binding.button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    fun observerData() {
        viewModel.fetchData().observe(this, Observer {
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
            binding.recyclerMessages.scrollToPosition(adapter.itemCount - 1)
        })
    }

}