package com.example.vamz_sp_fri_todo.mainFuncionality.fragments

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vamz_sp_fri_todo.R
import com.example.vamz_sp_fri_todo.database.StudentDatabase
import com.example.vamz_sp_fri_todo.database.data_classes.ToDoListDC
import com.example.vamz_sp_fri_todo.mainFuncionality.rec_view_adapters.ToDoListDCAdapter
import com.example.vamz_sp_fri_todo.mainFuncionality.view_model.MainFuncViewModel
import com.example.vamz_sp_fri_todo.mainFuncionality.view_model.MainFuncViewModelFactory
import com.example.vamz_sp_fri_todo.student.Student
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {

    private lateinit var viewModel: MainFuncViewModel
    private lateinit var student: Student

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_list, container, false)

        //prebratie prihlaseneho studenta a inicializacia ViewModelu
        student = this.activity?.intent?.getSerializableExtra("student") as Student

        //vytvorenie vpremennych pre pracu s datami
        val app = requireNotNull(this.activity).application
        val db = StudentDatabase.getInstance(app).studentDatabaseDao
        val viewModelFactory = MainFuncViewModelFactory(student.osCislo_, db, app)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainFuncViewModel::class.java]

        //vytvorenie adaptera a jeho priradenie do RecyclerView pre listy
        val adapter = ToDoListDCAdapter()
        view.list_of_lists.adapter = adapter

        viewModel.lists.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it[0]?.lists!!
            }
        })

        view.add_list.setOnClickListener(View.OnClickListener {
            var listName: String
            val listNameET = EditText(this.requireContext())

            val dialogBuilder = AlertDialog.Builder(this.requireContext())
            listNameET.setRawInputType(InputType.TYPE_CLASS_TEXT)
            dialogBuilder.setView(listNameET)
            dialogBuilder.setTitle("Pridanie nového zoznamu")

            dialogBuilder.setPositiveButton("Pridať") { dialog, which ->
                listName = listNameET.text.toString()

                val toDoList = ToDoListDC(0, listName, student.osCislo_)
                viewModel.insertList(toDoList)

                Toast.makeText(this.requireContext(),
                    "List úspešne vytvorený", Toast.LENGTH_SHORT).show()
            }

            dialogBuilder.setNegativeButton("Zrušiť") { dialog, which ->
                dialog.cancel()
            }

            dialogBuilder.show()
        })

        return view
    }
}