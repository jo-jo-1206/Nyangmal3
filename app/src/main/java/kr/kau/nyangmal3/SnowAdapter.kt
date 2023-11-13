/*package kr.kau.nyangmal3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.kau.nyangmal3.databinding.ItemSnowBinding
import java.util.concurrent.TimeUnit

class SnowAdapter : RecyclerView.Adapter<SnowAdapter.SnowViewHolder>() {

    private var snowList: MutableList<SnowItem> = mutableListOf()

    fun setListData(data: List<SnowItem>) {
        snowList = data
    }

    class SnowViewHolder(private val binding: ItemSnowBinding) : RecyclerView.ViewHolder(binding.root) {
        val timestampTextView = binding.timestampTextView
        val postTextView = binding.postTextView
        val userNameTextView = binding.userNameTextView

        fun bind(snowItem: SnowItem) {
            userNameTextView.text = snowItem.userName
            postTextView.text = snowItem.postText
            timestampTextView.text = getElapsedTime(snowItem.timestamp)
        }

        private fun getElapsedTime(timestampInMillis: Long): String {
            val elapsedMillis = System.currentTimeMillis() - timestampInMillis
            val hours = TimeUnit.MILLISECONDS.toHours(elapsedMillis)
            return "$hours hours ago"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SnowViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSnowBinding.inflate(inflater, parent, false)
        return SnowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SnowViewHolder, position: Int) {
        val currentItem = snowList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = snowList.size
}*/
package kr.kau.nyangmal3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.kau.nyangmal3.databinding.ItemSnowBinding
import java.util.concurrent.TimeUnit

class SnowAdapter : RecyclerView.Adapter<SnowAdapter.SnowViewHolder>() {

    private var snowList: MutableList<SnowItem> = mutableListOf()

    fun setListData(data: List<SnowItem>) {
        snowList.clear()
        snowList.addAll(data)
        notifyDataSetChanged()
    }

    class SnowViewHolder(private val binding: ItemSnowBinding) : RecyclerView.ViewHolder(binding.root) {
        val timestampTextView = binding.timestampTextView
        val postTextView = binding.postTextView
        val userNameTextView = binding.userNameTextView

        fun bind(snowItem: SnowItem) {
            userNameTextView.text = snowItem.userName
            postTextView.text = snowItem.postText
            timestampTextView.text = getElapsedTime(snowItem.timestamp)
        }

        private fun getElapsedTime(timestampInMillis: Long): String {
            val elapsedMillis = System.currentTimeMillis() - timestampInMillis
            val hours = TimeUnit.MILLISECONDS.toHours(elapsedMillis)
            return "$hours hours ago"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SnowViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSnowBinding.inflate(inflater, parent, false)
        return SnowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SnowViewHolder, position: Int) {
        val currentItem = snowList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = snowList.size
}


/*package kr.kau.nyangmal3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.kau.nyangmal3.databinding.FragmentSnowBinding
import kr.kau.nyangmal3.databinding.ItemSnowBinding
import kr.kau.nyangmal3.databinding.SendBinding
import java.util.concurrent.TimeUnit
import kotlin.coroutines.jvm.internal.CompletedContinuation.context

private var Any.text: String
    get() {
        TODO("Not yet implemented")
    }
    set() = Unit

class SnowAdapter() :
    RecyclerView.Adapter<SnowAdapter.SnowViewHolder>() {

    private var snowList: MutableList<SnowItem> = mutableListOf<SnowItem>()
    fun setListData(data: MutableList<SnowItem>){
        snowList = data
    }

    class SnowViewHolder(private val binding: ItemSnowBinding) : RecyclerView.ViewHolder(binding.root) {
        val timestampTextView: Any
            get() {
                TODO()
            }
        val postTextView: Any
            get() {
                TODO()
            }
        val userNameTextView: Any
            get() {
                TODO()
            }

        fun bind(snowList: SnowItem){
            binding.userNameTextView.text = snowList.userName
            binding.postTextView.text = snowList.postText
            binding.timestampTextView.text = snowList.timestamp.toString() //원래Long인데 ㅇㅇ
        }
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): SnowViewHolder {
        val binding = ItemSnowBinding.inflate(LayoutInflater.from(context))
        return SnowAdapter.SnowViewHolder(binding)
    }

    //뷰홀더가 각 아이템에대한 뷰를 바인딩하는것
    //데이터를 뷰홀ㄹ더에 ㄱㄱ함
    override fun onBindViewHolder(holder: SnowViewHolder, position: Int) {
        val currentItem = snowList[position]
        //ㄴ리사이클러 뷰에 표시될 데이터의 리스트. 현재 아이템 위치에 해당하는 데이터를 가져옴.

        holder.userNameTextView.text = currentItem.userName //현재아이템에서 사용자이름가져와 텍스트뷰에설정
        holder.postTextView.text = currentItem.postText //현재아이템에서 포스트텍스트가져와 텍스트뷰에 설정

        // Format timestamp to display time elapsed
        //이건뭔 잘 다시 잘 보고 잘 공부해보셈
        val timestampInMillis = currentItem.timestamp //현재아이템의 타임스템프
        val elapsedMillis = System.currentTimeMillis() - timestampInMillis //현재시간과 타임스탬프간 경과된 밀리초계산
        val hours = TimeUnit.MILLISECONDS.toHours(elapsedMillis) //경과된 밀리초를 시간으로 변환
        holder.timestampTextView.text = "$hours hours ago" //변환된시간을 저 문구와함께 표현함
    }

    //아이템 갯수반환
    override fun getItemCount() = snowList.size //이거 왜이럼;
} */