package kr.kau.nyangmal3

data class SnowItem(
    var key: String? = null,
    val postText: String,
    val timestamp: Long
){
    constructor():this("","",0)
}
//data class SnowItem(
//    val userName: String,
//    val postText: String,
//    val timestamp: Long,
//    val imageUrl: String
//){
//    constructor():this("","",0,"")
//}