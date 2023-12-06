package kr.kau.nyangmal3.Repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kr.kau.nyangmal3.CMessageData
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class CMessageRepository {
    private val database = Firebase.database
    private val chatRef = database.getReference("chat")
    private val mauth: FirebaseAuth = FirebaseAuth.getInstance()
    private val senderUid: String = mauth.currentUser!!.uid
    private var reciveUid: String = ""

    // receiveUid를 받아옴
    fun setReciveUid(uid: String) {
        reciveUid = uid
    }

    fun observeMessage(): MutableLiveData<MutableList<CMessageData>> {
        val mutableData = MutableLiveData<MutableList<CMessageData>>()

        // 대화방 ID
        val sendRoom = generateRoomId(senderUid, reciveUid)

        chatRef.child(sendRoom)
            .addValueEventListener(object : ValueEventListener {
                val listData: MutableList<CMessageData> = mutableListOf()

                override fun onDataChange(snapshot: DataSnapshot) {
                    listData.clear()
                    for (postSnapshat in snapshot.children) {
                        val getData = postSnapshat.getValue(CMessageData::class.java)
                        getData?.let {
                            listData.add(it)
                        }
                        mutableData.postValue(listData)
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        return mutableData
    }

    // 파이어베이스에 데이터 추가하기
    fun addMessage(message: CMessageData) {

        // 대화방 ID
        val sendRoom = generateRoomId(senderUid, reciveUid)
        val receiveRoom = generateRoomId(reciveUid, senderUid)

        chatRef.child(sendRoom).push()
            .setValue(message).addOnSuccessListener {
                chatRef.child(receiveRoom).push()
                    .setValue(message)
            }
    }

    fun updateImage(uri: String) {
        val sendRoom = generateRoomId(senderUid, reciveUid!!)
        val receiveRoom = generateRoomId(reciveUid!!, senderUid)
        val imageUrl = uri
        val messageMap = mapOf(
            "message" to "",
            "sendTime" to getTime(),
            "sendId" to senderUid,
            "image" to imageUrl
        )
        chatRef.child(sendRoom).push().setValue(messageMap)
        chatRef.child(receiveRoom).push().setValue(messageMap)

    }

    private fun generateRoomId(uid1: String, uid2: String): String {
        return "$uid1-$uid2"
    }

    // 시간 생성하는 함수
    fun getTime():String {
        val currentTime = System.currentTimeMillis()
        val timeData = Date(currentTime)
        val timeFormat = SimpleDateFormat("a hh:mm", Locale.KOREA)
        timeFormat.timeZone = TimeZone.getTimeZone("GMT+09:00")
        return timeFormat.format(timeData).toString()
    }
}