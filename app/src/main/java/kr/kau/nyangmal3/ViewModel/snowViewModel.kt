package kr.kau.nyangmal3.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.kau.nyangmal3.CMessageData
import kr.kau.nyangmal3.SnowItem
import kr.kau.nyangmal3.repository.SnowRepository

class SnowViewModel : ViewModel() {
    private val repository: SnowRepository()
    // LiveData를 사용하여 UI에 표시할 데이터를 감시
    private val _snowData = MutableLiveData<List<SnowItem>>() //챗은 ArrayList
    val snowData: LiveData<List<SnowItem>> get() = _snowData
    init {
        repository.observeSnowData(_snowData)
    }//이거 왜 챗에 없지
    //이건 챗에서 가져옴
    fun fetchData(): LiveData<MutableList<SnowItem>> {
        val mutableData = MutableLiveData<MutableList<SnowItem>>()
        repository.observeSnowData(mutableData.value.List<SnowItem>)
        return mutableData
    }

    //챗에서 가져옴
    //레파지토리를 이용해 데이터베이스에 값을 저장
    fun addSnow(data: SnowItem){
        //레파지토리를 이용해 데이터베이스에 값을 저장
        repository.uploadImage()
        repository.uploadText()
    }

}