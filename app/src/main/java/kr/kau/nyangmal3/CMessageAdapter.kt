package kr.kau.nyangmal3

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import kr.kau.nyangmal3.databinding.ReceiveBinding
import kr.kau.nyangmal3.databinding.ReceiveImageBinding
import kr.kau.nyangmal3.databinding.SendBinding
import kr.kau.nyangmal3.databinding.SendImageBinding
import org.w3c.dom.Text

class CMessageAdapter(private val context: android.content.Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val send = 1
    private val receive = 2
    private val sendImage = 3
    private val receiveImage = 4

    private var messageList = mutableListOf<CMessageData>() // 리스트에는 Message타입의 데이터가 들어감
    fun setListData(data: MutableList<CMessageData>){
        messageList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // return if - else 문으로 할 떈 안됐는데 when()으로 하니깐 됨. 뭐지??
        return when(viewType){
            send ->{
                val binding = SendBinding.inflate(LayoutInflater.from(context),parent,false)
                SendViewHolder(binding)
            }
            receive -> {
                val binding = ReceiveBinding.inflate(LayoutInflater.from(context),parent,false)
                ReceiveViewHolder(binding)
            }
            sendImage -> {
                val binding = SendImageBinding.inflate(LayoutInflater.from(context), parent, false)
                SendImageViewHolder(binding)

            }
            receiveImage ->{
                val binding = ReceiveImageBinding.inflate(LayoutInflater.from(context), parent, false)
                Log.d("receiveImage","recieveImage")
                ReceiveImageViewHolder(binding)
            }
            else -> {throw IllegalArgumentException("Invalid view type: $viewType")}
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]

        if (holder.javaClass == SendViewHolder::class.java) { // 보내는 데이터
            val viewHolder = holder as SendViewHolder
            viewHolder.sendBind(currentMessage)

        } else if (holder.javaClass == SendImageViewHolder::class.java) { // 이미지를 보내는 데이터
            val viewHolder = holder as SendImageViewHolder
            viewHolder.sendImageBind(currentMessage)

        } else if (holder.javaClass == ReceiveViewHolder::class.java){ // 받는 데이터
            val viewHolder = holder as ReceiveViewHolder
            viewHolder.receiveBind(currentMessage)
        } else {
            val viewHolder = holder as ReceiveImageViewHolder
            viewHolder.receiveImageBind(currentMessage)
        }
    }

    override fun getItemCount():Int = messageList.size

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]

        return when {
            currentMessage.message!!.isBlank() && FirebaseAuth.getInstance().currentUser?.uid == currentMessage.sendId -> sendImage
            currentMessage.message!!.isNotBlank() && FirebaseAuth.getInstance().currentUser?.uid == currentMessage.sendId -> send
            currentMessage.message!!.isBlank()&& FirebaseAuth.getInstance().currentUser?.uid != currentMessage.sendId -> receiveImage
            currentMessage.message!!.isNotBlank()&& FirebaseAuth.getInstance().currentUser?.uid != currentMessage.sendId -> receive
            else -> {Log.d("failed","실패ㅕ")}
        }
    }

    class SendViewHolder(private val binding: SendBinding) : RecyclerView.ViewHolder(binding.root){
        fun sendBind(messageList: CMessageData?){
            messageList?.let {
                binding.sendMessage.text = it.message
                val sendData = it.sendTime
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

    class SendImageViewHolder(private val binding: SendImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun sendImageBind(message: CMessageData?) {
            message?.let {
                val sendData = it.sendTime
                val dateText = getDateText(sendData)
                binding.txtDate.text = dateText
                if (it.image?.isNotBlank() == true) {
                Glide.with(binding.imgSend.context)
                    .load(it.image)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(binding.imgSend)
                }
                Log.d("ImageUrlDebug", "Image URL: ${it.image}")            }
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

    // 메세지 받는 쪽의 뷰홀더
    class ReceiveViewHolder(private val binding: ReceiveBinding) : RecyclerView.ViewHolder(binding.root){
        fun receiveBind(messageList: CMessageData?) {
            messageList?.let {
                binding.receiveMessage.text = it.message
                val sendData = it.sendTime
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

    class ReceiveImageViewHolder(private val binding: ReceiveImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun receiveImageBind(message: CMessageData?) {
            message?.let {
                val sendData = it.sendTime
                val dateText = getDateText(sendData)
                binding.txtDate.text = dateText
                if (it.image?.isNotBlank() == true) {
                    Glide.with(binding.imgReceive.context)
                        .load(it.image)
                        .placeholder(R.drawable.placeholder_image)
                        .error(R.drawable.error_image)
                        .into(binding.imgReceive)
                }
                Log.d("ImageUrlDebug", "Image URL: ${it.image}")            }
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
}


