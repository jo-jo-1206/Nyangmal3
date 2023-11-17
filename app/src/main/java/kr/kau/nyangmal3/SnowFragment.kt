package kr.kau.nyangmal3

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kr.kau.nyangmal3.databinding.FragmentSnowBinding
import kr.kau.nyangmal3.viewmodel.SnowViewModel

class SnowFragment : Fragment() {

    var binding: FragmentSnowBinding? = null
    //private lateinit var binding: FragmentSnowBinding
    private lateinit var adapter: SnowAdapter
    private val viewModel: SnowViewModel by viewModels()
//    private val viewModel: SnowViewModel by lazy {
//        ViewModelProvider(this).get(SnowViewModel::class.java)
//    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSnowBinding.inflate(layoutInflater)
        //val binding = FragmentSnowBinding.inflate(inflater, container, false)
        return binding?.root
    }

    //LiveData옵저빙, adapter세팅은 여기서
    /*


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //어댑터와 데이터리스트 연결
        adapter = SnowAdapter(context)
        // 리사이클러뷰에 어댑터 연결 = 니가 사용해야할 어댑터는 이것이다. 라고 알려줌
        binding?.recyclerSnows?.adapter = adapter
        // 레이아웃 매니저 설정
        // 우린 세로로배치
        binding?.recyclerSnows?.layoutManager = LinearLayoutManager(context)

        viewModel.snowData.observe(viewLifecycleOwner, Observer {
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
            binding?.recyclerSnows?.scrollToPosition(adapter.itemCount - 1)
        })
        /*
        viewModel.fetchData().observe(viewLifecycleOwner, Observer {
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
            binding?.recyclerSnows?.scrollToPosition(adapter.itemCount - 1)
        })
         */


        /*
        viewModel.snowData.observe(viewLifecycleOwner, Observer { snowData ->
            adapter.setListData(snowData)
            adapter.notifyDataSetChanged()
        })
        fun observerData() {
        viewModel.fetchData().observe(this, Observer {
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
            binding.recyclerMessages.scrollToPosition(adapter.itemCount - 1)
        })
    }
         */
        // 텍스트 업로드 버튼 클릭 이벤트
        binding?.snowaddIb?.setOnClickListener {
            // 텍스트 업로드 로직을 호출
            val snowText = binding!!.snowtextEt.text.toString()
            val snowData = SnowItem(snowText, snowText, 0, snowText) // 수정된 부분
            viewModel.addSnow(snowData)
            binding!!.snowtextEt.setText("") // 설명 전송하면 다시 텍스트 칸 초기화해주기
        }
         //이미지 업로드 버튼 클릭 이벤트
        binding?.snowimageIb?.setOnClickListener {
            // 이미지 업로드 로직을 호출
            // 선택한 이미지를 Firebase에 업로드하고, 성공하면 텍스트 업로드 로직을 호출
            // 이미지 선택을 위한 갤러리 인텐트 호출

        }
    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
