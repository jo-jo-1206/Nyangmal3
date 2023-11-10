package kr.kau.nyangmal3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import kr.kau.nyangmal3.databinding.ReceiveBinding
import kr.kau.nyangmal3.databinding.SendBinding
import org.w3c.dom.Text

class CMessageAdapter(private val context: android.content.Context,):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var messageList = mutableListOf<CMessageData>()
    fun setListData(data: MutableList<CMessageData>){
        messageList = data
    }

    // recylerview가 viewholder를 새로만들어야 할 때마다 메소드 호출
    // 뷰홀더와 뷰를 생성하고 초기화
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SendViewHolder {
        val binding = SendBinding.inflate(LayoutInflater.from(context))
        return SendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]
        if(holder.javaClass == SendViewHolder::class.java){
            val viewHolder = holder as SendViewHolder
            viewHolder.bind(currentMessage)
        }
        else{
            val viewHolder = holder as ReceiveViewHolder
            viewHolder.bind2(currentMessage)
        }
    }

    // 아이템의 갯수 반환 ex) 주소록의 총 주소 개수
    override fun getItemCount()=messageList.size

    class SendViewHolder(private val binding: SendBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(messageList: CMessageData){
            binding.sendMessage.text = messageList.message
        }
    }

    class ReceiveViewHolder(private val binding: ReceiveBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind2(messagelist: CMessageData){
            binding.receiveMeassge.text = messagelist.message
        }
    }
}