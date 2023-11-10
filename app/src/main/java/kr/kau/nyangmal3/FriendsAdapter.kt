package kr.kau.nyangmal3

import android.app.Dialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.kau.nyangmal3.databinding.DialogFriendactionBinding
import kr.kau.nyangmal3.databinding.ListFriendsBinding

class FriendsAdapter(val friends: Array<Friend>) : RecyclerView.Adapter<FriendsAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListFriendsBinding.inflate(LayoutInflater.from(parent.context))

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        // 렌더링
        holder.bind(friends[position])
    }

    override fun getItemCount() = friends.size

    class Holder(private val binding: ListFriendsBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.imgFriendProfPic.setOnClickListener {
                val friend = itemView.tag as Friend

                showFriendActionDialog(friend)
            }
        }

        fun bind(friend: Friend) {
            // TODO : db에서 받아오는 데이터로 바꾸기
            binding.imgFriendProfPic.setImageResource( when(friend.gender) {
                eGender.MALE -> R.drawable.default_profpic_male
                eGender.FEMALE -> R.drawable.default_profpic_female
                else -> R.drawable.default_profpic_male
            })

            binding.txtFriendName.text = friend.name

            itemView.tag = friend
        }

        private fun showFriendActionDialog(friend: Friend) {
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

        private fun talkWithFriend(friend: Friend) {
            val context = itemView.context
            val intent = Intent(context, ChatActivity::class.java).apply {
                putExtra("friend_name", friend.name)
            }

            context.startActivity(intent)
        }

        private fun sendNyangmalToFriend(friend: Friend) {

        }
    }
}