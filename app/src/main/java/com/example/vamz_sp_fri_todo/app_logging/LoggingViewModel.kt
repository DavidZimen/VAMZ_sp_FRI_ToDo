package com.example.vamz_sp_fri_todo.app_logging

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.vamz_sp_fri_todo.database.StudentDatabase
import com.example.vamz_sp_fri_todo.database.data_classes.StudentDC
import kotlinx.coroutines.launch

class LoggingViewModel(application: Application) : AndroidViewModel(application) {

    private val db = StudentDatabase.getInstance(application.applicationContext).studentDatabaseDao
    val student = MutableLiveData<StudentDC?>()

    fun insertStudent(student: StudentDC) {
        viewModelScope.launch {
            db.insert(student)
        }
    }

    fun getStudent(osobneCislo: Int) {
        viewModelScope.launch {
            student.value = db.getStudent(osobneCislo)
        }
    }

    fun clear() {
        viewModelScope.launch {
            db.clear()
        }
    }

}