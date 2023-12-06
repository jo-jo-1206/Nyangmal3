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

    private var messageList = mutableListOf<CMessageData>()
    fun setListData(data: MutableList<CMessageData>){
        messageList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
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
                ReceiveImageViewHolder(binding)
            }
            else -> {throw IllegalArgumentException("Invalid view type: $viewType")}
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]

        when (holder) {
            is SendViewHolder -> holder.sendBind(currentMessage)
            is SendImageViewHolder -> holder.sendImageBind(currentMessage)
            is ReceiveViewHolder -> holder.receiveBind(currentMessage)
            is ReceiveImageViewHolder -> holder.receiveImageBind(currentMessage)
        }
    }

    override fun getItemCount():Int = messageList.size

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]

        return when {
            currentMessage.message?.isBlank() == true && FirebaseAuth.getInstance().currentUser?.uid == currentMessage.sendId -> sendImage
            currentMessage.message?.isNotBlank() == true && FirebaseAuth.getInstance().currentUser?.uid == currentMessage.sendId -> send
            currentMessage.message?.isBlank() == true && FirebaseAuth.getInstance().currentUser?.uid != currentMessage.sendId -> receiveImage
            currentMessage.message?.isNotBlank() == true && FirebaseAuth.getInstance().currentUser?.uid != currentMessage.sendId -> receive
            else -> {
                throw IllegalArgumentException("Invalid message type")
            }
        }
    }

    class SendViewHolder(private val binding: SendBinding) : RecyclerView.ViewHolder(binding.root){
        fun sendBind(message: CMessageData?){
            message?.let {
                binding.sendMessage.text = it.message
                binding.txtDate.text = message.sendTime
            }
        }
    }

    class SendImageViewHolder(private val binding: SendImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun sendImageBind(message: CMessageData?) {
            message?.let {
                binding.txtDate.text = message.sendTime
                if (it.image?.isNotBlank() == true) {
                Glide.with(binding.imgSend.context)
                    .load(it.image)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(binding.imgSend)
                }
                Log.d("ImageUrlDebug", "Image URL: ${it.image}")
            }
        }
    }

    // 메세지 받는 쪽의 뷰홀더
    class ReceiveViewHolder(private val binding: ReceiveBinding) : RecyclerView.ViewHolder(binding.root){
        fun receiveBind(message: CMessageData?) {
            message?.let {
                binding.receiveMessage.text = it.message
                binding.txtDate.text = message.sendTime
            }
        }
    }

    class ReceiveImageViewHolder(private val binding: ReceiveImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun receiveImageBind(message: CMessageData?) {
            message?.let {
                binding.txtDate.text = message.sendTime
                if (it.image?.isNotBlank() == true) {
                    Glide.with(binding.imgReceive.context)
                        .load(it.image)
                        .placeholder(R.drawable.placeholder_image)
                        .error(R.drawable.error_image)
                        .into(binding.imgReceive)
                }
                Log.d("ImageUrlDebug", "Image URL: ${it.image}")            }
        }
    }
}


