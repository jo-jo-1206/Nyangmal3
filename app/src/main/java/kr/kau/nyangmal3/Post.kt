package kr.kau.nyangmal3

data class Post(
    val postId: String,
    val userId: String,
    val userName: String,
    val imageUrl: String,
    val text: String,
    val timestamp: Long
)
