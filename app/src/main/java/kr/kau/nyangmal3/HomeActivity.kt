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

        switchFragment(MyPageFragment())

        binding.bottomnv.setOnItemSelectedListener {item ->
            when(item.itemId) {
                R.id.myPage -> switchFragment(MyPageFragment())
                R.id.friends -> switchFragment(FriendListFragment())
                R.id.nyang -> switchFragment(NyangFragment())
                R.id.settings -> switchFragment(SettingsFragment())
            }
            true
        }
        /*setOnClickListener { item ->
            when (item.id) {
                R.id.myPage -> switchFragment(MyPageFragment())
                R.id.friends -> switchFragment(FriendsFragment())
                R.id.nyang -> switchFragment(NyangFragment())
                R.id.settings -> switchFragment(SettingsFragment())
            }
            true
        }*/
    }
    fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.containers, fragment).commit()
    }
}