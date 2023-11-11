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

    // Firebase에서 데이터를 가져오는 메서드 등을 구현
    fun fetchData() {
        repository.getSnowData().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val snowItems = mutableListOf<SnowItem>()
                for (dataSnapshot in snapshot.children) {
                    val snowItem = dataSnapshot.getValue(SnowItem::class.java)
                    snowItem?.let { snowItems.add(it) }
                }
                _snowData.value = snowItems
            }

            override fun onCancelled(error: DatabaseError) {
                // 데이터 가져오기 실패 처리
            }
        })
    }
}


//class snowViewModel : ViewModel(){
//
//}