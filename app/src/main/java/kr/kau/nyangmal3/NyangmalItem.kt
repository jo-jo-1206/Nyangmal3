package kr.kau.nyangmal3

data class NyangmalItem(
    var key: String? = null,
    val nyangText: String
){
    constructor():this("","")
}