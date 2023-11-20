package kr.kau.nyangmal3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.kau.nyangmal3.databinding.ItemSnowBinding
import java.util.concurrent.TimeUnit


//얘가 아이템뷰 객체들에게 바인딩해줘야함 아이템걍받아옴 전에만든데이터리스트를매개변수로받아와야함
//어댑터라는 클래스의 상속을 받아야함  뷰홀더도넣어줘야함
class SnowAdapter(private val context: SnowFragment) : RecyclerView.Adapter<SnowAdapter.SnowViewHolder>() {

    private var snowList: MutableList<SnowItem> = mutableListOf()
    fun setListData(data: MutableList<SnowItem>) {
        snowList.clear()
        snowList.addAll(data)
        notifyDataSetChanged()
        //snowList = data 이렇게 하면 ui변경알림안줘서 제대로 업데이트안될수도. 위처럼 고치셈
    }
    // 뷰홀더를 생성해줄때 호출되는 함수-> 처음에 생성될떄 몇번호출하고맘.온바인드뷰홀더랑다르게.
    // 여기서 아이템 뷰객체를만든뒤에 재활ㅇ용할라고 뷰홀더에 던져줌.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SnowViewHolder {
        //사용하고자하는아이템뷰객체만든뒤                그런데 이 매개변수들이 엉 그건 니가 찾아봐라
        val binding: ItemSnowBinding = ItemSnowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
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