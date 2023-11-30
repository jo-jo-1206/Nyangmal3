package kr.kau.nyangmal3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kr.kau.nyangmal3.ViewModel.UserInfoViewModel
import kr.kau.nyangmal3.databinding.FragmentFriendListBinding
import kr.kau.nyangmal3.viewmodel.NyangmalViewModel

class FriendListFragment : Fragment() {
    private lateinit var binding: FragmentFriendListBinding
    private val viewModel: UserInfoViewModel by viewModels()
    private val viewModelNyangmal: NyangmalViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFriendListBinding.inflate(inflater, container, false)

        val friendsAdapter = FriendsAdapter(requireContext(), arrayListOf(), viewModelNyangmal)

        binding.recFriends.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.recFriends.adapter = friendsAdapter

        viewModel.friendsList.observe(viewLifecycleOwner, Observer { friends ->
            friendsAdapter.updateFriendsList(friends)
        })

        viewModel.loadFriendsList()

        return binding.root
    }
}