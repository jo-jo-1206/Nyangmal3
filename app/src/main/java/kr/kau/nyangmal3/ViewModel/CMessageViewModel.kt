package kr.kau.nyangmal3.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.kau.nyangmal3.CMessageData
import kr.kau.nyangmal3.Repository.CMessageRepository
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.TimeZone


class CMessageViewModel : ViewModel() {
    // viewModle과 repostiroy를 연결
    private val repository = CMessageRepository()
    //private val _mutableData = MutableLiveData<ArrayList<CMessageData>>()
    //val message : LiveData<ArrayList<CMessageData>> get() = _mutableData

    // 레파지토리에서 데이터 가져오기
    // 메세지를 리스트로


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

    // 시간 가져오기
    fun getTime():String {
        val currentTime = System.currentTimeMillis()
        // 현재 시간을 Data 타입으로 변환
        val timeData = Date(currentTime)

        val timeFormat = SimpleDateFormat("yyyyMMddHHmmss")
        // 시간 맞춰주기
        timeFormat.timeZone = TimeZone.getTimeZone("GMT+09:00")
        return timeFormat.format(timeData).toString()
    }
}