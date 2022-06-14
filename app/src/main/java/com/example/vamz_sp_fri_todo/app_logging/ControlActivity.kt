package com.example.vamz_sp_fri_todo.app_logging


import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.example.vamz_sp_fri_todo.databinding.ActivityControlBinding
import com.example.vamz_sp_fri_todo.student.Student
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ControlActivity : AppCompatActivity() {

    private lateinit var binding: ActivityControlBinding
    private lateinit var viewModel: LoggingViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityControlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val student: Student = intent.getSerializableExtra("student") as Student

        viewModel = LoggingViewModel(this.application)
        viewModel.getStudent(student.osCislo_)

        viewModel.student.observe(this, Observer {
            if (it != null) {
                binding.tw1.text = it.osCislo.toString()
                binding.tw2.text = it.name
                binding.tw3.text = it.surname
                binding.tw4.text = it.mail

                val df  = DateTimeFormatter.ofPattern("dd.MM.yyyy")

                binding.tw5.text = SimpleDateFormat("dd.MM.yyyy").format(it.birthDate).toString()
                binding.tw6.text = LocalDate.parse(intent.getStringExtra("datum"), df).toString()
            }
        })
    }

    override fun onBackPressed() {
    }

}

