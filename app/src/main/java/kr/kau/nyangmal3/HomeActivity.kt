package kr.kau.nyangmal3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.MediaController
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kr.kau.nyangmal3.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding //근데 교수님이 이거말고 바이레이지쓰랬음 엥근데쓰잖아실습때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //바인딩주는건 실습과 동일

        switchFragment(MyPageFragment()) //맨첫화면

        //setonItemSelectedListener쓰라고 권고??ㅗ딤 아이템 선택 이벤트를 처리하기위한 리스너. 버튼선택되면알아서아이템알려줌
        //바인딩.바텀네비아이디.set-- 로 when써서 아이템클릭할때 이프레그먼트로 전환 ㅇㅇ
        binding.bottomnv.setOnItemSelectedListener {item ->
            when(item.itemId) {
                R.id.myPage -> switchFragment(MyPageFragment())
                R.id.friends -> switchFragment(FriendListFragment())
                R.id.nyang -> switchFragment(NyangFragment())
                R.id.settings -> switchFragment(SettingsFragment())
            }
            true
        }
    }
    fun switchFragment(fragment: Fragment) { //그냥 메서드 만들기 너무 자주씀
        supportFragmentManager.beginTransaction().replace(R.id.containers, fragment).commit()
    }
}