package kr.kau.nyangmal3

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import kr.kau.nyangmal3.databinding.DialogEditmynameBinding
import kr.kau.nyangmal3.databinding.FragmentSnowBinding
import kr.kau.nyangmal3.databinding.FragmentSnowDialogBinding

//냥: 펑/스토리 ~24시간뒤면 사라짐. 한번읽으면사라짐?, 모든 사람들의 상태메시지모음화면느낌
class SnowFragment : Fragment() {

    private var _binding: FragmentSnowBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSnowBinding.inflate(inflater, container, false)

        binding.addsnowIv.setOnClickListener {
            showDialog()
        }
        return binding.root
    }

    private fun showDialog(){
        val dialogBinding = FragmentSnowDialogBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext()).setView(dialogBinding.root).create()

       dialogBinding.snowpicB.setOnClickListener {
           editPicture()
           dialog.dismiss()
       }

        dialogBinding.snowtextB.setOnClickListener {
            editText()
            dialog.dismiss()
        }
    }

    private fun editText(){
        val dialogBinding = FragmentSnowDialogBinding.inflate(layoutInflater)

        val dialog = android.app.AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        // 확인 버튼을 눌렀을 때
        dialogBinding.snowtextB.setOnClickListener {
            // 사용자가 입력한 새로운 이름을 가져옴
            val newName = dialogBinding.snowtextEt.text.toString()

            binding.txtUserName.setText(newName)

            dialog.dismiss()
        }

        dialog.show()
    }

    private fun editPicture() {
        // 갤러리 어플 실행
        val intent = Intent(Intent.ACTION_PICK) // 사용자가 데이터를 선택하고
        intent.type = "image/*"

        val builder = android.app.AlertDialog.Builder(requireContext())
        builder.setTitle("사진 편집창").setMessage("사진을 편집합니다")
        builder.create().show()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}