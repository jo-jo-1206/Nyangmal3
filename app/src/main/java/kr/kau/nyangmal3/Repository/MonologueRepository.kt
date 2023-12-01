package kr.kau.nyangmal3.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kr.kau.nyangmal3.MonologueMessage

class MonologueRepository {
    private val dbRef: DatabaseReference = Firebase.database.reference
    private val monologueRef = dbRef.child("monologue")

    private val auth = Firebase.auth

    fun addMessage(message: MonologueMessage) {
        val currentUserId = auth.currentUser?.uid ?: return
        val key = monologueRef.child(currentUserId).push().key
        key?.let {
            monologueRef.child(currentUserId).child(it).setValue(message)
        }
    }

    fun getMessagesFromFirebase(): LiveData<List<MonologueMessage>> {
        val liveData = MutableLiveData<List<MonologueMessage>>()
        val currentUserId = auth.currentUser?.uid ?: return liveData

        monologueRef.child(currentUserId).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val messages = snapshot.children.mapNotNull {
                    it.getValue(MonologueMessage::class.java)
                }
                liveData.postValue(messages)
            }

            override fun onCancelled(error: DatabaseError) {
                // Error handling
            }
        })

        return liveData
    }
}