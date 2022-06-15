package com.example.vamz_sp_fri_todo.app_logging.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.vamz_sp_fri_todo.R
import com.example.vamz_sp_fri_todo.app_logging.LoginHelper
import com.example.vamz_sp_fri_todo.app_logging.view_model.LoggingViewModel
import com.example.vamz_sp_fri_todo.database.data_classes.StudentDC
import com.example.vamz_sp_fri_todo.mainFuncionality.MainFuncionalityActivity
import com.example.vamz_sp_fri_todo.student.Student
import kotlinx.android.synthetic.main.fragment_register.view.*
import java.util.*

/**
 * Vytvorí fragmet pre obsluhu registrácie do systému.
 */
class RegisterFragment : Fragment() {

    private lateinit var viewModel: LoggingViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        val helper = LoginHelper()

        val viewModelFactory = this.requireActivity().defaultViewModelProviderFactory
        viewModel = ViewModelProvider(this, viewModelFactory)[LoggingViewModel::class.java]

        view.datum.setOnClickListener(setDatumClickListener(view))

        //ziskanie autocomplete textview a nastavenie vlastnosti
        view.program.setAdapter(ArrayAdapter(this.context!!, android.R.layout.simple_dropdown_item_1line, resources.getStringArray(R.array.Programy)))
        view.program.showSoftInputOnFocus = false
        view.program.setOnClickListener( View.OnClickListener {
            view.program.showDropDown()
        })

        view.registerButton.setOnClickListener {

            val editTextList = mutableListOf<EditText>()
            editTextList.add(view.meno)
            editTextList.add(view.priezvisko)
            editTextList.add(view.pass)
            editTextList.add(view.pass_conf)
            editTextList.add(view.os_cislo)
            editTextList.add(view.datum)
            editTextList.add(view.program)
            editTextList.add(view.email)

            //zacne vykonavat iba ak su vyplnene vsetky polia
            if (helper.checkEmptiness(editTextList)) {

                //zadany mail musi byt v sprvnom tvare, iba vtedy sa pokracuje
                if (helper.checkEmail(editTextList.last())) {

                    //kontrola zhody zadanych hesiel
                    if (helper.checkPassword(view.pass, view.pass_conf)) {

                        //ak uz dany student neexistuje tak zaregistruje
                        viewModel.getStudent(editTextList[4].text.toString().toInt())
                        val pomStudent = viewModel.student

                        if (pomStudent == null) {
                            //vytiahnutie osobneho cisla, lebo to sa bude posielat dalej
                            val osCislo = Integer.parseInt(view.os_cislo.text.toString())

                            //vytvorenie instancie studenta a jeho nasledne pridanie do databazy
                            val student = StudentDC()
                            student.osCislo = osCislo
                            student.mail = view.email.text.toString()
                            student.name = view.meno.text.toString()
                            student.surname = view.priezvisko.text.toString()
                            student.password = view.pass.text.toString()
                            student.studOdbor = view.program.text.toString()
                            student.birthDate = helper.convertStringToDate(view.datum.text.toString())?.time!!

                            //pridanie studenta
                            viewModel.insertStudent(student)

                            val intent = Intent(this.context, MainFuncionalityActivity::class.java)
                            val passedStudent = Student(student)
                            intent.putExtra("student", passedStudent)
                            intent.putExtra("datum", view.datum.text.toString())
                            startActivity(intent)
                        } else {
                            editTextList[4].error = "Študent s daným číslom existuje. Prihláste sa alebo zadajne iné číslo."
                        }
                    }
                }
            }
        }

        return view
    }


    /**
     * Vytvorí OnClickListener pre výber dátumu.
     * Pre zjednodušenie prehľadnosti kódu.
     */
    private fun setDatumClickListener(view: View) : View.OnClickListener {

        return  View.OnClickListener {
            //ziskanie pristupu ku polu s datumom narodenia
            val birthDateEditText = view.datum
            birthDateEditText.showSoftInputOnFocus = false

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
                        birthDateEditText.setText("$day.0$month.$year")
                    } else {
                        birthDateEditText.setText("$day.$month.$year")
                    }
                }


            //vytvorenie a zobrazenie dialogu pre vyber datumu
            val dpd = activity?.let { it1 -> DatePickerDialog(it1, R.style.MyDatePickerDialogTheme,dateSetListener, year, month, day) }
            dpd?.datePicker?.maxDate = System.currentTimeMillis()
            dpd?.show()
        }
    }
}





































