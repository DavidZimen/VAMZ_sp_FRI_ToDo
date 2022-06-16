package com.example.vamz_sp_fri_todo.app_logging.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.vamz_sp_fri_todo.database.StudentDatabase
import com.example.vamz_sp_fri_todo.database.StudentDatabaseDao
import com.example.vamz_sp_fri_todo.database.data_classes.StudentDC
import kotlinx.coroutines.launch

/**
 * View model pre Logging, ktorý si drží informácie o študentovi
 * a podľa toho overuje buď heslo daného študenta,
 * alebo či pri registrácii už daný študent existuje.
 */
class LoggingViewModel(osCislo: Int?, val db: StudentDatabaseDao, application: Application) : AndroidViewModel(application) {

    lateinit var student: LiveData<StudentDC?>

    init {
        if (osCislo != null) {
            viewModelScope.launch {
                student = db.getStudent(osCislo)
            }
        }
    }

    fun insertStudent(student: StudentDC) {
        viewModelScope.launch {
            db.insertStudent(student)
        }
    }

    fun getStudent(osCislo: Int) {
        viewModelScope.launch {
            student = db.getStudent(osCislo)
        }
    }

    fun clear() {
        viewModelScope.launch {
            db.clearStudents()
        }
    }

}