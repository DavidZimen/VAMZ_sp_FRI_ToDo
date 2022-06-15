package com.example.vamz_sp_fri_todo.mainFuncionality.fragments

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.vamz_sp_fri_todo.R
import com.example.vamz_sp_fri_todo.app_logging.LoginHelper
import com.example.vamz_sp_fri_todo.database.data_classes.ToDoItemDC
import com.example.vamz_sp_fri_todo.mainFuncionality.MainFuncionalityActivity
import com.example.vamz_sp_fri_todo.mainFuncionality.view_model.MainFuncViewModel
import kotlinx.android.synthetic.main.activity_logging.*
import kotlinx.android.synthetic.main.fragment_add_item.view.*
import kotlinx.android.synthetic.main.fragment_register.view.*
import java.util.*

class AddItemFragment : Fragment() {

    private lateinit var helper: LoginHelper
    private lateinit var viewModel: MainFuncViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_item, container, false)

        //inicializacie helper triedy
        helper = LoginHelper()

        //ziskanie instanciew viewModelu
        val viewModelFactory = this.requireActivity().defaultViewModelProviderFactory
        viewModel = ViewModelProvider(this, viewModelFactory)[MainFuncViewModel::class.java]

        val activity = this.requireActivity() as MainFuncionalityActivity
        activity.setActionBarTitle("Nová povinnosť")

        view.item_date.setOnClickListener(setDatumClickListener(view))

        view.addItemButton.setOnClickListener(View.OnClickListener {
            val item = ToDoItemDC(0)

            val editTextList = mutableListOf<EditText>()
            editTextList.add(view.item_title)
            editTextList.add(view.item_date)

            val listId = this.requireActivity().intent.getIntExtra("ListId", -1)

            if (helper.checkEmptiness(editTextList)) {
                if (listId != -1) {
                    item.listId = listId
                    item.title = view.item_title.text.toString()
                    item.deadline = helper.convertStringToDate(view.item_date.text.toString())?.time!!
                    item.description = view.item_popis.text.toString()

                    viewModel.insertItem(item)

                    viewModel.getItemsOfList(listId)
                    Navigation.findNavController(view).navigate(R.id.action_addItemFragment_to_itemsFragment)
                } else {
                    Toast.makeText(this.requireContext(), "Ups niečo sa pokazilo.", Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(view).navigate(R.id.action_addItemFragment_to_itemsFragment)
                }
            }
        })

        return view
    }



    /**
     * Vytvorí OnClickListener pre výber dátumu.
     * Pre zjednodušenie prehľadnosti kódu.
     */
    private fun setDatumClickListener(view: View) : View.OnClickListener {

        return  View.OnClickListener {
            //ziskanie pristupu ku polu s datumom narodenia
            val birthDateEditText = view.item_date
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
            dpd?.datePicker?.minDate = System.currentTimeMillis()
            dpd?.show()
        }
    }

}
