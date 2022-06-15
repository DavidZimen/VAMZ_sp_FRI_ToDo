package com.example.vamz_sp_fri_todo.app_logging.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.vamz_sp_fri_todo.R
import com.example.vamz_sp_fri_todo.app_logging.LoginHelper
import com.example.vamz_sp_fri_todo.app_logging.view_model.LoggingViewModel
import com.example.vamz_sp_fri_todo.mainFuncionality.MainFuncionalityActivity
import com.example.vamz_sp_fri_todo.student.Student
import kotlinx.android.synthetic.main.fragment_login.view.*
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
        val helper = LoginHelper()

        val viewModelFactory = this.requireActivity().defaultViewModelProviderFactory
        val viewModel = ViewModelProvider(this, viewModelFactory)[LoggingViewModel::class.java]

        view.loginButton.setOnClickListener {
            val editTextList = mutableListOf<EditText>()
            editTextList.add(view.os_cislo)
            editTextList.add(view.pass)

            if (helper.checkEmptiness(editTextList)) {
                viewModel.getStudent(editTextList[0].text.toString().toInt())
                val student = viewModel.student

                if (editTextList[1].text.toString() == student?.password.toString()) {
                    val intent = Intent(this.context, MainFuncionalityActivity::class.java)
                    val passedStudent = Student(student!!)
                    intent.putExtra("student", passedStudent)
                    this.requireActivity().finish()
                    startActivity(intent)
                } else {
                    view.error_text.text = "Osobné číslo alebo heslo je nesprávne."
                }
            }
        }

        return view
    }
}