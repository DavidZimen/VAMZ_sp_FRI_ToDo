package com.example.vamz_sp_fri_todo.app_logging.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vamz_sp_fri_todo.database.StudentDatabaseDao
import java.lang.IllegalArgumentException

/**
 * ViewModelFactory trieda, ktorá vracia a ViewModel pre potreby Logging activity.
 */
class LoggingViewModelFactory(private val dataSource: StudentDatabaseDao, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(LoggingViewModel::class.java)) {
            return LoggingViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Neznámz ViewModel")
    }
}