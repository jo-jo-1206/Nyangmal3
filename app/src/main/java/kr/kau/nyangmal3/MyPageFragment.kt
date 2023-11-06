package kr.kau.nyangmal3

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
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("사진 편집창").setMessage("사진을 편집합니다")
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}