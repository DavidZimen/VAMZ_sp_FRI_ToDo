package com.example.vamz_sp_fri_todo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Abstraktná trieda pre databázu Studentov.
 * Vytvorí jednu inštanciu tabuľky, ktorú bude následne sprístupňovať pre účely aplikácie.
 */
@Database(entities = [StudentDataClass::class], version = 1, exportSchema = false)
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
                        "app_database"
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