package com.example.vamz_sp_fri_todo.database.data_classes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data trieda, ktroá reprezentuje používateľa, ktorý je v tomto prípade aj študentom.
 */
@Entity(tableName = "users_table")
data class StudentDC (

    @PrimaryKey(autoGenerate = false)
    var osCislo: Int = 0,

    @ColumnInfo(name = "mail")
    var mail: String = "",

    @ColumnInfo(name = "password")
    var password: String = "",

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "surname")
    var surname: String = "",

    @ColumnInfo(name = "birthDate")
    var birthDate: Long = 0L,

    @ColumnInfo(name = "studOdbor")
    var studOdbor: String = "",
)
