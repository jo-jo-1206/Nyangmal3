package kr.kau.nyangmal3

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import kr.kau.nyangmal3.databinding.DialogEditprofileBinding
import kr.kau.nyangmal3.databinding.FragmentMyPageBinding

class MyPageFragment : Fragment() {

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

        return binding.root
    }

    private fun showEditOptionsDialog() {
        val dialogBinding = DialogEditprofileBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        dialog.window?.let { window ->
            val layoutParams = WindowManager.LayoutParams().apply {
                copyFrom(window.attributes)
                dimAmount = 0.75f
            }

            window.attributes = layoutParams
        }

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
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("이름 편집창").setMessage("이름을 편집합니다")
        builder.create().show()
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