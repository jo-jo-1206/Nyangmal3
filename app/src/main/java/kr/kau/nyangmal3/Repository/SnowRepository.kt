package kr.kau.nyangmal3.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
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
    fun uploadText(text: SnowItem){
//        var snowKey: String = "post" 따로 정해주고싶으면 이렇게 해도되고
//        snowRef.child(snowKey).push().setValue(text)
        snowRef.push().setValue(text)
    }
}
/*
class SnowRepository {
    //firebase에서 데이터를 가져와  라이브데이터에 ㄱㄱ
    fun observeSnowData(): MutableLiveData<ArrayList<SnowItem>> { //snow은 뮤터블리스트임
        val mutableData = MutableLiveData<ArrayList<SnowItem>>()

        snowRef.addValueEventListener(object : ValueEventListener { //여기로리슨되어서 내부처럼바뀜.
            override fun onDataChange(snapshot: DataSnapshot) {
                //이내용대로 snowData가바뀌길바람. 그럼 뷰가 뷰모델의라이브데이터를보고 자기도바꿈.
                //리얼타임디비에서 데이터를 가져와서 snowData라는 라이브데이터에 설정.
                //snowData.postValue(snapshot.value.toString()) //근데우린리스트인데. 이건 교수님코드한줄을 아래처럼길게함...
                //db에서가져온 각SnowItem을 변경가능리스트로 저장.
                val snowItems: ArrayList<SnowItem> = arrayListOf() //db에서가져온 각SnowItem을 리스트로 저장
                if (snapshot.exists()) {
                    for (postSnapshot in snapshot.children) { //db의 데이터의 하위노드인데 각자식에대해반복.
                        val getData = postSnapshot.getValue(SnowItem::class.java) //각하위노드에대해snowItem클래스의객체생성(getValue)
                        //ㄴ firebase에서 가져온데이터를 snowItem의 인스턴스로 변환함.
                        getData?.let { //let사용(null아닐때만실행), snowItems에 snowitem을추가
                            snowItems.add(it)
                        }
                    }
                    // 데이터가 변경될 때마다 mutableData.value에 반영
                    mutableData.value = snowItems //snowItems리스트를 mutableData라이브데이터에 할당
                    // -> 옵저버들이 알아채고 ui바뀌게됨.
                }
            }
            override fun onCancelled(error: DatabaseError) {
                //데이터 가져오기 실패 처리한것임.
            }
        })
        return mutableData
    }

    //데이터베이스에 저장
    // 이미지 업로드 로직을 구현
//    fun uploadImage(imageUri: Uri): Task<Uri> {
//        val imageName = "${System.currentTimeMillis()}_image.jpg" // 이미지 파일명 생성
//        val imageRef = storage.reference.child("snow/$imageName") // Firebase Storage 경로 지정
//
//        // 이미지를 Firebase Storage에 업로드
//        return imageRef.putFile(imageUri)
//            .continueWithTask { task ->
//                if (!task.isSuccessful) {
//                    task.exception?.let { throw it }
//                }
//                // 이미지 업로드가 성공하면 다운로드 URL을 반환
//                imageRef.downloadUrl
//            }
//    }

    // 텍스트 데이터베이스에 업로드
    fun uploadText(text: SnowItem){
//        var snowKey: String = "post" 따로 정해주고싶으면 이렇게 해도되고
//        snowRef.child(snowKey).push().setValue(text)
        snowRef.push().setValue(text)

    }
}
 */