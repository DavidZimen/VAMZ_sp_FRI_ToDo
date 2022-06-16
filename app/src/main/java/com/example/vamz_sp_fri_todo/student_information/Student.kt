package com.example.vamz_sp_fri_todo.student_information

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
        studentDC.osCislo.also { osCislo_ = it }
        studentDC.mail.also { mail_ = it}
        studentDC.password.also { password_ = it }
        studentDC.name.also { name_ = it }
        studentDC.surname.also { surname_ = it }
        Date(studentDC.birthDate).also { birthDate_ = it }
        studentDC.studOdbor.also { studOdbor_ = it }
    }

    var osCislo_: Int

    var mail_: String

    var password_: String

    var name_: String

    var surname_: String

    var birthDate_: Date

    var studOdbor_: String

}