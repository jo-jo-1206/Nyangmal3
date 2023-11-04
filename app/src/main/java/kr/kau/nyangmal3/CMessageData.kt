package kr.kau.nyangmal3

import android.os.Message
// 데이터를 저장하기 위한 클래스.
data class CMessageData(
    val message: String? // 널{이 될 수 있음
){
    constructor():this("")
}