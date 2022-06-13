package com.example.vamz_sp_fri_todo.app_logging

import android.graphics.Color
import android.os.Build
import android.util.Patterns
import android.widget.EditText
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class HelperClass {

    /**
     * Premení string na vstupe na korektný dátum a vráti.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun convertToDate(stringDate: String): Date? {
        val df  = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val date = LocalDate.parse(stringDate, df).toString()

        return java.sql.Date.valueOf(date)
    }

    /**
     * Skontroluje, či sú vyplnené všetky polia na obrazovke.
     */
    fun checkEmptiness(editTexts: List<EditText>) :  Boolean {
        var counter = 0

        for (editText in editTexts) {
            if (editText.text.toString().isEmpty()) {
                editText.setError("Pole nesmie byť prázdne.")
                counter++
            }
        }
        return counter == 0
    }

    /**
     * Skontroluje správnosť mailovej adresy.
     * Podľa toho vráti booleanovskú hodnotu.
     */
    fun checkEmail(email: EditText): Boolean {
        return if (Patterns.EMAIL_ADDRESS.matcher(email.text).matches()) {
            true
        } else {
            email.error = "Zadajte korektný tvar e-mailovej adresy."
            false
        }
    }

    /**
     * Skontroluje zhodu hesiel pri registrácii.
     */
    fun checkPassword(pass1: EditText, pass2: EditText): Boolean {
        return if (pass1.text != pass2.text) {
            pass2.setError("Heslá sa musia zhodovať !")
            pass2.setTextColor(Color.RED)
            false
        } else {
            true
        }
    }
}