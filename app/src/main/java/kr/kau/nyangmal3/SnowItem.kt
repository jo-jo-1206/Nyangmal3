package kr.kau.nyangmal3

data class SnowItem(
    val userName: String,
    val postText: String,
    //val timestamp: String,
    val timestamp: Long,
//    val postId: String,
//    val userId: String, 이건여기서 가져오는게 맞나?
    val imageUrl: String
){
    constructor():this("","",0,"")

}