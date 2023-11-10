package kr.kau.nyangmal3.repository

import com.google.firebase.Firebase
import com.google.firebase.database.database

class SnowRepository {
    val database = Firebase.database
    val snowRef = database.getReference("snow")


}