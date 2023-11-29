package kr.kau.nyangmal3.Repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kr.kau.nyangmal3.CMessageData
import kr.kau.nyangmal3.User

class CUserInfoRepository {
    private val dbRef: DatabaseReference = Firebase.database.reference
    private val userRef = dbRef.child("user")
    private val auth: FirebaseAuth = Firebase.auth

    fun getFriendsList(onResult: (ArrayList<User>) -> Unit) {
        val friendList = ArrayList<User>()

        userRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                friendList.clear()

                for (postSnapshot in snapshot.children) {
                    val user = postSnapshot.getValue(User::class.java)

                    if (auth.currentUser?.uid != user?.uID) {
                        user?.let {
                            friendList.add(it)
                        }
                    }
                }

                onResult(friendList)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    //현재 uid와 저장된 uid를 비교하여 같다면 name을 가져와서 변수에 저장하기
    fun getMyName(onNameResult: (String?) -> Unit) {
        val myUid = auth.currentUser?.uid

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var myName: String? = null

                for (postSnapshot in snapshot.children) {
                    val user = postSnapshot.getValue(User::class.java)

                    // Check if the UID matches
                    if (user?.uID == myUid) {
                        myName = user?.name
                        break
                    }
                }

                onNameResult(myName)
            }

            override fun onCancelled(error: DatabaseError) {
                onNameResult(null)
            }
        })
    }

}