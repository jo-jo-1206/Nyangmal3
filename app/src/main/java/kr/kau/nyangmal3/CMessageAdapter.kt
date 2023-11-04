package kr.kau.nyangmal3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class CMessageAdapter(private val context: Context, val messageList:ArrayList<CMessageData>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    // viewholder를 새로 만들 때 호출되는 메서드 -> 레이아웃을 만든다
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.send, parent, false)
        return SendViewHolder(view)
    }

    // 아이템 개수
    override fun getItemCount(): Int {
        return messageList.size
    }

    // ViewHolder를 데이터와 연결할 때 호출되는 메서드.
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]
        val ViewHolder = holder as SendViewHolder
    }

    // View 담기?? -> 받는 쪽, 보내는 쪽 둘 다 있어야댐
    class SendViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val sendMessage: Text = itemView.findViewById(R.id.send_meassge)
    }

}