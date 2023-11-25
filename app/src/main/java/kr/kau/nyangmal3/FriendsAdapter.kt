package kr.kau.nyangmal3

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.kau.nyangmal3.databinding.DialogFriendactionBinding
import kr.kau.nyangmal3.databinding.DialogSendnyangmalBinding
import kr.kau.nyangmal3.databinding.ListFriendsBinding
import kr.kau.nyangmal3.viewmodel.NyangmalViewModel

class FriendsAdapter
    (
    private val context: Context,
    private val friends: ArrayList<User>,
    private val viewModel: NyangmalViewModel
            )
    : RecyclerView.Adapter<FriendsAdapter.friendsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): friendsViewHolder {
        val binding = ListFriendsBinding.inflate(LayoutInflater.from(parent.context))
        return friendsViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: friendsViewHolder, position: Int) {
        val currentUser = friends[position]
        holder.bind(currentUser)

        Glide.with(context)
            .load(currentUser.profileImageUrl)
            .placeholder(R.drawable.defaultprofpic)
            .into(holder.binding.imgFriendProfPic)
    }

    override fun getItemCount(): Int = friends.size

    fun updateFriendsList(friends: ArrayList<User>) {
        this.friends.clear()
        this.friends.addAll(friends)
        notifyDataSetChanged()
    }

    class friendsViewHolder(val binding: ListFriendsBinding, private val viewModel: NyangmalViewModel) : RecyclerView.ViewHolder(binding.root) {
        private var currentFriend: User ?= null

        val nameText: TextView = binding.txtFriendName

        fun bind(friend: User) {
            currentFriend = friend
            binding.txtFriendName.text = friend.name
            binding.imgFriendProfPic.setOnClickListener {
                showFriendActionDialog(friend)
            }
        }

        private fun showFriendActionDialog(friend: User) {
            val dialog = Dialog(itemView.context)
            val dialogBinding = DialogFriendactionBinding.inflate(LayoutInflater.from(itemView.context))

            dialogBinding.txtFriendName.text = friend.name + "와..."

            dialog.setContentView(dialogBinding.root)

            dialogBinding.btnTalk.setOnClickListener {
                dialog.dismiss()
                talkWithFriend(friend)
            }

            dialogBinding.btnSendNyangmal.setOnClickListener {
                dialog.dismiss()
                showSendNyangmalDialog(friend)
            }

            dialog.show()
        }

        private fun talkWithFriend(friend: User) {
            val context = itemView.context
            val intent = Intent(context, ChatActivity::class.java).apply {
                putExtra("uid", friend.uID)
            }

            context.startActivity(intent)
        }

        private fun showSendNyangmalDialog(friend: User) {
            val dialog = Dialog(itemView.context)
            val dialogBinding = DialogSendnyangmalBinding.inflate(LayoutInflater.from(itemView.context))

            dialogBinding.txtSendTo.text = friend.name + "에게..."

            dialog.setContentView(dialogBinding.root)

            dialogBinding.btnConfirm.setOnClickListener { // 보내기 버튼 눌렀을 때
                dialog.dismiss()
                // 냥말 보내기

                // 냥말 박스
                val nyangText = dialogBinding.txtSendNyangmal.text.toString()

                viewModel.addNyangmal(nyangText)
                dialogBinding.txtSendNyangmal.setText("") // 설명 전송하면 다시 텍스트 칸 초기화해주기

            }
            dialog.show()
        }
    }
}