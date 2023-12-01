package kr.kau.nyangmal3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kr.kau.nyangmal3.ViewModel.MonologueViewModel
import kr.kau.nyangmal3.databinding.ActivityMonologueBinding

class MonologueActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMonologueBinding
    private val viewModel: MonologueViewModel by viewModels()
    private val adapter = MonologueAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMonologueBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView();
        setUpMessageSending();

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        binding.recMonologues.adapter = adapter
        binding.recMonologues.layoutManager = LinearLayoutManager(this)

        viewModel.messages.observe(this) { messages ->
            adapter.setListData(messages)
            binding.recMonologues.scrollToPosition(messages.size - 1)
        }
    }
    private fun setUpMessageSending() {
        binding.btnSubmit.setOnClickListener {
            val messageText = binding.edtMessage.text.toString()
            if (messageText.isNotBlank()) {
                viewModel.addMessage(messageText)
                binding.edtMessage.text.clear()
            }
        }
    }
}
