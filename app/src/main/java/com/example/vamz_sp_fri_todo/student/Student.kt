package com.example.vamz_sp_fri_todo.student

import java.util.*

/**
 * Trieda v ktorej sa uchovávavjú informácie o aktuálne prihlásenom študentovi v aplikácii.
 */
class Student(val id: Int, val mail: String, password: String,
              name: String, surname: String, birthDate: Date, studOdbor: String) {
    init {
        password.also { password_ = it }
        name.also { name_ = it }
        surname.also { surname_ = it }
        birthDate.also { birthDate_ = it }
        studOdbor.also { studOdbor_ = it }
    }

    private var password_: String
        get() = password_
        set(value: String) {password_ = value}

    private var name_: String
        get() = name_
        set(value) {name_ = value}

    private var surname_: String
        get() = surname_
        set(value: String) {surname_ = value}

    private var birthDate_: Date
        get() = birthDate_
        set(value) {birthDate_ = value}

    private var studOdbor_: String
        get() = studOdbor_
        set(value: String) {studOdbor_ = value}

    fun printInfo(): String = "$name_ $surname_ $id $studOdbor_"
}