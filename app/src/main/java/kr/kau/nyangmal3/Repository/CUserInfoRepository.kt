package kr.kau.nyangmal3.Repository

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CUserInfoRepository {
    val database = Firebase.database
    val userRef = database.getReference("user")

}