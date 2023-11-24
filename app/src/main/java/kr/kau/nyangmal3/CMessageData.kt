package kr.kau.nyangmal3

import android.os.Message
// 데이터를 저장하기 위한 클래스.
data class CMessageData(
    val message: String,
    val sendTime:String,
    var sendId:String?
){
    constructor():this("","","")
}