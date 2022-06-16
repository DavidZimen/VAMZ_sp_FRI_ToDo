package com.example.vamz_sp_fri_todo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.vamz_sp_fri_todo.database.data_classes.StudentDC
import com.example.vamz_sp_fri_todo.database.data_classes.ToDoItemDC
import com.example.vamz_sp_fri_todo.database.data_classes.ToDoListDC

/**
 * Abstraktná trieda pre databázu Studentov.
 * Vytvorí jednu inštanciu tabuľky, ktorú bude následne sprístupňovať pre účely aplikácie.
 */
@Database(
    entities = [
        StudentDC::class,
        ToDoListDC::class,
        ToDoItemDC::class],
    version = 4,
    exportSchema = false
)
abstract class StudentDatabase : RoomDatabase() {

    abstract val studentDatabaseDao: StudentDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: StudentDatabase? = null

        fun getInstance(context: Context): StudentDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        StudentDatabase::class.java,
                        "to_do_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}