package kr.kau.nyangmal3.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.kau.nyangmal3.SnowItem
import kr.kau.nyangmal3.repository.SnowRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

//ui에서 발생한 데이터를 레포지토리에 넘겨?
class SnowViewModel: ViewModel() {
    private val repository: SnowRepository = SnowRepository()

    private val _snowData = MutableLiveData<ArrayList<SnowItem>>()
    val snowData: LiveData<ArrayList<SnowItem>> get() = _snowData

    init {
        fetchData()
    }

    fun fetchData(): LiveData<MutableList<SnowItem>> {
        val mutableData = MutableLiveData<MutableList<SnowItem>>()
        repository.observeSnowData().observeForever {
            mutableData.value = it
        }
        return mutableData
    }

    fun addSnow(data: SnowItem){
        // 여기에서 이미지 및 텍스트 업로드 메서드를 호출하여 Firebase에 데이터를 추가
        //레파지토리를 이용해 데이터베이스에 값을 저장
        repository.uploadText(data)
        repository.uploadImage(Uri.parse(data.imageUrl)) // 이미지 업로드 호출, imageUrl을 Uri로 변환하여 전달
    }

    //이게 나한테 필요한지는 모르겠음 나중에 보고 ㄱㄱ
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