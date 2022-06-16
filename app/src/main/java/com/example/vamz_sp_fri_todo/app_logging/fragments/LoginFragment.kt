package com.example.vamz_sp_fri_todo.app_logging.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vamz_sp_fri_todo.R
import com.example.vamz_sp_fri_todo.helper.Helper
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
        val helper = Helper()

        val viewModelFactory = this.requireActivity().defaultViewModelProviderFactory
        val viewModel = ViewModelProvider(this, viewModelFactory)[LoggingViewModel::class.java]

        val shrPref = this.requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor = shrPref.edit()

        view.loginButton.setOnClickListener {
            val editTextList = mutableListOf<EditText>()
            editTextList.add(view.os_cislo)
            editTextList.add(view.pass)

            if (helper.checkEmptiness(editTextList)) {
                //inicializacie premennej student
                viewModel.getStudent(editTextList[0].text.toString().toInt())

                viewModel.student.observe(viewLifecycleOwner, Observer {
                    viewModel.getStudent(editTextList[0].text.toString().toInt())
                    val studentDC = it

                    if (editTextList[1].text.toString() == studentDC?.password.toString()) {
                        val intent = Intent(this.context, MainFuncionalityActivity::class.java)
                        val student = Student(studentDC!!)
                        intent.putExtra("student", student)

                        editor.apply {
                            editor.putInt("os_cislo", student.osCislo_)
                            apply()
                        }

                        this.requireActivity().finish()
                        startActivity(intent)
                    } else {
                        view.error_text.text = "Osobné číslo alebo heslo je nesprávne."
                    }
                })
            }
        }

        return view
    }
}