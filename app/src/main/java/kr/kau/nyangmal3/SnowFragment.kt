package kr.kau.nyangmal3

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kr.kau.nyangmal3.ViewModel.UserInfoViewModel
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
    private lateinit var adapter: SnowAdapter

    private val viewModelS: SnowViewModel by viewModels()
    private val viewModelU: UserInfoViewModel by viewModels()

    // Toast 메시지를 보여주는 함수
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

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

        // 24시간 이전의 timestamp를 가져오기

        val twentyFourHoursAgo = System.currentTimeMillis() - (24 * 60 * 60 * 1000)

        viewModelS.fetchData().observe(viewLifecycleOwner) { snowItems ->
            val itemsToRemove = mutableListOf<SnowItem>()

            snowItems.forEach { snowItem ->
                if (snowItem.timestamp < twentyFourHoursAgo) {
                    itemsToRemove.add(snowItem)
                }
            }

            itemsToRemove.forEach { snowItem ->
                viewModelS.deleteSnow(snowItem)
            }

            // 업데이트된 목록으로 UI 갱신
            val updatedList = snowItems - itemsToRemove
            adapter.setListData(updatedList.toMutableList())
            adapter.notifyDataSetChanged()
        }


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
        viewModelS.fetchData().observe(viewLifecycleOwner, Observer {
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
            binding?.recyclerSnows?.scrollToPosition(adapter.itemCount - 1)
        })


        binding?.snowimageIb?.setOnClickListener {
            // 이미지 선택 작업 실행
            pickImage()
        }

        //스노우애드 클릭하면 포스트 업데이트될것임.
        binding?.snowaddIb?.setOnClickListener {
            val snowText = binding!!.snowtextEt.text.toString()
            val currentTime = System.currentTimeMillis()
            viewModelU.fetchMyName()
            var isNameFetched = false // 플래그 설정
            val imageUri = viewModelS.getSelectedImageUri()

            // 이미지가 선택되지 않았다면 경고 메시지 표시
            if (imageUri == null) {
                showToast("이미지를 선택하세요.")
            } else {
                // 이미지 URL을 가져와서 데이터베이스에 업로드
                //val imageUrl = imageUri.toString()
                viewModelU.myName.observe(viewLifecycleOwner) { userName ->
                    userName?.let {
                        if (!isNameFetched) { // 플래그를 확인하여 한 번만 처리
                            viewModelS.addImage(imageUri)
                            viewModelS.addSnow(it, snowText, currentTime, imageUri)
                            binding!!.snowtextEt.setText("") // 설명 전송하면 다시 텍스트 칸 초기화해주기
                            binding!!.snowimageIb.setImageResource(R.drawable.image_snow)
                            isNameFetched = true // 플래그 변경
                        }
                    }
                }
            }
        }

//        binding?.snowaddIb?.setOnClickListener {
//            val snowText = binding!!.snowtextEt.text.toString()
//            val currentTime = System.currentTimeMillis()
//
//            // 이미 이름을 가져왔는지 확인하는 LiveData
//            val nameFetchedObserver = Observer<String> { userName ->
//                userName?.let {
//                    viewModelS.addSnow(it, snowText, currentTime)
//                    binding!!.snowtextEt.setText("") // 설명 전송하면 다시 텍스트 칸 초기화
//                    // 관찰이 끝났으므로 관찰 해제
//                    viewModelU.myName.removeObserver(this)
//                }
//            }
//
//            // 이름을 가져오기 위한 ViewModel의 메서드 호출
//            viewModelU.fetchMyName()
//
//            // 이미 이름을 가져왔는지를 관찰하는 Observer를 등록
//            viewModelU.myName.observe(viewLifecycleOwner, nameFetchedObserver)
//        }

        //이미지 업로드 클릭하면 선택한이미지를 파이어베이스에 업로드하고 이미지선택을위해 갤러리 인텐트 호출해줘야함
        //binding?.snowimageIb?.setOnClickListener {}
    }
    // 이미지 선택을 위한 함수
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            // ViewModel에 선택된 이미지 URI 전달
            viewModelS.setSelectedImageUri(uri)
            // 이미지 버튼 업데이트
            binding?.snowimageIb?.setImageURI(uri)
        } ?: showToast("이미지 선택이 취소되었습니다.")
    }
    private fun pickImage() {
//        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
//            uri?.let {
//                // ViewModel에 선택된 이미지 URI 전달
//                viewModelS.setSelectedImageUri(uri)
//                // 이미지 버튼 업데이트
//                binding?.snowimageIb?.setImageURI(uri)
//            } ?: showToast("이미지 선택이 취소되었습니다.")
//        }
        getContent.launch("image/*")
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
