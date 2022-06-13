package com.example.vamz_sp_fri_todo.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.vamz_sp_fri_todo.database.data_classes.StudentDC

/**
 * Dao interface pre prácu s databázou, ktorá ukladá v sebe studentov
 * ako používateľov aplikácie.
 */
@Dao
interface StudentDatabaseDao {

    @Insert
    suspend fun insert(student: StudentDC)

    @Update
    suspend fun update(student: StudentDC)

    /**
     * Vyčistí celú tabuľku s používateľmi.
     */
    @Query("DELETE FROM users_table")
    suspend fun clear()

    /**
     * Môže vracať aj null hodnoty, lebo dany kluc sa nemusí v tabuľke nachádzať
     */
    @Query("SELECT * FROM users_table WHERE id = :key")
    suspend fun getStudent(key: Int): StudentDC?
}