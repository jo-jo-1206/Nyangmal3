package kr.kau.nyangmal3

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
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
import kr.kau.nyangmal3.Repository.CMessageRepository
import kr.kau.nyangmal3.ViewModel.CMessageViewModel
import kr.kau.nyangmal3.databinding.ActivityChatBinding
// *** var ,val 구별하기
private val mauth: FirebaseAuth = FirebaseAuth.getInstance()

class ChatActivity : AppCompatActivity() {
    lateinit var binding: ActivityChatBinding

    private lateinit var adapter: CMessageAdapter
    private val viewModel: CMessageViewModel by viewModels()

    private val CHANNEL_ID = "testChannel01"   // Channel for notification
    private var notificationManager: NotificationManager? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 알림 채널 만들기
        createNotificationChannel(CHANNEL_ID, "testChannel", "this is a test Channel")

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
            val messageData = CMessageData(messageText,currentTime,senderUid)
            viewModel.addMessage(messageData)
            // 메세지 전송 후 텍스트칸 초기화
            binding.edtMessage.setText("")
            displayNotification("Nyanmal",messageText)
        }

        // 뒤로가기 버튼
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun displayNotification(title:String,message:String) {
        val notificationId = 45

        var notification = NotificationCompat.Builder(this,CHANNEL_ID)
            .setSmallIcon(R.drawable.submit_btn)
            .setContentTitle(title)
            .setContentText(message)

        notificationManager?.notify(notificationId, notification.build())
    }

    private fun createNotificationChannel(channelId: String, name: String, channelDescription: String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = channelDescription
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
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
}