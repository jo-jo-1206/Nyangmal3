package kr.kau.nyangmal3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kr.kau.nyangmal3.databinding.FragmentSnowBinding
import kr.kau.nyangmal3.viewmodel.SnowViewModel

//냥: 펑/스토리 ~24시간뒤면 사라짐. 한번읽으면사라짐?, 모든 사람들의 상태메시지모음화면느낌
class SnowFragment : Fragment() {

    var binding: FragmentSnowBinding?=null
    private lateinit var adapter: SnowAdapter
    val viewModel: SnowViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSnowBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SnowAdapter(this)
        binding.recyclerSnows.layoutManager = LinearLayoutManager(this) //쌓이는형태
        binding.recyclerSnows.adapter = adapter
        observerData()
        // 뷰모델 초기화
        //viewModel = ViewModelProvider(this, SnowViewModelFactory(repository)).get(SnowViewModel::class.java)

        // LiveData를 관찰하고 UI 업데이트
        viewModel.snowData.observe(viewLifecycleOwner){
            /*binding?.snowpicB.
            binding?.snowtextB
            binding?.snowaddIb 여기는 후에 수정*/
        }
//        snowViewModel.snowData.observe(viewLifecycleOwner, Observer { snowData ->
//            // RecyclerView 등을 사용하여 데이터를 UI에 표시
//        })
        //이건챗에서 같은내용아래처럼햇는데 걍 ㄱㄱ
        fun observerData() {
            viewModel.fetchData().observe(this, Observer {
                adapter.setListData(it)
                adapter.notifyDataSetChanged()
            }
            )
        }

        // 이미지 업로드 버튼 클릭 이벤트
        binding?.snowimageIb?.setOnClickListener {
            // 이미지 업로드 로직을 호출
            // 선택한 이미지를 Firebase에 업로드하고, 성공하면 텍스트 업로드 로직을 호출
        }

        // 텍스트 업로드 버튼 클릭 이벤트
        //전송버튼눌렀을때 텍스트도 업로드되면서 이미지 하...
        binding?.snowaddIb?.setOnClickListener {
            // 텍스트 업로드 로직을 호출
            val snowText = binding!!.snowtextEt.text.toString()
            val snowData = SnowItem(snowText)
            viewModel.addSnow(snowData)
            binding!!.snowtextEt.setText("") //설명전송하면 다시 텍스트칸 초기화해주기

        }

        // 데이터 가져오기 및 RecyclerView 업데이트
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding=null
    }
}



//class SnowFragment : Fragment() {
//
//    // Firebase
//    private lateinit var databaseReference: DatabaseReference
//    private lateinit var storageReference: StorageReference
//
//    // ViewBinding 변수 추가
//    private var _binding: FragmentSnowBinding? = null
//    private val binding get() = _binding!!
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = FragmentSnowBinding.inflate(inflater, container, false)
//
//        // Firebase 초기화
//        databaseReference = FirebaseDatabase.getInstance().reference.child("uploads")
//        storageReference = FirebaseStorage.getInstance().reference.child("uploads")
//
//        // Set up RecyclerView
//        val layoutManager = LinearLayoutManager(activity)
//        binding.recyclerSnow.layoutManager = layoutManager
//
//        // Set up click listeners
//        binding.snowpicB.setOnClickListener { pickImageFromGallery() }
//        binding.snowtextB.setOnClickListener { uploadText() }
//        binding.snowaddIb.setOnClickListener { uploadData() }
//
//        return binding.root
//    }
//
//    // 이미지 선택
//    private fun pickImageFromGallery() {
//        // 이미지 선택 로직 추가
//    }
//
//    // 텍스트 업로드
//    private fun uploadText() {
//        val text = binding.snowtextEt.text.toString()
//        // Firebase에 텍스트 업로드 로직 추가
//    }
//
//    // 데이터 업로드
//    private fun uploadData() {
//        // 이미지 및 텍스트 Firebase에 업로드 로직 추가
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}
/////////////////////////////////////
//class SnowFragment : Fragment() {

//    private lateinit var binding: FragmentSnowBinding
//    private lateinit var snowAdapter: SnowAdapter // Assuming you have a custom adapter for your RecyclerView
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentSnowBinding.inflate(inflater,container,false)
//        return binding.root
//    }
    /////////////////////////////////////////////////////////////////
//    private val GALLERY_CODE = 10
//    private lateinit var photo: ImageView
//    private lateinit var storage: FirebaseStorage
//    private lateinit var binding: FragmentSnowBinding
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentSnowBinding.inflate(inflater,container,false)
//        binding
//        return super.onCreateView(inflater, container, savedInstanceState)
//    }
    ////////////////////////////////////////
//    private var _binding: FragmentSnowBinding? = null
//    private var __binding: SnowStoryBinding? = null
//    private val binding get() = _binding!!
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        _binding = FragmentSnowBinding.inflate(inflater, container, false)
//        __binding = SnowStoryBinding.inflate(inflater,container,false)
//
//        binding.addsnowIv.setOnClickListener {
//            showDialog()
//        }
//        return binding.root
//    }
//
//    private fun showDialog(){
//        val dialogBinding = FragmentSnowDialogBinding.inflate(layoutInflater)
//
//        val dialog = AlertDialog.Builder(requireContext()).setView(dialogBinding.root).create()
//
//       dialogBinding.snowpicB.setOnClickListener {
//           editPicture()
//           dialog.dismiss()
//       }
//
//        dialogBinding.snowtextB.setOnClickListener {
//            editText()
//            dialog.dismiss()
//        }
//    }
//
//    private fun editText(){
//        val dialogBinding = FragmentSnowDialogBinding.inflate(layoutInflater)
//
//        val dialog = android.app.AlertDialog.Builder(requireContext())
//            .setView(dialogBinding.root)
//            .create()
//
//        // 확인 버튼을 눌렀을 때
//        dialogBinding.snowtextB.setOnClickListener {
//            // 사용자가 입력한 새로운 이름을 가져옴
//            val newName = dialogBinding.snowtextEt.text.toString()
//
//            __binding?.textTv?.setText(newName)
//            dialog.dismiss()
//        }
//        dialog.show()
//    }
//
//    private fun editPicture() {
//        // 갤러리 어플 실행
//        val intent = Intent(Intent.ACTION_PICK) // 사용자가 데이터를 선택하고
//        intent.type = "image/*"
//
//        val builder = android.app.AlertDialog.Builder(requireContext())
//        builder.setTitle("사진 편집창").setMessage("사진을 편집합니다")
//        builder.create().show()
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (resultCode == Activity.RESULT_OK) {
//            when(requestCode) {
//                105 -> {
//                    var uri = data?.data
//                    __binding?.imageIv?.setImageURI(uri)
//                }
//            }
//        }
//    }
//
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//        __binding = null
//    }

//}