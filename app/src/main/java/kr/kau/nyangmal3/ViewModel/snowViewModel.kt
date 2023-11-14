package kr.kau.nyangmal3.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.kau.nyangmal3.SnowItem
import kr.kau.nyangmal3.repository.SnowRepository
import kr.kau.nyangmal3.viewmodel.SnowViewModel


class SnowViewModel : ViewModel() {

    private val repository: SnowRepository = SnowRepository()

    private val _snowData = MutableLiveData<List<SnowItem>>()
    val snowData: LiveData<List<SnowItem>> get() = _snowData

    init {
        fetchData()
    }

    private fun fetchData() {
        val mutableData = MutableLiveData<List<SnowItem>>()
        repository.observeSnowData(mutableData)
        _snowData.value = mutableData.value
    }

    fun addSnow(data: SnowItem) {
        // 여기에서 이미지 및 텍스트 업로드 메서드를 호출하여 Firebase에 데이터를 추가하세요.
        repository.uploadImage(Uri.parse(data.imageUrl)) // 이미지 업로드 호출, imageUrl을 Uri로 변환하여 전달
        repository.uploadText(data.postText) // 예시로 텍스트 업로드 호출
    }
}

/*package kr.kau.nyangmal3.viewmodel

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
        repository.uploadText("")
    }

}*/