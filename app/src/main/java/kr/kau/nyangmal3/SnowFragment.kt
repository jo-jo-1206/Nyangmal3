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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kr.kau.nyangmal3.databinding.FragmentSnowBinding
import kr.kau.nyangmal3.viewmodel.SnowViewModel


class SnowFragment : Fragment() {

    var binding: FragmentSnowBinding? = null
    private lateinit var adapter: SnowAdapter
    private val viewModel: SnowViewModel by viewModels()

    // 이미지 업로드를 위한 상수
    private val GALLERY_CODE = 100
    private lateinit var storageReference: StorageReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSnowBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SnowAdapter()
        binding?.recyclerSnows?.layoutManager = LinearLayoutManager(context)
        binding?.recyclerSnows?.adapter = adapter

        viewModel.snowData.observe(viewLifecycleOwner, Observer { snowData ->
            adapter.setListData(snowData)
            adapter.notifyDataSetChanged()
        })

        // 이미지 업로드 버튼 클릭 이벤트
        binding?.snowimageIb?.setOnClickListener {
            // 이미지 업로드 로직을 호출
            // 선택한 이미지를 Firebase에 업로드하고, 성공하면 텍스트 업로드 로직을 호출
            // 이미지 선택을 위한 갤러리 인텐트 호출
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, GALLERY_CODE)
        }

        // 텍스트 업로드 버튼 클릭 이벤트
        binding?.snowaddIb?.setOnClickListener {
            // 텍스트 업로드 로직을 호출
            val snowText = binding!!.snowtextEt.text.toString()
            val snowData = SnowItem(snowText, snowText, 0, snowText) // 수정된 부분
            viewModel.addSnow(snowData)
            binding!!.snowtextEt.setText("") // 설명 전송하면 다시 텍스트 칸 초기화해주기
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { selectedImage ->
                // 이미지를 Firebase에 업로드
                uploadImageToFirebase(selectedImage)
            }
        }
    }

    // 이미지를 Firebase에 업로드
        // 이미지를 Firebase Storage에 업로드하기 위한 코드
        // storageReference를 사용하여 이미지를 업로드하고 성공 여부를 확인하세요.
        // 성공하면 해당 이미지의 URL을 받아올 수 있습니다.

    private fun uploadImageToFirebase(imageUri: Uri) {
        // Firebase Storage에 업로드할 이미지의 저장소 경로 설정
        val imageName = "snow/${System.currentTimeMillis()}_image.jpg"
        val imageRef = storageReference.child(imageName)

        // 이미지를 Firebase Storage에 업로드
        val uploadTask = imageRef.putFile(imageUri)

        // 업로드가 완료되면 실행될 콜백
        uploadTask.addOnCompleteListener(OnCompleteListener<UploadTask.TaskSnapshot> { task ->
            if (task.isSuccessful) {
                // 이미지 업로드 성공
                Log.d("Firebase", "Image upload successful")

                // 업로드된 이미지의 다운로드 URL을 받아옴
                imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    val imageUrl = downloadUri.toString()
                    // TODO: 여기서 imageUrl을 사용하여 필요한 작업 수행
                    Log.d("Firebase", "Image URL: $imageUrl")
                }.addOnFailureListener { exception ->
                    // 다운로드 URL 가져오기 실패
                    Log.e("Firebase", "Failed to get download URL: $exception")
                }
            } else {
                // 이미지 업로드 실패
                Log.e("Firebase", "Image upload failed: ${task.exception}")
            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

/*package kr.kau.nyangmal3

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

//냥: 펑/스토리 ~24시간뒤면 사라짐. 한번읽으면사라짐?, 모든 사람들의 상태메시지모음화면느낌
class SnowFragment() : Fragment() {

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

        adapter = SnowAdapter()
        binding?.recyclerSnows?.layoutManager = LinearLayoutManager(context) //쌓이는형태
        binding?.recyclerSnows?.adapter = adapter
        //observerData()
        // 뷰모델 초기화
        //viewModel = ViewModelProvider(this, SnowViewModelFactory(repository)).get(SnowViewModel::class.java)

        // LiveData를 관찰하고 UI 업데이트
//        viewModel.snowData.observe(viewLifecycleOwner){
//            /*binding?.snowpicB.
//            binding?.snowtextB
//            binding?.snowaddIb 여기는 후에 수정*/
//        }
//        snowViewModel.snowData.observe(viewLifecycleOwner, Observer { snowData ->
//            // RecyclerView 등을 사용하여 데이터를 UI에 표시
//        })
        //이건챗에서 같은내용아래처럼햇는데 걍 ㄱㄱ
        fun observerData() {
            viewModel.fetchData().observe(viewLifecycleOwner, Observer {
                adapter.setListData(it)
                adapter.notifyDataSetChanged()
            }
            )
        }

        observerData()

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
            val snowData = SnowItem(snowText, snowText, 0, snowText) //고쳐라
            viewModel.addSnow(snowData)
            binding!!.snowtextEt.setText("") //설명전송하면 다시 텍스트칸 초기화해주기

        }

        // 데이터 가져오기 및 RecyclerView 업데이트
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding=null
    }
}*/

//////////////////////////////////////////////////////////////////

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