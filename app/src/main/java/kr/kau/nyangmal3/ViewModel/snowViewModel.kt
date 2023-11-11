package kr.kau.nyangmal3.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.kau.nyangmal3.SnowItem
import kr.kau.nyangmal3.repository.SnowRepository

class SnowViewModel : ViewModel() {
    private val repository: SnowRepository()
    // LiveData를 사용하여 UI에 표시할 데이터를 감시
    private val _snowData = MutableLiveData<List<SnowItem>>()
    val snowData: LiveData<List<SnowItem>> get() = _snowData
    init {
        repository.observeSnowData(_snowData)
    }

    //is어쩌구
    //set어쩌구

}