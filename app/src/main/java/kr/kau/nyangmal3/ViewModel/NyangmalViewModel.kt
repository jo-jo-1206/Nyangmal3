package kr.kau.nyangmal3.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.kau.nyangmal3.NyangmalItem
import kr.kau.nyangmal3.Repository.NyangmalRepository

class NyangmalViewModel: ViewModel() {
    private val repository: NyangmalRepository = NyangmalRepository()

    private val _nyangData = MutableLiveData<ArrayList<NyangmalItem>>()
    val nyangData: LiveData<ArrayList<NyangmalItem>> get() = _nyangData

    //
    fun setReceiveUid(uid: String) {
        repository.setReciveUid(uid)
    }

    fun fetchData(): LiveData<MutableList<NyangmalItem>> {
        val mutableData = MutableLiveData<MutableList<NyangmalItem>>()
        repository.observeNyangData().observeForever {
            mutableData.value = it
        }
        return mutableData
    }

    // 레포지토리 업로드텍스트 메서드 호출하여 파이어베이스에 업로드
    fun addNyangmal(nyangText: String){
        repository.uploadText(nyangText)
    }
    // 레포지토리 딜리트데이터 메서드 호출하여 파이어베이스에 업로드
    fun deleteNynagmal(nyangmalItem: NyangmalItem) {
        repository.deleteData(nyangmalItem)
    }
}