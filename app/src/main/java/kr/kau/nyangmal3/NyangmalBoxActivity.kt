package kr.kau.nyangmal3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kr.kau.nyangmal3.databinding.ActivityNyangmalBoxBinding
import kr.kau.nyangmal3.viewmodel.NyangmalViewModel

//냥말박스: 익명편지함 냥말오면 쌓이고? 읽고 삭제 가능, 냥말보내기는 친구리스트에서 구현?(팝업), 50자 정도.
//메모장 비슷하게??? db는 나중에 한다치고, 채팅이랑 비슷한가???
class NyangmalBoxActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNyangmalBoxBinding
    private lateinit var adapter: NyangmalAdapter
    private val viewModel: NyangmalViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNyangmalBoxBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 어댑터
        adapter = NyangmalAdapter(this) { position ->
            // 클릭된 위치에 대한 삭제 처리

            viewModel.deleteNynagmal(adapter.getItemAt(position))
            adapter.removeItem(position)
        }
        binding.recyclerNyangmals.layoutManager = LinearLayoutManager(this)
        binding.recyclerNyangmals.adapter = adapter

        // 뷰 모델에서 채팅데이터 가져와서 어댑터에 보여줘라
        viewModel.fetchData().observe(this, Observer {newData ->
            adapter.setListData(newData)
            binding.recyclerNyangmals.scrollToPosition(adapter.itemCount - 1)
            adapter.notifyDataSetChanged()
        })
        //전송버튼눌렀을때 파이어베이스로 올리는 코드는 친구리스트에서 함

        //backib눌렀을때 다시 홈액티비티로 전환됨.
        binding.backIb.setOnClickListener {
            val nextintent = Intent(this, HomeActivity::class.java)
            startActivity(nextintent)
        }

        setupNyangmalAdapter()

    }

    private fun setupNyangmalAdapter() {
        binding.recyclerNyangmals.adapter = adapter
    }

    //상태변화에 따라 달라지는 작업은 여기서 해야하나??? 숫자 카운트되는거
    //편지 쌓이는 것은 어디에서 작업하지?
    override fun onResume() {
        super.onResume()
    }
}