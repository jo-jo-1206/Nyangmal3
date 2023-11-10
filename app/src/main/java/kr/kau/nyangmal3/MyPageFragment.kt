package kr.kau.nyangmal3

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import kr.kau.nyangmal3.databinding.DialogEditmynameBinding
import kr.kau.nyangmal3.databinding.DialogEditprofileBinding
import kr.kau.nyangmal3.databinding.FragmentMyPageBinding

class MyPageFragment : Fragment() {
    private var userName: String = "조성우"

    private var _binding: FragmentMyPageBinding ?= null
    private val binding get() = _binding!!

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
        // 갤러리 어플 실행
        val intent = Intent(Intent.ACTION_PICK) // 사용자가 데이터를 선택하고
        intent.type = "image/*"
        startActivityForResult(intent, 105)
        // 질문 : startActivityForResult는 더이상 사용하지 않는 함수라고 하는데 이미지 접근 어케 하나요?

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("사진 편집창").setMessage("사진을 편집합니다")
        builder.create().show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when(requestCode) {
                105 -> {
                    var uri = data?.data
                    binding.imgMyProfilePic.setImageURI(uri)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}