package kr.kau.nyangmal3

data class SnowItem(
    val userName: String,
    val postText: String,
    val timestamp: Long,
//    val postId: String,
//    val userId: String, 이건여기서 가져오는게 맞나?
    val imageUrl: String
){
    constructor():this("","",0,"")
    // Map 형태로 변환하는 함수
    //Firebase Realtime Database에서는 데이터를 효율적으로 저장하기 위해 Map 형태로 변환하여 저장하는 것이 권장됩니다.

//    fun toMap(): Map<String, Any?> {
//        return mapOf(
//            "userName" to userName,
//            "postText" to postText,
//            "timestamp" to timestamp,
//            "imageUrl" to imageUrl
//        )
//    }

}