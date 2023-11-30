package kr.kau.nyangmal3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.kau.nyangmal3.databinding.ItemNyangBinding

//ㅇㅋ
class NyangmalAdapter(private val context: android.content.Context, private val onDeleteClick: (Int) -> Unit):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var nyangmalList = mutableListOf<NyangmalItem>()
    fun setListData(data: MutableList<NyangmalItem>){
        nyangmalList = data
    }

    // recylerview가 viewholder를 새로만들어야 할 때마다 메소드 호출
    // 뷰홀더와 뷰를 생성하고 초기화
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NyangmalViewHolder {
        val binding = ItemNyangBinding.inflate(LayoutInflater.from(context),parent,false)
        return NyangmalViewHolder(binding)
    }

    // 뷰홀더에 데이터를 바인딩해줘야 할때마다 호출되는 함수 => 사용자가화면위아래로스크롤할때마다 엄청나게 많이 호출
    // 얘는 매개변수로 뷰홀더랑 포지션(리사이클러뷰에서는 인덱스아이디)이 들어오지?
    // 따라서 받아온 뷰홀더에 바인딩을 해주기위해 리스트에서 해당포지션인 데이터를 뷰홀더에 방금만든바인드에던져주기
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val nyangHolder = holder as NyangmalViewHolder
        nyangHolder.bind(nyangmalList[position], onDeleteClick)
    }

    // 아이템의 갯수 반환 ex) 주소록의 총 주소 개수
    override fun getItemCount():Int = nyangmalList.size

    //아이템뷰객체들이 날아가지않도록 잡고있는 아이
    //그럼 매개변수로 아이템뷰객체를 받아야겠지? >그래서 아이템앨범객체를 받아온것임.
    //그리고 뷰홀더 상속받을때 바인딩루트를 어 걍 저렇게 한다고만 알아둬라
    /*
    class NyangmalViewHolder(private val binding: ItemNyangBinding) :
        RecyclerView.ViewHolder(binding.root) {
        //이 바인드함수가 받아온데이터를 아이템뷰객체에넣어줌.
        init {
            // 아이템 뷰에 클릭 리스너를 설정합니다.
            binding.deleteIb.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // RecyclerView.NO_POSITION은 유효하지 않은 위치를 나타내는 상수입니다.
                    // 여기에서는 실제 아이템을 삭제하는 기능을 호출할 수 있습니다.
                    // 예를 들어 뷰모델을 통해 삭제 기능을 호출할 수 있습니다.
                    // viewModel.deleteNyangmalItem(nyangmalList[position])
                    onDeleteClick(position)
                }
            }
        }
        fun bind(nyangmalList: NyangmalItem?) {
            nyangmalList?.let {
                binding.messageTv.text = it.nyangText
            }
        }
    }

     */
    class NyangmalViewHolder(private val binding: ItemNyangBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(nyangmalList: NyangmalItem?, onDeleteClick: (Int) -> Unit) {
            nyangmalList?.let {
                binding.messageTv.text = it.nyangText
                binding.deleteIb.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onDeleteClick(position) // 클릭 이벤트 처리 콜백 호출
                    }
                }
            }
        }
    }
    //뷰객체 삭제
    fun removeItem(position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            nyangmalList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun getItemAt(position: Int): NyangmalItem {
        return nyangmalList[position]
    }
}