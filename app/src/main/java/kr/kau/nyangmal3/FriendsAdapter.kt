package kr.kau.nyangmal3

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.kau.nyangmal3.databinding.DialogFriendactionBinding
import kr.kau.nyangmal3.databinding.ListFriendsBinding

class FriendsAdapter(private val context: Context, private val friends: ArrayList<User>)
    : RecyclerView.Adapter<FriendsAdapter.friendsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): friendsViewHolder {
        val binding = ListFriendsBinding.inflate(LayoutInflater.from(parent.context))
        return friendsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: friendsViewHolder, position: Int) {
        val currentUser = friends[position]
        holder.nameText.text = currentUser.name
    }

    override fun getItemCount(): Int = friends.size

    class friendsViewHolder(private val binding: ListFriendsBinding) : RecyclerView.ViewHolder(binding.root) {
        val nameText: TextView = binding.txtFriendName
        init {
            binding.imgFriendProfPic.setOnClickListener {
                val friend = itemView.tag as User
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
                sendNyangmalToFriend(friend)
            }

            dialog.show()
        }

        private fun talkWithFriend(friend: User) {
            val context = itemView.context
            val intent = Intent(context, ChatActivity::class.java).apply {
                putExtra("friend_name", friend.name)
            }

            context.startActivity(intent)
        }

        private fun sendNyangmalToFriend(friend: User) {

        }
    }
}