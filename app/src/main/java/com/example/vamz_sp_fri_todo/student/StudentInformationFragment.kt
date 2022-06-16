package com.example.vamz_sp_fri_todo.student

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.vamz_sp_fri_todo.R
import kotlinx.android.synthetic.main.fragment_register.view.*
import kotlinx.android.synthetic.main.fragment_register.view.meno
import kotlinx.android.synthetic.main.fragment_register.view.os_cislo
import kotlinx.android.synthetic.main.fragment_register.view.priezvisko
import kotlinx.android.synthetic.main.fragment_student_information.view.*
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Fragment, ktorý zobrazí informácie o študentovi do aktivity.
 */
class StudentInformationFragment : Fragment() {
    private lateinit var student: Student

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_student_information, container, false)

        //ziskanie aktivity a studenta z nej
        student = this.activity?.intent?.getSerializableExtra("student") as Student

        with(view) {
            acc_meno.text = student.name_
            acc_priezvisko.text = student.surname_
            acc_os_cislo.text = student.osCislo_.toString()
            acc_stud_program.text = student.studOdbor_
            acc_mail.text = student.mail_

            val date = student.birthDate_.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            acc_datum_nar.text = date.format(formatter).toString()
        }

        return view
    }
}