package kr.kau.nyangmal3

data class MonologueMessage(
    val uid: String,
    val message: String,
    val timestamp: String
) {
    constructor(): this("", "", "")
}