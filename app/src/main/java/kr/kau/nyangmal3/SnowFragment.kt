package kr.kau.nyangmal3

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kr.kau.nyangmal3.databinding.FragmentSnowBinding
import kr.kau.nyangmal3.viewmodel.SnowViewModel

class SnowFragment : Fragment() {


    lateinit var snowActivity: Activity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        snowActivity = context as Activity
    }
    //ㄴ fragment는 context상속안받아져있어서 필요한거 아래에서 쓸라고 이렇게 해줌


    var binding: FragmentSnowBinding? = null
    //lateinit var binding: FragmentSnowBinding
    private lateinit var adapter: SnowAdapter

    private val viewModel: SnowViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSnowBinding.inflate(layoutInflater)
        return binding?.root
    }

    //LiveData옵저빙, adapter세팅은 여기서
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //어댑터와 데이터리스트 연결
        //근데 챗은 context자리에 this썼는데
        //fragment는 context상속받지않아서 this쓸수없고,
        // 우에서 그거 해줌 context상속받기 그럼 this슬수잇음
        adapter = SnowAdapter(this)
        // 리사이클러뷰에 어댑터 연결 = 니가 사용해야할 어댑터는 이것이다. 라고 알려줌
        binding?.recyclerSnows?.adapter = adapter
        // 레이아웃 매니저 설정
        // 우린 세로로배치
        binding?.recyclerSnows?.layoutManager = LinearLayoutManager(requireContext())

        // LiveData 옵저빙 뷰모델의 snowData 라는 라이브데이터 옵저브
        // 관찰하다가(observe매서드) 변하면 ui도 변경 = 리사이클러뷰에 반영
        // 값이 변경될때마다 내부코드가 실행됨
        // Observe는 라이브데이터변경감지하다갑 변경되면 snowData라는 파라미터 받음
        // 처음에 데이터 한개도없으면 실행안되어야 맞는데 observe가
        viewModel.fetchData().observe(viewLifecycleOwner, Observer {
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
            binding?.recyclerSnows?.scrollToPosition(adapter.itemCount - 1)
        })

        //스노우애드 클릭하면 포스트 업데이트될것임.
        binding?.snowaddIb?.setOnClickListener {
            val snowText = binding!!.snowtextEt.text.toString()
            val currentTime = viewModel.getTime()
            //val snowData = SnowItem("글쓴이", snowText, 0, "이미지Url")
            val snowData = SnowItem(snowText, currentTime)
            viewModel.addSnow(snowData)
            binding!!.snowtextEt.setText("") // 설명 전송하면 다시 텍스트 칸 초기화해주기
        }
         //이미지 업로드 클릭하면 선택한이미지를 파이어베이스에 업로드하고 이미지선택을위해 갤러리 인텐트 호출해줘야함
        //binding?.snowimageIb?.setOnClickListener {}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
