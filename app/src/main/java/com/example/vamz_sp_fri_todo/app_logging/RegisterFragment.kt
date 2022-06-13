package com.example.vamz_sp_fri_todo.app_logging

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import com.example.vamz_sp_fri_todo.R
import kotlinx.android.synthetic.main.fragment_register.view.*
import com.example.vamz_sp_fri_todo.database.data_classes.StudentDC
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

/**
 * Vytvorí fragmet pre obsluhu registrácie do systému.
 */
class RegisterFragment : Fragment() {

    private lateinit var viewModel: LoggingViewModel

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        viewModel = this.activity?.let { LoggingViewModel(it.application) }!!

        view.datum.setOnClickListener(setDatumClickListener(view))

        view.registerButton.setOnClickListener {
            //TODO: nezabudnut vymazat
            viewModel.clear()

            //vytiahnutie premennych z pohladu fragmentu
            val meno = view.meno.text.toString()
            val priezvisko = view.priezvisko.text.toString()
            val datum = convertToDate(view.datum.text.toString())?.time
            val osCislo = Integer.parseInt(view.os_cislo.text.toString())
            val studOdbor = view.program.text.toString()
            val mail = view.email.text.toString()
            val heslo = view.pass.text.toString()

            //vytvorenie instancie studenta a jeho nasledne pridanie do databazy
            //val student = datum?.let { it1 ->
            Log.d("VIEW MODEL", "idem vkladat studenta")
            val student = StudentDC()
            student.id = osCislo
            student.mail = mail
            student.name = meno
            student.surname = priezvisko
            student.password = heslo
            student.studOdbor = studOdbor
            student.birthDate = datum!!

            registerStudent(student)

            Log.d("prepnutie", "Tu som sa dostal")
            val intent = Intent(this.context, ControlActivity::class.java)
            intent.putExtra("osobneCislo", osCislo)
            startActivity(intent)
        }

        return view
    }

    /**
     * Funkcia na registraciu noveho studenta do DB používateľov.
     * Skontroluje či daný študent už existuje.
     */
    fun registerStudent(student: StudentDC) : Boolean {
        viewModel.insertStudent(student)
        return true
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
                    //prebratie nazvu mesiaca
                    //val month_date = SimpleDateFormat("MMMM")
                    //val month_name: String = month_date.format(calendar.time)

                    birthDateEditText.setText("$dayOfMonth.$monthOfYear.$year")
                }

            //vytvorenie a zobrazenie dialogu pre vyber datumu
            activity?.let { it1 -> DatePickerDialog(it1, dateSetListener, year, month, day) }
                ?.show()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun convertToDate(stringDate: String?) : Date? {
        if (stringDate != null) {

            //TODO Opravit nejako ten datum, lebo to nechce zobrat
            var date = SimpleDateFormat("dd.mm.yyyy").parse(stringDate)
            return date
        }
        return null
    }
}
