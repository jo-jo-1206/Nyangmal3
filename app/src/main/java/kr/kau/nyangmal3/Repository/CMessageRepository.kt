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
import java.util.TimeZone

class CMessageRepository {
    private val database = Firebase.database
    private val chatRef = database.getReference("chat")
    private val mauth: FirebaseAuth = FirebaseAuth.getInstance()
    private val senderUid: String? = mauth.currentUser?.uid
    private var reciveUid: String? = null


    fun setReciveUid(uid: String) {
        reciveUid = uid
    }
    fun observeMessage(): MutableLiveData<MutableList<CMessageData>> {
        if (reciveUid == null) {
            throw IllegalStateException("ReciveUid is not set. Call setReciveUid(uid) before calling observeMessage()")
        }

        val mutableData = MutableLiveData<MutableList<CMessageData>>()
        senderUid?.let {
            val sendRoom = generateRoomId(it, reciveUid!!)

            chatRef.child(sendRoom)
                .addValueEventListener(object : ValueEventListener {
                    val listData: MutableList<CMessageData> = mutableListOf<CMessageData>()

                    override fun onDataChange(snapshot: DataSnapshot) {
                        listData.clear()
                        if (snapshot.exists()) {
                            for (postSnapshat in snapshot.children) {
                                val getData = postSnapshat.getValue(CMessageData::class.java)
                                if (getData is CMessageData) {
                                    listData.add(getData)
                                } else {

                                }
                                mutableData.value = listData
                            }
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                    }
                })
        }
        return mutableData
    }

    fun addMessage(message: CMessageData) {

        senderUid?.let {
            val sendRoom = generateRoomId(it, reciveUid!!)
            val receiveRoom = generateRoomId(reciveUid!!, it)

            // 파이어베이스에 데이터 넣기
            chatRef.child(sendRoom).push()
                .setValue(message).addOnSuccessListener {
                    chatRef.child(receiveRoom).push()
                        .setValue(message)
                }
        }
    }
    fun updateImage(uri: String) {
        senderUid?.let{
            val sendRoom = generateRoomId(it, reciveUid!!)
            val receiveRoom = generateRoomId(reciveUid!!, it)

            val imageUrl = uri
            val messageMap = mapOf(
                "message" to "", // You might want to provide some default value or leave it empty
                "sendTime" to getTime(),
                "sendId" to senderUid,
                "image" to imageUrl
            )
            chatRef.child(sendRoom).push().setValue(messageMap)
            chatRef.child(receiveRoom).push().setValue(messageMap)
        }
    }

    private fun generateRoomId(uid1: String, uid2: String): String {
        return "$uid1-$uid2"
    }

    fun getTime():String {
        val currentTime = System.currentTimeMillis()
        // 현재 시간을 Data 타입으로 변환
        val timeData = Date(currentTime)

        val timeFormat = SimpleDateFormat("yyyyMMddHHmmss")
        // 시간 맞춰주기
        timeFormat.timeZone = TimeZone.getTimeZone("GMT+09:00")
        return timeFormat.format(timeData).toString()
    }


}