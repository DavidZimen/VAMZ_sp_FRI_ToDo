package com.example.vamz_sp_fri_todo.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.vamz_sp_fri_todo.database.data_classes.StudentDC
import com.example.vamz_sp_fri_todo.database.data_classes.ToDoListDC

/**
 * Trieda, ktorá pripája ku jednotlivým študentom zoznamy, ktoré si vytvorili
 * pre lepšiu prehľadnosť.
 */
data class StudentWithLists (

    @Embedded val student: StudentDC,
    @Relation(
        parentColumn = "osCislo",
        entityColumn = "student"
    )

    val lists: List<ToDoListDC>
    )