package kr.kau.nyangmal3

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kr.kau.nyangmal3.databinding.FragmentFriendListBinding

class FriendListFragment : Fragment() {
    private lateinit var _binding: FragmentFriendListBinding
    private val binding get() = _binding!!
    lateinit var friendsAdapter: FriendsAdapter

    private lateinit var Auth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference

    private lateinit var friendList: ArrayList<User>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFriendListBinding.inflate(inflater, container, false)
        binding.recFriends.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        Auth = Firebase.auth
        dbRef = Firebase.database.reference

        friendList = ArrayList()

        friendsAdapter = FriendsAdapter(requireContext(), friendList)
        binding.recFriends.adapter = friendsAdapter

        dbRef.child("user").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) { // 데이터가 변경될 때
                for(postSnapshot in snapshot.children) {

                    val currentUser = postSnapshot.getValue(User::class.java)

                    // 본인 정보 제외
                    if (Auth.currentUser?.uid != currentUser?.uID) {
                        friendList.add(currentUser!!)
                    }
                }

                friendsAdapter.notifyDataSetChanged() // 들어온 데이터가 실제 화면에 적용
            }

            override fun onCancelled(error: DatabaseError) { // 오류가 발생할 때 (실패 했을 때)
            }
        })

        return binding.root
    }
}