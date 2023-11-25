package kr.kau.nyangmal3

data class User(
    var name: String,
    var email: String,
    var uID: String,
    var profileImageUrl: String
) {
    constructor(): this("", "", "", "")
}
