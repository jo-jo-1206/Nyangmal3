package kr.kau.nyangmal3.Repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kr.kau.nyangmal3.NyangmalItem
import kr.kau.nyangmal3.SnowItem

//ㅇㅋ
class NyangmalRepository {
    val database = Firebase.database
    val nyangRef = database.getReference("nyang")
    private val nauth: FirebaseAuth = FirebaseAuth.getInstance()
    private val myUid: String? = nauth.currentUser?.uid
    private var receiveUid: String? = null

    fun setReciveUid(uid: String) {
        receiveUid = uid
    }

    //받는사람 uid아래돌면서 리스트 ㅇㅇ가져오든말든잘가져오기
    fun observeNyangData(): MutableLiveData<MutableList<NyangmalItem>> { //snow은 뮤터블리스트임
        if (myUid == null) {
            throw IllegalStateException("ReciveUid is not set. Call setReciveUid(uid) before calling observeMessage()")
        }
        val mutableData = MutableLiveData<MutableList<NyangmalItem>>()
        myUid?.let {
            nyangRef.child(it).addValueEventListener(object : ValueEventListener { //여기로리슨되어서 내부처럼바뀜.
                val listData: MutableList<NyangmalItem> = mutableListOf<NyangmalItem>()
                override fun onDataChange(snapshot: DataSnapshot) {
                    //이내용대로 snowData가바뀌길바람. 그럼 뷰가 뷰모델의라이브데이터를보고 자기도바꿈.
                    //리얼타임디비에서 데이터를 가져와서 snowData라는 라이브데이터에 설정.
                    //snowData.postValue(snapshot.value.toString()) //근데우린리스트인데. 이건 교수님코드한줄을 아래처럼길게함...
                    //db에서가져온 각SnowItem을 변경가능리스트로 저장.
                    listData.clear()
                    if (snapshot.exists()) {
                        for (postSnapshot in snapshot.children) { //db의 데이터의 하위노드인데 각자식에대해반복.
                            val getData = postSnapshot.getValue(NyangmalItem::class.java) //각하위노드에대해snowItem클래스의객체생성(getValue)
                            //ㄴ firebase에서 가져온데이터를 snowItem의 인스턴스로 변환함.
                            listData.add(getData!!)
                            mutableData.value = listData
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    //데이터 가져오기 실패 처리한것임.
                }
            })
        }
        return mutableData
    }

    // 텍스트 데이터베이스에 업로드
//    fun uploadText(nyangText: String){
//        val newItemRef =
//            receiveUid?.let { nyangRef.child(it).push() } // 새로운 데이터를 추가하고 반환된 참조를 newItemRef에 저장
//        val newItemKey = newItemRef?.key // 추가된 데이터의 고유한 키를 가져옴
//
//        val text: NyangmalItem = NyangmalItem(newItemKey, nyangText)
////받는사람 uid아래 뉴고유키아래에 텍스트 저장 ㅇㅇ
//        receiveUid?.let {
//            newItemKey?.let { it2 ->
//                nyangRef.child(it).child(it2).setValue(text)
//            }
//        }
//    }
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

//    fun deleteData(nyangmalItem: NyangmalItem) {
//        val itemKey = nyangmalItem.key // SnowItem에서 키를 가져옴
//        if (itemKey != null) {
//            nyangRef.child(itemKey).removeValue() // 해당 키에 대한 데이터 삭제
//        }
//    }
fun deleteData(nyangmalItem: NyangmalItem) {
    val itemKey = nyangmalItem.key
    itemKey?.let {
        nyangRef.child(receiveUid ?: "").child(it).removeValue()
    }
}
    //firebase에서 데이터를 삭제
    // snowItem을 삭제하는 코드를 작성해야 합니다.
    // 예를 들어 Firebase Realtime Database에서 해당 데이터를 삭제하는 코드가 여기에 위치합니다.
}