package kr.kau.nyangmal3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.kau.nyangmal3.databinding.FragmentNyangBinding

//냥: 펑/스토리 ~24시간뒤면 사라짐. 한번읽으면사라짐?, 모든 사람들의 상태메시지모음화면느낌
class NyangFragment : Fragment() {

    private lateinit var binding: FragmentNyangBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nyang, container, false)
    }
}