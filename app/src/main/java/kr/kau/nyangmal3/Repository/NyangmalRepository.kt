package kr.kau.nyangmal3.Repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kr.kau.nyangmal3.NyangmalItem

class NyangmalRepository {
    val database = Firebase.database
    val nyangRef = database.getReference("nyang")
    private val nauth: FirebaseAuth = FirebaseAuth.getInstance()
    private val myUid: String? = nauth.currentUser?.uid
    private var receiveUid: String? = null

    fun setReciveUid(uid: String) {
        receiveUid = uid
    } //받는사람 = 친구

    //받는사람=나 uid아래돌면서 리스트 가져오기
    // 데이터 관찰하고 업데이트
    fun observeNyangData(): MutableLiveData<ArrayList<NyangmalItem>> {  //arraylist도 mutable ㅇㅇ
        if (myUid == null) {
            throw IllegalStateException("ReciveUid is not set")
        }
        val mutableData = MutableLiveData<ArrayList<NyangmalItem>>()
        myUid?.let {
            nyangRef.child(it).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listData: ArrayList<NyangmalItem> = ArrayList()

                    if (snapshot.exists()) {
                        for (postSnapshot in snapshot.children) {
                            val getData = postSnapshot.getValue(NyangmalItem::class.java)
                            getData?.let { listData.add(it) }
                        }
                    }
                    // 데이터 업데이트를 한번에   (전엔 각 데이터 하나하나 가져왔음
                    mutableData.value = listData
                }
                override fun onCancelled(error: DatabaseError) {
                // 데이터 가져오기 실패
                }
            })
        }
        return mutableData
    }


    // 파이어베이스에 텍스트 업로드
    fun uploadText(nyangText: String) {
        val newItemRef = receiveUid?.let { nyangRef.child(it).push() }
        val newItemKey = newItemRef?.key

        val text: NyangmalItem = NyangmalItem(newItemKey, nyangText)

        if (newItemKey != null) {
            receiveUid?.let {
                nyangRef.child(it).child(newItemKey).setValue(text)
            }
        }
    }

    fun deleteData(nyangmalItem: NyangmalItem) {
        val itemKey = nyangmalItem.key
        if (itemKey != null) {
            myUid?.let {
                nyangRef.child(it).child(itemKey).removeValue()
            }
        }
    }
}