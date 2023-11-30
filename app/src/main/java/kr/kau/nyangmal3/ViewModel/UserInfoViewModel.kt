package kr.kau.nyangmal3.ViewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kr.kau.nyangmal3.Repository.CUserInfoRepository
import kr.kau.nyangmal3.User

class UserInfoViewModel: ViewModel() {
    private val repository = CUserInfoRepository()

    private val _userProfile = MutableLiveData<User>()
    val userProfile: LiveData<User> = _userProfile

    private val _updateResult = MutableLiveData<Boolean>()
    val updateResult: LiveData<Boolean> = _updateResult

    private val _friendsList = MutableLiveData<ArrayList<User>>()
    val friendsList: LiveData<ArrayList<User>> = _friendsList
    private val _myName = MutableLiveData<String?>()
    val myName: LiveData<String?> = _myName

    private fun getCurrentUserId(): String? = Firebase.auth.currentUser?.uid

    fun loadFriendsList() {
        repository.getFriendsList { friends ->
            val filteredFriends = friends.filter { it.uID != getCurrentUserId() }
            _friendsList.value = ArrayList(filteredFriends)
        }
    }

    fun loadUserProfile() {
        getCurrentUserId()?.let { userId ->
            repository.getUserProfile(userId) { user->
                _userProfile.value = user
            }
        }
    }

    fun updateUserName(newName: String) {
        getCurrentUserId()?.let { uid ->
            repository.updateUserName(uid, newName) { success ->
                if (success) {
                    _userProfile.value = _userProfile.value?.copy(name = newName)
                }

                _updateResult.postValue(success)
            }
        }
    }

    fun updateUserImage(imageUri: Uri) {
        getCurrentUserId()?.let { uid ->
            val storageRef = Firebase.storage.reference.child("profile_images/$uid.jpg")

            storageRef.putFile(imageUri)
                .addOnSuccessListener {
                    storageRef.downloadUrl.addOnSuccessListener { uri ->
                        repository.updateUserImage(uid, uri.toString()) { success ->
                            if (success) {
                                _userProfile.value = _userProfile.value?.copy(profileImageUrl = uri.toString())
                            }

                            _updateResult.postValue(success)
                        }
                    }
                }
                .addOnFailureListener {
                    _updateResult.postValue(false)
                }
        }
    }

    fun fetchMyName() {
        repository.getMyName { name ->
            _myName.postValue(name)
        }
    }
}