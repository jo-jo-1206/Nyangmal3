package kr.kau.nyangmal3.Repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kr.kau.nyangmal3.CMessageData

private lateinit var sendRoom: String
private lateinit var receiveRoom: String

val database = Firebase.database
val chatRef = database.getReference("chat")
class CMessageRepository {
    fun observeMessage(): MutableLiveData<MutableList<CMessageData>> {
        val mutableData = MutableLiveData<MutableList<CMessageData>>()
        sendRoom = "보낸사람 uid" + " 받는사람 uid"
        receiveRoom = "받는사람 uid" + " 보낸사람 uid"

        chatRef.child(sendRoom).child("message")
            .addValueEventListener(object : ValueEventListener {
                val listData: MutableList<CMessageData> = mutableListOf<CMessageData>()
                override fun onDataChange(snapshot: DataSnapshot) {
                    listData.clear()
                    if (snapshot.exists()) {
                        for (postSnapshat in snapshot.children) {
                            val getData = postSnapshat.getValue(CMessageData::class.java)
                            listData.add(getData!!)
                            mutableData.value = listData
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        return mutableData


    }

    // 데이터베이스에 저장
    fun addMessage(message: CMessageData) {
        chatRef.child(sendRoom).child("message").push()
            .setValue(message).addOnSuccessListener { // 성공하면 받는 쪽에도 같이 저장해둠 - > 메세지 받아야되니까
                chatRef.child(receiveRoom).child("message").push()
                    .setValue(message)
            }

    }
}