package kr.kau.nyangmal3.Repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kr.kau.nyangmal3.User

class CUserInfoRepository {
    private val dbRef: DatabaseReference = Firebase.database.reference
    private val userRef = dbRef.child("user")

    fun getFriendsList(onResult: (ArrayList<User>) -> Unit) {
        val friendList = ArrayList<User>()

        userRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                friendList.clear()
                snapshot.children.mapNotNullTo(friendList) { it.getValue(User::class.java) }
                onResult(friendList)
            }

            override fun onCancelled(error: DatabaseError) {
                // 적절한 에러 처리
            }
        })
    }

    fun getUserProfile(userId: String, onResult: (User) -> Unit) {
        userRef.child(userId).addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.getValue(User::class.java)?.let(onResult)
            }

            override fun onCancelled(error: DatabaseError) {
                // 적절한 에러 처리
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

    fun updateUser(uid: String, updates: Map<String, Any>, onComplete: (Boolean) -> Unit) {
        userRef.child(uid).updateChildren(updates)
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }

    fun updateUserName(uid: String, newName: String, onComplete: (Boolean) -> Unit) {
        updateUser(uid, hashMapOf("name" to newName), onComplete)
    }
    fun updateUserImage(uid: String, imageUrl: String, onComplete: (Boolean) -> Unit) {
        updateUser(uid, hashMapOf("profileImageUrl" to imageUrl), onComplete)
    }
}