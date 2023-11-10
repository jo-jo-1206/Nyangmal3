package kr.kau.nyangmal3

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import kr.kau.nyangmal3.databinding.DialogSnowBinding

class SnowDialog : DialogFragment() {
    private var _binding: DialogSnowBinding? = null
    private val binding get() = _binding!!

    private lateinit var snowPicButton: Button
    private lateinit var snowTextButton: Button
    private lateinit var snowEditText: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogSnowBinding.inflate(LayoutInflater.from(requireContext()))
        val view = binding.root

        // Initialize views
        snowPicButton = binding.snowpicB
        snowTextButton = binding.snowtextB
        snowEditText = binding.snowtextEt

        // Set up click listeners
        snowPicButton.setOnClickListener {
            // Handle logic for adding a picture
            // You can open the gallery or camera here
        }

        snowTextButton.setOnClickListener {
            // Handle logic for adding a description
            val description = snowEditText.text.toString()
            // You can save the description or perform any other necessary action
        }

        // Build the dialog
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setView(view)
        builder.setTitle("Add Snow Details")

        return builder.create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}