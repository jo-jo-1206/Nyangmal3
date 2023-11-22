package kr.kau.nyangmal3.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.kau.nyangmal3.Repository.CUserInfoRepository
import kr.kau.nyangmal3.User

class UserInfoViewModel: ViewModel() {
    private val repository = CUserInfoRepository()
    private val _friendsList = MutableLiveData<ArrayList<User>>()
    val friendsList: LiveData<ArrayList<User>> = _friendsList

    fun loadFriendsList() {
        repository.getFriendsList { friends ->
            _friendsList.value = friends
        }
    }
}