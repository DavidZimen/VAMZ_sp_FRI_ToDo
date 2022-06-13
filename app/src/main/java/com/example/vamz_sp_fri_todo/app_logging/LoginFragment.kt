package com.example.vamz_sp_fri_todo.app_logging

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.vamz_sp_fri_todo.R
import com.example.vamz_sp_fri_todo.database.data_classes.StudentDC
import com.example.vamz_sp_fri_todo.student.Student
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_register.view.*
import kotlinx.android.synthetic.main.fragment_register.view.os_cislo
import kotlinx.android.synthetic.main.fragment_register.view.pass


/**
 * Vytvorí fragmet pre obsluhu prihlásenia do systému.
 */
class LoginFragment : Fragment() {

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_login, container, false)
        val helper = HelperClass()

        val viewModel = this.activity?.let { LoggingViewModel(it.application) }!!

        view.loginButton.setOnClickListener {
            val editTextList = mutableListOf<EditText>()
            editTextList.add(view.os_cislo)
            editTextList.add(view.pass)

            if (helper.checkEmptiness(editTextList)) {
                viewModel.getStudent(editTextList[0].text.toString().toInt())
                val student = viewModel.student.value

                if (editTextList[1].text.toString() == student?.password) {
                    val intent = Intent(this.context, ControlActivity::class.java)
                    val passedStudent = Student(student)
                    intent.putExtra("student", passedStudent)
                    startActivity(intent)
                } else {
                    view.error_text.text = "Osobné číslo alebo heslo je nesprávne."
                }
            }
        }

        return view
    }
}