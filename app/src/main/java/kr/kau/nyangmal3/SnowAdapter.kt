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

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.kau.nyangmal3.databinding.ItemSnowBinding
import java.util.concurrent.TimeUnit


//완벽함 ㄱㄱ
//얘가 아이템뷰 객체들에게 바인딩해줘야함 아이템걍받아옴 전에만든데이터리스트를매개변수로받아와야함
//어댑터라는 클래스의 상속을 받아야함  뷰홀더도넣어줘야함
class SnowAdapter(private val context: Context?) : RecyclerView.Adapter<SnowAdapter.SnowViewHolder>() {

    private var snowList: MutableList<SnowItem> = mutableListOf()
    fun setListData(data: List<SnowItem>) {
//        snowList.clear()
//        snowList.addAll(data)
//        notifyDataSetChanged()
        snowList = data as MutableList<SnowItem>
    }
    // 뷰홀더를 생성해줄때 호출되는 함수-> 처음에 생성될떄 몇번호출하고맘.온바인드뷰홀더랑다르게.
    // 여기서 아이템 뷰객체를만든뒤에 재활ㅇ용할라고 뷰홀더에 던져줌.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SnowViewHolder {
        //사용하고자하는아이템뷰객체만든뒤                그런데 이 매개변수들이 엉 그건 니가 찾아봐라
        val binding: ItemSnowBinding = ItemSnowBinding.inflate(LayoutInflater.from(context), parent, false)
        //뷰홀더에 던져주기
        return  SnowViewHolder(binding)
    }

    // 뷰홀더에 데이터를 바인딩해줘야 할때마다 호출되는 함수 => 사용자가화면위아래로스크롤할때마다 엄청나게 많이 호출
    // 얘는 매개변수로 뷰홀더랑 포지션(리사이클러뷰에서는 인덱스아이디)이 들어오지?
    // 따라서 받아온 뷰홀더에 바인딩을 해주기위해 앨범리스트에서 해당포지션인 데이터를 뷰홀더에 방금만든바인드에던져주기
    override fun onBindViewHolder(holder: SnowViewHolder, position: Int) {
        holder.bind(snowList[position]) //이러면 아래 바인드함수가 받아온데이터를객체에 넣어줌
    }

    // 데이터 세트 크기를 알려주는 함수 => 리사이클러뷰가 마지막이 언제인지를 알게 된다.
    override fun getItemCount(): Int = snowList.size

    //아이템뷰객체들이 날아가지않도록 잡고있는 아이
    //그럼 매개변수로 아이템뷰객체를 받아야겠지? >그래서 아이템앨범객체를 받아온것임.
    //그리고 뷰홀더 상속받을때 바인딩루트를 어 걍 저렇게 한다고만 알아둬라
    class SnowViewHolder(val binding: ItemSnowBinding): RecyclerView.ViewHolder(binding.root) {
        //이 바인드함수가 받아온데이터를 아이템뷰객체에넣어줌.
        fun bind(snowList: SnowItem?){
            snowList?.let {
                binding.userNameTv.text = it.userName
                binding.postTextTv.text = it.postText
                binding.timestampTv.text = getElapsedTime(it.timestamp)
                // Firebase 스토리지의 이미지 URL을 사용하여 이미지 로드
                /*Glide.with(binding.root)
                    .load(it.firebaseStorageImageUrl)
                    // snowItem.firebaseStorageImageUrl은 Firebase 스토리지의 이미지 URL
                    .into(binding.postImageIv)*/
            }
        }
        private fun getElapsedTime(timestampInMillis: Long): String {
            val elapsedMillis = System.currentTimeMillis() - timestampInMillis
            val hours = TimeUnit.MILLISECONDS.toHours(elapsedMillis)
            return "$hours hours ago"
        }
    }
}
/*
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
*/

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