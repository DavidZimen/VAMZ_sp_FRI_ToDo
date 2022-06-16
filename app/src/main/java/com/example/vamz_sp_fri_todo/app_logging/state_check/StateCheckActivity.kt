package com.example.vamz_sp_fri_todo.app_logging.state_check

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vamz_sp_fri_todo.R
import com.example.vamz_sp_fri_todo.app_logging.LoggingActivity
import com.example.vamz_sp_fri_todo.app_logging.view_model.LoggingViewModel
import com.example.vamz_sp_fri_todo.app_logging.view_model.LoggingViewModelFactory
import com.example.vamz_sp_fri_todo.database.StudentDatabase
import com.example.vamz_sp_fri_todo.mainFuncionality.MainFuncionalityActivity
import com.example.vamz_sp_fri_todo.student.Student

class StateCheckActivity : AppCompatActivity() {

    private lateinit var viewModelFactory: LoggingViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_state_check)

        val shrPref = getSharedPreferences("pref", Context.MODE_PRIVATE)


        val osCislo = shrPref.getInt("os_cislo", -1)

        //vytvorenie vpremennych pre pracu s datami
        val app = requireNotNull(this).application
        val db = StudentDatabase.getInstance(app).studentDatabaseDao
        viewModelFactory = LoggingViewModelFactory(osCislo, db, app)
        val viewModel = ViewModelProvider(this, viewModelFactory)[LoggingViewModel::class.java]

        if (osCislo != -1) {
            viewModel.student.observe(this, Observer {
                viewModel.getStudent(osCislo)
                val studentDC = it
                val student = Student(studentDC!!)
                val intent = Intent(this, MainFuncionalityActivity::class.java)
                intent.putExtra("student", student)
                startActivity(intent)
            })
        } else {
            val intent = Intent(this, LoggingActivity::class.java)
            startActivity(intent)
        }

    }

    override fun getDefaultViewModelProviderFactory(): LoggingViewModelFactory {
        return viewModelFactory
    }
}