package com.example.vamz_sp_fri_todo.database.data_classes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Udr6iava v sebe informácie o listoch, ktoré boli vytvorené.
 */
@Entity(tableName = "lists_table")
data class ToDoListDC (
    @ColumnInfo(name = "listId")
    @PrimaryKey(autoGenerate = true)
    val listId: Int,

    @ColumnInfo(name = "listName")
    val listName: String = "",

    @ColumnInfo(name = "student")
    val student: Int,

    )