package com.example.vamz_sp_fri_todo.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.vamz_sp_fri_todo.database.data_classes.StudentDC
import com.example.vamz_sp_fri_todo.database.data_classes.ToDoItemDC

/**
 * Trieda, ktorá pripája ku jednotlivým študentom povinnosti, ktoré si vytvorili
 * pre lepšiu prehľadnosť.
 */
data class StudentWithItems (
    @Embedded val student: StudentDC,
    @Relation(
        parentColumn = "osCislo",
        entityColumn = "student"
    )

    val items: List<ToDoItemDC?>
        )
