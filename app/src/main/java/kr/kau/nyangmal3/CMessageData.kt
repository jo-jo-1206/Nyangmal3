package kr.kau.nyangmal3
data class CMessageData(
    val message: String?,
    val sendTime:String,
    val sendId:String,
    val image:String
){
    constructor():this("","","","")
}