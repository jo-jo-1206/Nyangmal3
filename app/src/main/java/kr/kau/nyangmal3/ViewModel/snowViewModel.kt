package kr.kau.nyangmal3.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.kau.nyangmal3.SnowItem
import kr.kau.nyangmal3.repository.SnowRepository

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

    fun addSnow(userName: String, snowText: String, currentTime: Long, imageUrl: Uri){
        val url = repository.uploadImage(imageUrl) {result ->
            repository.uploadText(userName, snowText, currentTime, result!!)
        }
    }

    fun deleteSnow(snowItem: SnowItem) {
        repository.deleteData(snowItem)
    }

    private var selectedImageUri: Uri? = null

    fun setSelectedImageUri(uri: Uri) {
        selectedImageUri = uri
    }

    fun getSelectedImageUri(): Uri? {
        return selectedImageUri
    }

    fun resetSelectedImageUri() {
        selectedImageUri = null
    }


}