package com.example.vamz_sp_fri_todo.app_logging


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.example.vamz_sp_fri_todo.databinding.ActivityControlBinding
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ControlActivity : AppCompatActivity() {

    private lateinit var binding: ActivityControlBinding
    private lateinit var viewModel: LoggingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityControlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val osCislo = intent.getIntExtra("osobneCislo", -1)
        Log.d("Osobne cislo: ", osCislo.toString())

        viewModel = LoggingViewModel(this.application)
        viewModel.getStudent(osCislo)

        viewModel.student.observe(this, Observer {
            if (it != null) {
                binding.tw1.text = it.id.toString()
                binding.tw2.text = it.name
                binding.tw3.text = it.surname
                binding.tw4.text = it.mail
                binding.tw5.text = it.studOdbor
                binding.tw6.text = it.password
            }
        })
    }


}

