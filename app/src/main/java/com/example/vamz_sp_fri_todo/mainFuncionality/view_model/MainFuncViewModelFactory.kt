package com.example.vamz_sp_fri_todo.mainFuncionality.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vamz_sp_fri_todo.database.StudentDatabaseDao
import java.io.Serializable
import java.lang.IllegalArgumentException

/**
 * ViewModelFactory trieda, ktorá vracia a ViewModel pre potreby MainFuncionalityActivity.
 */
class MainFuncViewModelFactory(private val osCislo: Int, private val dataSource: StudentDatabaseDao, private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MainFuncViewModel::class.java)) {
            return MainFuncViewModel(osCislo, dataSource, application) as T
        }
        throw IllegalArgumentException("Neznámz ViewModel")
    }

}