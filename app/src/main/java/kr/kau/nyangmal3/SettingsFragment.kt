package kr.kau.nyangmal3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import kr.kau.nyangmal3.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment(R.layout.fragment_settings){
    private var _binding:FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        childFragmentManager.beginTransaction()
            .add(R.id.fragment_container,PreferenceSettingFragment())
            .addToBackStack(null)
            .commit()
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


