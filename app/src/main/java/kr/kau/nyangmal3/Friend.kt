package kr.kau.nyangmal3

enum class eGender {
    MALE, FEMALE, END
}
data class Friend(val name: String, val gender: eGender, val email: String)