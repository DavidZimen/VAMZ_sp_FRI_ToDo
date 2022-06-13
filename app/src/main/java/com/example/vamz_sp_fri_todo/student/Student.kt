package com.example.vamz_sp_fri_todo.student

import com.example.vamz_sp_fri_todo.database.data_classes.StudentDC
import java.io.Serializable
import java.util.*

/**
 * Trieda v ktorej sa uchovávavjú informácie o aktuálne prihlásenom študentovi v aplikácii.
 * Implementuje Serializable, aby sa mohla posúvať medzi aktivitami.
 * @param studentDC - data classs, podla ktorej sa ppri prihlaseni vytvori instancia studenta, s ktorou sa bude pracovat v celej aplikacii
 */
class Student(studentDC: StudentDC) : Serializable {
    init {
        studentDC.id.also { osCislo_ = it }
        studentDC.mail.also { mail_ = it}
        studentDC.password.also { password_ = it }
        studentDC.name.also { name_ = it }
        studentDC.surname.also { surname_ = it }
        Date(studentDC.birthDate).also { birthDate_ = it }
        studentDC.studOdbor.also { studOdbor_ = it }
    }

    var osCislo_: Int
        get() = field
        set(value: Int) { field = value }

    var mail_: String
        get() = field
        set(value: String) { field = value }

    var password_: String
        get() = field
        set(value: String) { field = value }

    var name_: String
        get() = field
        set(value) { field = value }

    var surname_: String
        get() = field
        set(value: String) { field = value }

    var birthDate_: Date
        get() = field
        set(value) { field = value }

    var studOdbor_: String
        get() = field
        set(value: String) { field = value }

    fun printInfo(): String = "$name_ $surname_ $osCislo_ $studOdbor_"
}