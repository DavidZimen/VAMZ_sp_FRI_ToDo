package com.example.vamz_sp_fri_todo.mainFuncionality.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.vamz_sp_fri_todo.database.StudentDatabaseDao
import com.example.vamz_sp_fri_todo.database.data_classes.ToDoItemDC
import com.example.vamz_sp_fri_todo.database.data_classes.ToDoListDC
import com.example.vamz_sp_fri_todo.database.relations.ListWithItems
import com.example.vamz_sp_fri_todo.database.relations.StudentWithLists
import kotlinx.coroutines.launch
import java.io.Serializable

/**
 * ViewModel pre MainFuncionalityActivity a fragmenty, kotré ku nej prislúchajú.
 */
class MainFuncViewModel(val osCislo: Int, val db: StudentDatabaseDao, application: Application) : AndroidViewModel(application), Serializable {

    lateinit var lists: LiveData<List<StudentWithLists?>>
    var items: LiveData<List<ListWithItems?>>? = null

    init {
        viewModelScope.launch {
            lists = db.getStudentWithLists(osCislo)
        }
    }

    fun removeListAtPosition(position: Int) {
        val id = lists.value?.get(0)?.lists?.get(position - 1)?.listId

        deleteList(id!!)
    }

    /**
     * Inicializuje items z daného listu pre ďalšie používanie.
     */
    fun getItemsOfList(listId: Int) {
        viewModelScope.launch {
            items = db.getListWithItems(listId)
        }
    }

    /**
     * Vloží ToDoItem do databázy.
     */
    fun insertItem(item: ToDoItemDC) {
        viewModelScope.launch {
            db.insertItem(item)
        }
    }

    /**
     * Vymaže ToDoItem s daným Id z databázy.
     */
    fun deleteItem(itemId: Int) {
        viewModelScope.launch {
            db.deleteItem(itemId)
        }
    }

    /**
     * Aktualizuje item v databáze.
     */
    fun updateItem(item: ToDoItemDC) {
        viewModelScope.launch {
            db.updateItem(item)
        }
    }

    /**
     * Vloží ToDoList do databázy.
     */
    fun insertList(list: ToDoListDC) {
        viewModelScope.launch {
            db.insertList(list)
        }
    }

    /**
     * Vymaže ToDoList s daným Id z databázy.
     */
    fun deleteList(listId: Int) {
        viewModelScope.launch {
            db.deleteList(listId)
        }
    }

    /**
     * Aktualizuje list v databáze.
     */
    fun updateList(list: ToDoListDC) {
        viewModelScope.launch {
            db.updateList(list)
        }
    }

    /**
     * Vymaže všetky ToDoItemy prislúchajúce ku danému ToDoListu.
     */
    fun deleteItemsOfList(listId: Int) {
        viewModelScope.launch {
            db.deleteItemsFromList(listId)
        }
    }

    /**
     * nastavi hodnotu listId pre mazanie
     */
    private fun getListId(osCislo: Int, title: String) {
        //TODO: Z nejakeho dovodu vracia null hodnotu
        viewModelScope.launch {
             db.getListId(osCislo, title)?.listId
        }
    }
}