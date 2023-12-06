package kr.kau.nyangmal3.ViewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kr.kau.nyangmal3.CMessageData
import kr.kau.nyangmal3.Repository.CMessageRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone


class CMessageViewModel : ViewModel() {
    // viewModle과 repostiroy를 연결
    private val repository = CMessageRepository()
    private val _updateResult = MutableLiveData<Boolean>()
    private val mauth: FirebaseAuth = FirebaseAuth.getInstance()
    private val senderUid: String = mauth.currentUser!!.uid

    fun setReciveUid(uid: String) {
        repository.setReciveUid(uid)
    }

    fun fetchData(): LiveData<MutableList<CMessageData>> {
//        val mutableData = MutableLiveData<MutableList<CMessageData>>()
//        repository.observeMessage().observeForever {
//            mutableData.value = it
//        }
//        return mutableData
        return repository.observeMessage()
    }

    fun addMessage(data: CMessageData){
        repository.addMessage(data)
    }

    fun updateImage(uri: Uri){
        val fileName = Firebase.storage.reference.child("chat/$senderUid-${System.currentTimeMillis()}.jpg")
        fileName.putFile(uri)
            .addOnSuccessListener {
                fileName.downloadUrl.addOnSuccessListener { uri ->
                    repository.updateImage(uri.toString())
                }
            }
            .addOnFailureListener {
                _updateResult.postValue(false)
            }
    }

    fun getTime():String{
        return repository.getTime()
    }

}