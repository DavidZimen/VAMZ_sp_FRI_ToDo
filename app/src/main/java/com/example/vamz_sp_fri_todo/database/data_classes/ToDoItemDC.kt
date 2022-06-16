package com.example.vamz_sp_fri_todo.database.data_classes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Udržiava v sebe informácie pre ToDoItem,
 * ktoré sa zobrazujú na obrazovke.
 */
@Entity(tableName = "items_table")
data class ToDoItemDC (
    @PrimaryKey(autoGenerate = true)
    val itemId: Int,

    @ColumnInfo(name = "listId")
    var listId: Int = 0,

    @ColumnInfo(name = "student")
    var student: Int = 0,

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "description")
    var description: String = "",

    @ColumnInfo(name = "status")
    var status: Int = 0,

    @ColumnInfo(name = "deadline")
    var deadline: Long = 0L,

        )