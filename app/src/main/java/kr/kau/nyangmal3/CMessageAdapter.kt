package kr.kau.nyangmal3

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import kr.kau.nyangmal3.databinding.ReceiveBinding
import kr.kau.nyangmal3.databinding.SendBinding
import org.w3c.dom.Text

class CMessageAdapter(private val context: android.content.Context):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var messageList = mutableListOf<CMessageData>()
    fun setListData(data: MutableList<CMessageData>){
        messageList = data
    }

    // recylerview가 viewholder를 새로만들어야 할 때마다 메소드 호출
    // 뷰홀더와 뷰를 생성하고 초기화
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SendViewHolder {
        val binding = SendBinding.inflate(LayoutInflater.from(context),parent,false)
        return SendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]
        //val currentTime = currentMessage.send_time
        if(holder.javaClass == SendViewHolder::class.java){
            val viewHolder = holder as SendViewHolder
            viewHolder.bind(currentMessage)
            //(viewHolder.getDateText(currentTime)
        }
        else{
            val viewHolder = holder as ReceiveViewHolder
            viewHolder.bind2(currentMessage)
        }
    }

    // 아이템의 갯수 반환 ex) 주소록의 총 주소 개수
    override fun getItemCount():Int = messageList.size

    class SendViewHolder(private val binding: SendBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(messageList: CMessageData?){
            messageList?.let {
                binding.sendMessage.text = it.message
                val sendData = it.send_time
                val dateText = getDateText(sendData)
                binding.txtDate.text = dateText
            }
        }

        fun getDateText(sendData:String): String {
            var dateText =""
            var timeString = ""
            if(sendData.isNotBlank()){
                timeString = sendData.substring(8,12)
                var hour = timeString.substring(0, 2)
                var minute = timeString.substring(2, 4)

                var timeformat = "%02d:%02d"

                if (hour.toInt() > 11) {
                    dateText += "오후 "
                    dateText += timeformat.format(hour.toInt() - 12, minute.toInt())
                } else {
                    dateText += "오전 "
                    dateText += timeformat.format(hour.toInt(), minute.toInt())
                }
            }
            return dateText
            }
    }

    class ReceiveViewHolder(private val binding: ReceiveBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind2(messageList: CMessageData?) {
            messageList?.let {
                binding.receiveMessage.text = it.message

            }
        }
    }
    }


