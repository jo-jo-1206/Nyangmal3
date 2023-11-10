package kr.kau.nyangmal3

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kr.kau.nyangmal3.databinding.FragmentFriendListBinding

class FriendListFragment : Fragment() {
    private var _binding: FragmentFriendListBinding ?= null
    private val binding get() = _binding!!


    // TODO : db에서 받아온 친구 데이터로 바꾸기
    val friends = arrayOf(
        Friend("조소윤", eGender.FEMALE, "whthdbs1206@naver.com"),
        Friend("조성우", eGender.MALE, "zzz11411@naver.com"),
        Friend("윤지원", eGender.FEMALE, "dbswldnjs1206@naver.com")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFriendListBinding.inflate(inflater, container, false)

        binding.recFriends.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.recFriends.adapter = FriendsAdapter(friends)

        return binding.root
    }
}