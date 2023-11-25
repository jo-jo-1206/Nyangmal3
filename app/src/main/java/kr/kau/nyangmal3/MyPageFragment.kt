package kr.kau.nyangmal3

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kr.kau.nyangmal3.Repository.CUserInfoRepository
import kr.kau.nyangmal3.ViewModel.UserInfoViewModel
import kr.kau.nyangmal3.databinding.DialogEditmynameBinding
import kr.kau.nyangmal3.databinding.DialogEditprofileBinding
import kr.kau.nyangmal3.databinding.FragmentMyPageBinding

class MyPageFragment : Fragment() {
    // private var userName: String = "조성우"

    private val repository = CUserInfoRepository()
    init {
        repository
    }
    private var _binding: FragmentMyPageBinding ?= null
    private val binding get() = _binding!!

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            // binding.imgMyProfilePic.setImageURI(it)
            uploadImageToStorage(it)
        }
    }

    val viewModel_userInfo : UserInfoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMyPageBinding.inflate(inflater, container, false)

        binding.btnEditProfile.setOnClickListener {
            showEditOptionsDialog()
        }

        binding.btnNyangMal.setOnClickListener {
            val intent = Intent(activity, NyangmalBoxActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentUser = Firebase.auth.currentUser
        currentUser?.let {user ->
            val uID = user.uid
            val userRef = Firebase.database.reference.child("user").child(uID)

            userRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userInfo = snapshot.getValue(User::class.java)
                    binding.txtUserName.text = userInfo?.name

                    val profileImageUrl = if (userInfo?.profileImageUrl.isNullOrEmpty()) {
                        "https://firebasestorage.googleapis.com/v0/b/nyangmal-38f77.appspot.com/o/profile_images%2Fdefaultprofpic.jpg?alt=media&token=49c9dc8f-06d8-4f23-acfb-41f2fb5c3ca9"
                    } else {
                        userInfo?.profileImageUrl
                    }

                    Glide.with(this@MyPageFragment).load(profileImageUrl).into(binding.imgMyProfilePic)
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

        }
    }

    private fun showEditOptionsDialog() {
        val dialogBinding = DialogEditprofileBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        dialogBinding.btnEditName.setOnClickListener {
            editName()
            dialog.dismiss()
        }

        dialogBinding.btnEditPic.setOnClickListener {
            editPicture()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun editName() {
        val dialogBinding = DialogEditmynameBinding.inflate(layoutInflater)
        
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()
        
        // 확인 버튼을 눌렀을 때
        dialogBinding.btnConfirm.setOnClickListener {
            // 사용자가 입력한 새로운 이름을 가져옴
            val newName = dialogBinding.txtEditNameField.text.toString()
            binding.txtUserName.setText(newName)

            dialog.dismiss()
        }

        dialog.show()
    }

    private fun editPicture() {
        getContent.launch("image/*")
    }

    private fun uploadImageToStorage(imageUri: Uri) {
        val uid = Firebase.auth.currentUser?.uid ?: return
        val ref = Firebase.storage.reference.child("profile_images/$uid.jpg")

        ref.putFile(imageUri)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener { uri ->
                    saveUserToDB(uri.toString())
                }
            }
            .addOnFailureListener {

            }
    }

    private fun saveUserToDB(profileImageUrl: String) {
        val uid = Firebase.auth.currentUser?.uid ?: return
        val userRef = Firebase.database.reference.child("user").child(uid)

        val userUpdates = hashMapOf<String, Any>(
            "profileImageUrl" to profileImageUrl
        )

        userRef.updateChildren(userUpdates)
            .addOnSuccessListener {

            }
            .addOnFailureListener {

            }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}