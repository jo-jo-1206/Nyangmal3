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
import com.google.firebase.auth.FirebaseAuth
import kr.kau.nyangmal3.databinding.ReceiveBinding
import kr.kau.nyangmal3.databinding.SendBinding
import org.w3c.dom.Text

class CMessageAdapter(private val context: android.content.Context):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //val myUid = FirebaseAuth.getInstance().currentUser?.uid.toString()
    private val send = 1
    private val receive = 2

    private var messageList = mutableListOf<CMessageData>() // 리스트에는 Message타입의 데이터가 들어감
    fun setListData(data: MutableList<CMessageData>){
        messageList = data
    }

    // recylerview가 viewholder를 새로만들어야 할 때마다 메소드 호출
    // 뷰홀더와 뷰를 생성하고 초기화
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // return if - else 문으로 할 떈 안됐는데 when()으로 하니깐 됨. 뭐지??
        return when(viewType){
            send ->{
                val binding = SendBinding.inflate(LayoutInflater.from(context),parent,false)
                Log.d("onCreate","send=1")
                Log.d("onCreate", "$viewType")
                SendViewHolder(binding)
            }
            receive -> {
                val binding = ReceiveBinding.inflate(LayoutInflater.from(context),parent,false)
                Log.d("onCreate","receive=2")
                Log.d("onCreate", "$viewType")
                ReceiveViewHolder(binding)
            }

            else -> {throw IllegalArgumentException("Invalid view type: $viewType")}
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]

        if(holder.javaClass == SendViewHolder::class.java){ // 보내는 데이터
            val viewHolder = holder as SendViewHolder
            Log.d("onBindViewHolder","sendView")
            viewHolder.sendBind(currentMessage)
        }
        else{ // 받는 데이터
            val viewHolder = holder as ReceiveViewHolder
            Log.d("onBindViewHolder","receiveView")
            viewHolder.receiveBind(currentMessage)
        }

    }

    // 아이템의 갯수 반환 ex) 주소록의 총 주소 개수
    override fun getItemCount():Int = messageList.size

    // 어떤 뷰홀더를 사용할 지 결정 함 -> 현재 접속자 아이디와 sendId가 같으면 sendViewHolder 사용
    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        //return if(messageList[position].sendId.equals(myUid)){
        return if(FirebaseAuth.getInstance().currentUser?.uid == currentMessage.sendId){
            send
        }else{
            receive
        }
    }

    // 메세지를 보내는 쪽의 뷰홀더
    class SendViewHolder(private val binding: SendBinding) : RecyclerView.ViewHolder(binding.root){
        fun sendBind(messageList: CMessageData?){
            messageList?.let {
                binding.sendMessage.text = it.message
                val sendData = it.send_time
                val dateText = getDateText(sendData)
                binding.txtDate.text = dateText
            }
        }

        // 시간 포맷 예뿌게
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
    }


