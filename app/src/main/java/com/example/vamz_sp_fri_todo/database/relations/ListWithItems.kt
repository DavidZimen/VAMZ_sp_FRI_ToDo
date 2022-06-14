package com.example.vamz_sp_fri_todo.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.vamz_sp_fri_todo.database.data_classes.ToDoItemDC
import com.example.vamz_sp_fri_todo.database.data_classes.ToDoListDC


/**
 * Trieda dát, ktorá spája jednotlivé ToDoItems ku príslušným zoznamom.
 */
data class ListWithItems (
    @Embedded val toDoList: ToDoListDC,

    @Relation(
        parentColumn = "listId",
        entityColumn = "listId",
    )

    val toDoItems: List<ToDoItemDC>

        )