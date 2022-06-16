package com.example.vamz_sp_fri_todo.calendar

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vamz_sp_fri_todo.R
import com.example.vamz_sp_fri_todo.database.StudentDatabase
import com.example.vamz_sp_fri_todo.mainFuncionality.rec_view_adapters.ToDoItemDCAdapter
import com.example.vamz_sp_fri_todo.mainFuncionality.view_model.MainFuncViewModel
import com.example.vamz_sp_fri_todo.student.Student
import kotlinx.android.synthetic.main.fragment_calendar.view.*
import kotlinx.android.synthetic.main.to_do_item_dialog.view.*
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class CalendarFragment : Fragment() {

    private lateinit var viewModel: MainFuncViewModel
    private lateinit var student: Student

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_calendar, container, false)

        //prebratie prihlaseneho studenta a inicializacia ViewModelu
        student = this.activity?.intent?.getSerializableExtra("student") as Student

        //ziskanie instanciew viewModelu
        val app = requireNotNull(this.activity).application
        val db = StudentDatabase.getInstance(app).studentDatabaseDao
        viewModel = MainFuncViewModel(student.osCislo_, db, app)

        val adapter = createAdapter()
        view.calednar_rv.adapter = adapter

        viewModel.itemsWithDate?.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        view.calendarView.setOnDateChangeListener(
            CalendarView.OnDateChangeListener { view, year, month, dayOfMonth ->
                val date = view.calendarView.date
                viewModel.getItemsWithDate(date)
        })

        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createAdapter() : ToDoItemDCAdapter {
        return ToDoItemDCAdapter({
            val dialogBuilder = AlertDialog.Builder(this.requireContext())
            dialogBuilder.setTitle("Popis o povinnosti")

            //nastavenie vlastneho layoutu
            val view = layoutInflater.inflate(R.layout.to_do_item_dialog, null)
            dialogBuilder.setView(view)

            //nastavenie textov do textView
            view.tw_title.text = it?.title
            val date: LocalDate = Instant.ofEpochMilli(it?.deadline!!).atZone(ZoneId.systemDefault()).toLocalDate()
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            view.tw_date.text = date.format(formatter)
            view.tw_popis.text = it.description


            dialogBuilder.setNegativeButton("Zavrieť") { dialog, which ->
                dialog.cancel()
            }

            dialogBuilder.show()
        }, viewModel)
    }
}