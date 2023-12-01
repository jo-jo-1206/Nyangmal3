package kr.kau.nyangmal3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.kau.nyangmal3.ViewModel.MonologueViewModel
import kr.kau.nyangmal3.databinding.ListFriendsBinding
import kr.kau.nyangmal3.databinding.MonologueBinding

class MonologueAdapter : RecyclerView.Adapter<MonologueAdapter.messageViewHolder>() {

    private var messages = mutableListOf<MonologueMessage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): messageViewHolder {
        val binding = MonologueBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return messageViewHolder(binding)
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: messageViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    fun setListData(data: List<MonologueMessage>) {
        messages.clear()
        messages.addAll(data)
        notifyDataSetChanged()
    }

    class messageViewHolder(private val binding: MonologueBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: MonologueMessage) {
            binding.txtMonologue.text = message.message
            binding.txtDate.text = message.timestamp
        }
    }
}
