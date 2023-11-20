package kr.kau.nyangmal3.Repository

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.selects.select
import kr.kau.nyangmal3.CMessageData
import java.text.SimpleDateFormat
import java.util.Date
private lateinit var reciverUid: String
class CMessageRepository {

    private val database = Firebase.database
    private val chatRef = database.getReference("chat")
    private val mauth: FirebaseAuth = FirebaseAuth.getInstance()
    private val senderUid: String? = mauth.currentUser?.uid
    fun observeMessage(): MutableLiveData<MutableList<CMessageData>> {
        val mutableData = MutableLiveData<MutableList<CMessageData>>()

        senderUid?.let {
            val sendRoom = it + "받는사람 uid"
            chatRef.child(sendRoom)
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
        }
        return mutableData
    }

    // 데이터베이스에 저장
    fun addMessage(message: CMessageData) {
        senderUid?.let {
            val sendRoom = it + "받는사람 uid"
            val receiveRoom = "받는사람 uid" + it

            chatRef.child(sendRoom).push()
                .setValue(message).addOnSuccessListener {
                    chatRef.child(receiveRoom).push()
                        .setValue(message)
                }
        }
    }
}