package com.example.vamz_sp_fri_todo.mainFuncionality.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.vamz_sp_fri_todo.R
import com.example.vamz_sp_fri_todo.database.StudentDatabase
import com.example.vamz_sp_fri_todo.mainFuncionality.view_model.MainFuncViewModel
import com.example.vamz_sp_fri_todo.mainFuncionality.view_model.MainFuncViewModelFactory
import com.example.vamz_sp_fri_todo.student.Student

class ItemsFragment : Fragment() {

    private lateinit var viewModel: MainFuncViewModel
    private lateinit var student: Student

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_items, container, false)

        //prebratie prihlaseneho studenta a inicializacia ViewModelu
        student = this.activity?.intent?.getSerializableExtra("student") as Student

        //vytvorenie vpremennych pre pracu s datami
        val app = requireNotNull(this.activity).application
        val db = StudentDatabase.getInstance(app).studentDatabaseDao
        val viewModelFactory = MainFuncViewModelFactory(student.osCislo_, db, app)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainFuncViewModel::class.java]

        return view
    }

}