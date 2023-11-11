package kr.kau.nyangmal3

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Vibrator
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import kr.kau.nyangmal3.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSettingsBinding.inflate(inflater, container, false)
//        var vibrator : Vibrator? = null
//        vibrator = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            getSystemService(Vibrator::class.java)
//        }else{
//            getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
//        }

        return binding.root
    }
}