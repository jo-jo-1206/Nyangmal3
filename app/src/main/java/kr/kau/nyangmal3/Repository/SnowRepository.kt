package kr.kau.nyangmal3.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import kr.kau.nyangmal3.SnowItem

class SnowRepository {
    val database = Firebase.database
    val snowRef = database.getReference("snow")
    val storage: FirebaseStorage = Firebase.storage

    //firebase에서 데이터를 가져와 snowData라는 라이브데이터에 ㄱㄱ
    fun observeSnowData(snowData: MutableLiveData<List<SnowItem>>){ //챗은 뮤터블리스트임
        snowRef.addValueEventListener(object : ValueEventListener { //여기로리슨되어서 내부처럼바뀜.
            override fun onDataChange(snapshot: DataSnapshot) {
                //이내용대로 snowData가바뀌길바람. 그럼 뷰가 뷰모델의라이브데이터를보고 자기도바꿈.
                //리얼타임디비에서 데이터를 가져와서 snowData라는 라이브데이터에 설정.
                //snowData.postValue(snapshot.value.toString()) //근데우린리스트인데. 이건 교수님코드한줄을 아래처럼길게함...
                val snowItems = mutableListOf<SnowItem>() //db에서가져온 각SnowItem을 변경가능리스트로 저장.
                for (dataSnapshot in snapshot.children) { //db의 데이터의 하위노드인데 각자식에대해반복.
                    val snowItem = dataSnapshot.getValue(SnowItem::class.java) //각하위노드에대해snowItem클래스의객체생성(getValue)
                    //ㄴ firebase에서 가져온데이터를 snowItem의 인스턴스로 변환함.
                    snowItem?.let { snowItems.add(it) } //let사용(null아닐때만실행), snowItems에 snowitem을추가
                }
                snowData.value = snowItems  //snowItems리스트를 snowData라이브데이터에 할당
                // -> 옵저버들이 알아채고 ui바뀌게됨.

            }
            override fun onCancelled(error: DatabaseError) {
                //데이터 가져오기 실패 처리한것임.
            }
        })
    }

    //데이터베이스에 저장
    fun uploadImage(imageUri: Uri): Task<Uri> {
        // 이미지 업로드 로직을 구현
    }

    fun uploadText(text: String): Task<Void> {
        // 텍스트 업로드 로직을 구현
    }

    fun getSnowData(): DatabaseReference {
        // Firebase Realtime Database에서 데이터를 가져오는 로직을 구현
    }


}