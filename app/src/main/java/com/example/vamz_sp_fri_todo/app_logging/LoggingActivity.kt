package com.example.vamz_sp_fri_todo.app_logging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vamz_sp_fri_todo.R
import com.example.vamz_sp_fri_todo.app_logging.view_model.LoggingViewModelFactory
import com.example.vamz_sp_fri_todo.database.StudentDatabase

class LoggingActivity : AppCompatActivity() {

    private lateinit var viewModelFactory: LoggingViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logging)

        //vytvorenie vpremennych pre pracu s datami
        val app = requireNotNull(this).application
        val db = StudentDatabase.getInstance(app).studentDatabaseDao
        viewModelFactory = LoggingViewModelFactory(db, app)
    }

    override fun getDefaultViewModelProviderFactory(): LoggingViewModelFactory {
        return viewModelFactory
    }
}