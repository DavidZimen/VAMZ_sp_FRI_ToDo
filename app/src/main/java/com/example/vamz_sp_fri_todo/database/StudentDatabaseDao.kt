package com.example.vamz_sp_fri_todo.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.vamz_sp_fri_todo.database.data_classes.StudentDC
import com.example.vamz_sp_fri_todo.database.data_classes.ToDoItemDC
import com.example.vamz_sp_fri_todo.database.data_classes.ToDoListDC
import com.example.vamz_sp_fri_todo.database.relations.ListWithItems
import com.example.vamz_sp_fri_todo.database.relations.StudentWithLists

/**
 * Dao interface pre prácu s databázou, ktorá ukladá v sebe
 * studentov ako používateľov aplikácie,
 * listy, ako zoznamy s todoItems
 * a ToDoItems.
 */
@Dao
interface StudentDatabaseDao {

    @Insert
    suspend fun insertStudent(student: StudentDC)

    @Insert
    suspend fun insertList(list: ToDoListDC)

    @Insert
    suspend fun insertItem(item: ToDoItemDC)

    @Update
    suspend fun updateStudent(student: StudentDC)

    @Update
    suspend fun updateList(list: ToDoListDC)

    @Update
    suspend fun updateItem(item: ToDoItemDC)

    /**
     * Vyčistí celú tabuľku s používateľmi.
     */
    @Transaction
    @Query("DELETE FROM users_table")
    suspend fun clearStudents()

    /**
     * Môže vracať aj null hodnoty, lebo dany kluc sa nemusí v tabuľke nachádzať
     */
    @Transaction
    @Query("SELECT * FROM users_table WHERE osCislo = :key")
    fun getStudent(key: Int): LiveData<StudentDC?>

    /**
     * Môže vracať aj null hodnoty, lebo dany kluc sa nemusí v tabuľke nachádzať
     */
    @Transaction
    @Query("SELECT * FROM lists_table WHERE listId = :listId")
    suspend fun getList(listId: Int): ToDoListDC?

    /**
     * Môže vracať aj null hodnoty, lebo dany kluc sa nemusí v tabuľke nachádzať
     */
    @Transaction
    @Query("SELECT * FROM items_table WHERE itemId = :itemId")
    suspend fun getItem(itemId: Int): ToDoItemDC?

    /**
     * Vráti študenta so všetkými svojími zoznamami, ktoré vytvoril.
     */
    @Transaction
    @Query("SELECT * FROM users_table WHERE osCislo = :osCislo")
    fun getStudentWithLists(osCislo: Int): LiveData<List<StudentWithLists?>>

    /**
     * Vráti študenta so všetkými svojími zoznamami, ktoré vytvoril.
     */
    @Transaction
    @Query("SELECT * FROM lists_table WHERE listId = :listId")
    fun getListWithItems(listId: Int): LiveData<List<ListWithItems?>>

    /**
     * Vymaže ToDoItem s daným Id.
     */
    @Transaction
    @Query("DELETE FROM items_table WHERE itemId = :itemId")
    suspend fun deleteItem(itemId: Int)

    /**
     * Vymaže všetky itemy, ktoré patria ku danému listu.
     */
    @Transaction
    @Query("DELETE FROM items_table WHERE listId = :listId")
    suspend fun deleteItemsFromList(listId: Int)

    /**
     * Vymaže list s daným id.
     */
    @Transaction
    @Query("DELETE FROM lists_table WHERE listId = :listId")
    suspend fun deleteList(listId: Int)

    /**
     * Vráti id zadaného zoznamu od zadaného študenta.
     */
    @Transaction
    @Query("SELECT * FROM lists_table WHERE listName = :title AND student = :osCislo")
    suspend fun getListId(osCislo: Int, title: String): ToDoListDC?
}
































