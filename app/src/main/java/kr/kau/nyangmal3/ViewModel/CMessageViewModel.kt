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


    private val senderUid: String? = mauth.currentUser?.uid

    fun setReciveUid(uid: String) {
        repository.setReciveUid(uid)
    }

    fun fetchData(): LiveData<MutableList<CMessageData>> {
        val mutableData = MutableLiveData<MutableList<CMessageData>>()
        repository.observeMessage().observeForever {
            mutableData.value = it
        }
        return mutableData
    }

    fun addMessage(data: CMessageData){
        //레파지토리를 이용해 데이터베이스에 값을 저장
        repository.addMessage(data)
    }

    fun updateImage(uri: Uri){
        val fileName = Firebase.storage.reference.child("chat/$senderUid-${System.currentTimeMillis()}.jpg")
        // 파이어베이스 스토리지의 chat 아래에 uri를 저장해라.
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

    // 시간 가져오기
    fun getTime():String{
        return repository.getTime()
    }

}