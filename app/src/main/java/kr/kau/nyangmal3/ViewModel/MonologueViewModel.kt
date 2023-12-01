package kr.kau.nyangmal3.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kr.kau.nyangmal3.MonologueMessage
import kr.kau.nyangmal3.Repository.MonologueRepository
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class MonologueViewModel : ViewModel() {
    private val repository = MonologueRepository()
    private val auth = Firebase.auth

    val messages: LiveData<List<MonologueMessage>> = repository.getMessagesFromFirebase()

    fun addMessage(messageText: String) {
        val currentUserId = auth.currentUser?.uid ?: return
        val formattedTimestamp = convertTimestamp(System.currentTimeMillis())
        val message = MonologueMessage(currentUserId, messageText, formattedTimestamp)
        repository.addMessage(message)
    }

    private fun convertTimestamp(timestamp: Long): String {
        val sdf = SimpleDateFormat("a hh:mm", Locale.KOREA)
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(Date(timestamp))
    }
}
