package com.example.vamz_sp_fri_todo.mainFuncionality.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.vamz_sp_fri_todo.R
import com.example.vamz_sp_fri_todo.database.StudentDatabase
import com.example.vamz_sp_fri_todo.mainFuncionality.rec_view_adapters.ToDoItemDCAdapter
import com.example.vamz_sp_fri_todo.mainFuncionality.view_model.MainFuncViewModel
import com.example.vamz_sp_fri_todo.mainFuncionality.view_model.MainFuncViewModelFactory
import com.example.vamz_sp_fri_todo.student.Student
import kotlinx.android.synthetic.main.fragment_items.view.*

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

        //ziskanie instanciew viewModelu
        val viewModelFactory = this.requireActivity().defaultViewModelProviderFactory
        viewModel = ViewModelProvider(this, viewModelFactory)[MainFuncViewModel::class.java]

        //vytvorenie adaptera s onClickListenerom a jeho priradenie do RecyclerView pre itemy
        val adapter = ToDoItemDCAdapter()
        view.list_of_items.adapter = adapter

        val listId = this.requireActivity().intent.getIntExtra("ListId", -1)
        viewModel.getItemsOfList(listId)

        viewModel.items?.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it[0]?.toDoItems!!
            }
        })

        view.add_item.setOnClickListener(View.OnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_itemsFragment_to_addItemFragment)
        })

        return view
    }

}