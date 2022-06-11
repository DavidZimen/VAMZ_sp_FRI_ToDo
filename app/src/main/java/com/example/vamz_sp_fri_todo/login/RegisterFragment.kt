package com.example.vamz_sp_fri_todo.login

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vamz_sp_fri_todo.R
import kotlinx.android.synthetic.main.fragment_register.view.*
import java.util.*

/**
 * Vytvorí fragmet pre obsluhu registrácie do systému.
 */
class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        view.datum.setOnClickListener(setDatumClickListener(view))

        view.registerButton.setOnClickListener {
            val mail = view.email.toString()
            val meno = view.meno.toString()
            val priezvisko = view.priezvisko.toString()
        }

        return view
    }

    /**
     * Vytvorí OnClickListener pre výber dátumu.
     * Pre zjednodušenie prehľadnosti kódu.
     */
    fun setDatumClickListener(view: View) : View.OnClickListener {

        return  View.OnClickListener {
            //ziskanie pristupu ku polu s datumom narodenia
            val birthDateEditText = view.datum
            birthDateEditText.showSoftInputOnFocus = false

            //vytvorenie instancie kalendara a ziskanie jednotlivych casti do premennych
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            //vytvorenie listenera pre datum a nastavenie datumu do textView
            val dateSetListener =
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    birthDateEditText.setText("$dayOfMonth.$monthOfYear.$year")
                }

            //vytvorenie a zobrazenie dialogu pre vyber datumu
            activity?.let { it1 -> DatePickerDialog(it1, dateSetListener, year, month, day) }
                ?.show()
        }
    }
}
