package com.example.vamz_sp_fri_todo.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * Dao interface pre prácu s databázou, ktorá ukladá v sebe studentov
 * ako používateľov aplikácie.
 */
@Dao
interface StudentDatabaseDao {
    @Insert
    fun insert(student: StudentDataClass)

    @Update
    fun update(student: StudentDataClass)

    /**
     * Vyčistí celú tabuľku s používateľmi.
     */
    @Query("DELETE FROM users_table")
    fun clear()

    /**
     * Môže vracať aj null hodnoty, lebo dany kluc sa nemusí v tabuľke nachádzať
     */
    @Query("SELECT * FROM users_table WHERE id = :key")
    fun getStudent(key: Int): StudentDataClass?
}