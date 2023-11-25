package kr.kau.nyangmal3.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.kau.nyangmal3.NyangmalItem
import kr.kau.nyangmal3.Repository.NyangmalRepository

class NyangmalViewModel: ViewModel() {
    private val repository: NyangmalRepository = NyangmalRepository()

    private val _snowData = MutableLiveData<ArrayList<NyangmalItem>>()
    val snowData: LiveData<ArrayList<NyangmalItem>> get() = _snowData

    fun fetchData(): LiveData<MutableList<NyangmalItem>> {
        val mutableData = MutableLiveData<MutableList<NyangmalItem>>()
        repository.observeSnowData().observeForever {
            mutableData.value = it
        }
        return mutableData
    }

    fun addNyangmal(nyangText: String){
        // 여기에서 이미지 및 텍스트 업로드 메서드를 호출하여 Firebase에 데이터를 추가
        //레파지토리를 이용해 데이터베이스에 값을 저장
        repository.uploadText(nyangText)
    }

    fun deleteNynagmal(nyangmalItem: NyangmalItem) {
        // snowItem을 삭제하는 코드를 작성해야 합니다.
        // 예를 들어 Firebase Realtime Database에서 해당 데이터를 삭제하는 코드가 여기에 위치합니다.
        repository.deleteData(nyangmalItem)
    }
}