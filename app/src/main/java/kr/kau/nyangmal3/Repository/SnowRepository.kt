package kr.kau.nyangmal3.repository

import android.net.Uri
//import androidx.datastore.core.Storage
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.storage.storage
import kr.kau.nyangmal3.SnowItem


class SnowRepository {
    //firebase에서 데이터를 가져와  라이브데이터에 ㄱㄱ
    val database = Firebase.database
    val snowRef = database.getReference("snow")

    fun observeSnowData(): MutableLiveData<MutableList<SnowItem>> { //snow은 뮤터블리스트임
        val mutableData = MutableLiveData<MutableList<SnowItem>>()

        snowRef.addValueEventListener(object : ValueEventListener { //여기로리슨되어서 내부처럼바뀜.
            val listData: MutableList<SnowItem> = mutableListOf<SnowItem>()
            override fun onDataChange(snapshot: DataSnapshot) {
                //이내용대로 snowData가바뀌길바람. 그럼 뷰가 뷰모델의라이브데이터를보고 자기도바꿈.
                //리얼타임디비에서 데이터를 가져와서 snowData라는 라이브데이터에 설정.
                //snowData.postValue(snapshot.value.toString()) //근데우린리스트인데. 이건 교수님코드한줄을 아래처럼길게함...
                //db에서가져온 각SnowItem을 변경가능리스트로 저장.
                listData.clear()
                if (snapshot.exists()) {
                    for (postSnapshot in snapshot.children) { //db의 데이터의 하위노드인데 각자식에대해반복.
                        val getData = postSnapshot.getValue(SnowItem::class.java) //각하위노드에대해snowItem클래스의객체생성(getValue)
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
        return mutableData
    }

    // 텍스트 데이터베이스에 업로드
    fun uploadText(userName: String, snowText: String, currentTime: Long, url: String) {
        val newItemRef = snowRef.push() // 새로운 데이터를 추가하고 반환된 참조를 newItemRef에 저장
        val newItemKey = newItemRef.key // 추가된 데이터의 고유한 키를 가져옴

        val text: SnowItem = SnowItem(userName, newItemKey, snowText, currentTime, url)
        newItemKey?.let { snowRef.child(it).setValue(text) }

    }

    fun uploadImage(imageUri: Uri, callback: (String?) -> Unit) {
        val fileName = Firebase.storage.reference.child("snow/${System.currentTimeMillis()}.jpg")
        fileName.putFile(imageUri)
            .addOnSuccessListener { _ ->
                fileName.downloadUrl.addOnSuccessListener { downloadUri ->
                    callback.invoke(downloadUri.toString())
                }.addOnFailureListener {
                    callback.invoke(null)
                }
            }
            .addOnFailureListener {
                callback.invoke(null)
            }
    }

    //firebase에서 데이터를 삭제
    // snowItem을 삭제하는 코드를 작성해야 합니다.
    // 예를 들어 Firebase Realtime Database에서 해당 데이터를 삭제하는 코드가 여기에 위치합니다.
    fun deleteData(snowItem: SnowItem) {
        deleteImage(snowItem)
        val itemKey = snowItem.key // SnowItem에서 키를 가져옴
        if (itemKey != null) {
            snowRef.child(itemKey).removeValue() // 해당 키에 대한 데이터 삭제
        }
    }
    fun deleteImage(snowItem: SnowItem) {
        // Firebase 스토리지 레퍼런스
        val storageRef = Firebase.storage.getReferenceFromUrl(snowItem.imageUrl)

        // 파일 삭제
        storageRef.delete()
    }
}