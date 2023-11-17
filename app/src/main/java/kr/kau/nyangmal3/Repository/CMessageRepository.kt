package kr.kau.nyangmal3.Repository

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import kotlinx.coroutines.selects.select
import kr.kau.nyangmal3.CMessageData
import java.text.SimpleDateFormat
import java.util.Date

private lateinit var sendRoom: String
private lateinit var receiveRoom: String
private lateinit var store:FirebaseStorage

val database = Firebase.database
val chatRef = database.getReference("chat")
var selectImage: Uri?=null

class CMessageRepository {
    fun observeMessage(): MutableLiveData<MutableList<CMessageData>> {
        val mutableData = MutableLiveData<MutableList<CMessageData>>()
        sendRoom = "보낸사람 uid" + " 받는사람 uid"
        receiveRoom = "받는사람 uid" + " 보낸사람 uid"

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
        return mutableData


    }

    // 데이터베이스에 저장
    fun addMessage(message: CMessageData) {
        chatRef.child(sendRoom).push()
            .setValue(message).addOnSuccessListener {
                chatRef.child(receiveRoom).push()
                    .setValue(message)
            }
//        chatRef.child(sendRoom).child("message").push()
//            .setValue(message).addOnSuccessListener { // 성공하면 받는 쪽에도 같이 저장해둠 - > 메세지 받아야되니까
//                chatRef.child(receiveRoom).child("message").push()
//                    .setValue(message)
//            }
    }

    // 파이어베이스 스토리지에 사진 저장하기
//    fun addImage(){
//        store = FirebaseStorage.getInstance()
//        store.getReference("chat").child("Image")
//            .putFile(selectImage!!).addOnSuccessListener {
//                Log.d("uploadMessage","메세지 업로드 성공")
//            }
//    }

}