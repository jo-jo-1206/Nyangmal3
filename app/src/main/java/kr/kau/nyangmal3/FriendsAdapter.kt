package kr.kau.nyangmal3

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.kau.nyangmal3.databinding.DialogFriendactionBinding
import kr.kau.nyangmal3.databinding.DialogSendnyangmalBinding
import kr.kau.nyangmal3.databinding.ListFriendsBinding
import kr.kau.nyangmal3.viewmodel.NyangmalViewModel

class FriendsAdapter(private val context: Context, private val friends: ArrayList<User>, private val viewModel: NyangmalViewModel) : RecyclerView.Adapter<FriendsAdapter.friendsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): friendsViewHolder {
        val binding = ListFriendsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return friendsViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: friendsViewHolder, position: Int) {
        holder.bind(friends[position], context)
    }

    override fun getItemCount(): Int = friends.size

    fun updateFriendsList(friends: ArrayList<User>) {
        this.friends.clear()
        this.friends.addAll(friends)
        notifyDataSetChanged()
    }

    class friendsViewHolder(val binding: ListFriendsBinding, private val viewModel: NyangmalViewModel) : RecyclerView.ViewHolder(binding.root) {
        fun bind(friend: User, context: Context) {
            binding.txtFriendName.text = friend.name
            Glide.with(context)
                .load(friend.profileImageUrl)
                .placeholder(R.drawable.defaultprofpic)
                .into(binding.imgFriendProfPic)

            binding.imgFriendProfPic.setOnClickListener {
                showFriendActionDialog(context, friend)
            }
        }

        private fun showFriendActionDialog(context: Context, friend: User) {
            val dialogBinding = DialogFriendactionBinding.inflate(LayoutInflater.from(context))
            val dialog = Dialog(context).apply {
                setContentView(dialogBinding.root)
                dialogBinding.txtFriendName.text = "${friend.name}와..."
                dialogBinding.btnTalk.setOnClickListener {
                    dismiss()
                    talkWithFriend(context, friend)
                }
                dialogBinding.btnSendNyangmal.setOnClickListener {
                    dismiss()
                    showSendNyangmalDialog(context, friend)
                }
            }
            dialog.show()
        }

        private fun talkWithFriend(context: Context, friend: User) {
            val intent = Intent(context, ChatActivity::class.java).apply {
                putExtra("uid", friend.uID)
            }
            context.startActivity(intent)
        }

        private fun showSendNyangmalDialog(context: Context, friend: User) {
            val dialogBinding = DialogSendnyangmalBinding.inflate(LayoutInflater.from(context))
            val dialog = Dialog(context).apply {
                setContentView(dialogBinding.root)
                dialogBinding.txtSendTo.text = "${friend.name}에게..."
                dialogBinding.btnConfirm.setOnClickListener {
                    dismiss()
                    val nyangText = dialogBinding.txtSendNyangmal.text.toString()
                    viewModel.setReceiveUid(friend.uID)
                    viewModel.addNyangmal(nyangText)
                    dialogBinding.txtSendNyangmal.setText("")
                }
            }
            dialog.show()
        }
    }
}