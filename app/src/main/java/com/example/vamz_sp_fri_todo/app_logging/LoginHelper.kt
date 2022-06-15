package com.example.vamz_sp_fri_todo.app_logging

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Build
import android.util.Patterns
import android.view.View
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.vamz_sp_fri_todo.R
import kotlinx.android.synthetic.main.fragment_add_item.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class LoginHelper {

    /**
     * Premení string na vstupe na korektný dátum a vráti.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun convertStringToDate(stringDate: String): Date? {
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
                editText.error = "Pole nesmie byť prázdne."
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
        return if (pass1.text.toString() != pass2.text.toString()) {
            pass2.error = "Heslá sa musia zhodovať !"
            pass2.setTextColor(Color.RED)
            false
        } else {
            true
        }
    }

    /**
     * Vytvorí OnClickListener pre výber dátumu.
     * Pre zjednodušenie prehľadnosti kódu.
     */
    fun setDatumClickListener(view: View, register: Boolean, fragment: Fragment) : View.OnClickListener {

        return  View.OnClickListener {
            //ziskanie pristupu ku polu s datumom narodenia
            val dateEditText = view.item_date
            dateEditText.showSoftInputOnFocus = false

            //vytvorenie instancie kalendara a ziskanie jednotlivych casti do premennych
            //mesiac ma o jedno viac, aby sme sa posunuli v poli, kedze januar zacina na 0
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            //vytvorenie listenera pre datum a nastavenie datumu do textView
            val dateSetListener =
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    val month: Int = monthOfYear + 1
                    var day: String = dayOfMonth.toString()

                    if (dayOfMonth < 10) day = "0$day"

                    if (month < 10) {
                        dateEditText.setText("$day.0$month.$year")
                    } else {
                        dateEditText.setText("$day.$month.$year")
                    }
                }


            //vytvorenie a zobrazenie dialogu pre vyber datumu
            val dpd = fragment.requireActivity().let { it1 -> DatePickerDialog(it1, R.style.MyDatePickerDialogTheme,dateSetListener, year, month, day) }

            if (register) {
                dpd.datePicker.maxDate = System.currentTimeMillis()
            } else {
                dpd.datePicker.minDate = System.currentTimeMillis()
            }

            dpd.show()
        }
    }
}