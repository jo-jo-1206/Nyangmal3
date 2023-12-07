package kr.kau.nyangmal3

data class NyangmalItem(
    var key: String? = null,
    val nyangText: String
){
    constructor():this("","")
} // 빈 문자열로 초기화