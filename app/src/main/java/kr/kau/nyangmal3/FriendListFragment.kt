package kr.kau.nyangmal3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.kau.nyangmal3.databinding.FragmentFriendListBinding

class FriendListFragment : Fragment() {
    private var _binding: FragmentFriendListBinding ?= null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFriendListBinding.inflate(inflater, container, false)
        return binding.root
    }
}