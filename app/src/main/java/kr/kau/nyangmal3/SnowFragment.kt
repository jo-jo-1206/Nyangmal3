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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //어댑터와 데이터리스트 연결
        //근데 챗은 context자리에 this썼는데
        //fragment는 context상속받지않아서 this쓸수없고,
        // 우에서 그거 해줌 context상속받기
        adapter = SnowAdapter(this)
        // 리사이클러뷰에 어댑터 연결 = 니가 사용해야할 어댑터는 이것이다. 라고 알려줌
        binding?.recyclerSnows?.adapter = adapter
        // 레이아웃 매니저 설정
        // 우린 세로로배치
        binding?.recyclerSnows?.layoutManager = LinearLayoutManager(context)

//        viewModel.snowData.observe(viewLifecycleOwner, Observer {
//            adapter.setListData(it)
//            adapter.notifyDataSetChanged()
//            binding?.recyclerSnows?.scrollToPosition(adapter.itemCount - 1)
//        })
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
        /*viewModel.snowData.observe(viewLifecycleOwner, Observer { snowData ->
            // 이러면 snowData가 널이 아닌경우에만 실행되어야 정상아님????
            snowData?.let {
                // 어댑터에 snowData전달하고
                adapter.setListData(it) //it은 snowData 근데 라이브데이터에서 전달된 최신데이터니까 맨첨엔 당연히
                //널인게맞지않나 어쩌
                adapter.notifyDataSetChanged() //데이터변경을 알리기위해 호출되는 함수
                // 리사이클러뷰를 마지막아이템위치로 스크롤해줌
                binding?.recyclerSnows?.scrollToPosition(adapter.itemCount - 1)
            }
        })
         */
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
            val currentTime = viewModel.getTime()
            val snowData = SnowItem("글쓴이", snowText, currentTime, "이미지Url")
            viewModel.addSnow(snowData)
            binding!!.snowtextEt.setText("") // 설명 전송하면 다시 텍스트 칸 초기화해주기
        }
         //이미지 업로드 버튼 클릭 이벤트
//        binding?.snowimageIb?.setOnClickListener {
//            // 이미지 업로드 로직을 호출
//            // 선택한 이미지를 Firebase에 업로드하고, 성공하면 텍스트 업로드 로직을 호출
//            // 이미지 선택을 위한 갤러리 인텐트 호출
//
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
