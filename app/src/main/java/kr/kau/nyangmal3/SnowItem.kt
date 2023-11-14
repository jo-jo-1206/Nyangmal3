package kr.kau.nyangmal3

data class SnowItem(
    val userName: String,
    val postText: String,
    val timestamp: Long,
//    val postId: String,
//    val userId: String, 이건여기서 가져오는게 맞나?
    val imageUrl: String
){
    // 다른 필요한 멤버 변수 및 생성자는 유지되어 있습니다.

    // Map 형태로 변환하는 함수
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "userName" to userName,
            "postText" to postText,
            "timestamp" to timestamp,
            "imageUrl" to imageUrl
        )
    }
}

