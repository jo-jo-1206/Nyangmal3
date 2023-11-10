package kr.kau.nyangmal3.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserInfoViewModel: ViewModel() {
    private val _name = MutableLiveData<String>("UserName")
    val name : LiveData<String> get() = _name
}