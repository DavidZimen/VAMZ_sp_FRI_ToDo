package com.example.vamz_sp_fri_todo.mainFuncionality.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.view.marginLeft
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.vamz_sp_fri_todo.R
import com.example.vamz_sp_fri_todo.database.StudentDatabase
import com.example.vamz_sp_fri_todo.mainFuncionality.MainFuncionalityActivity
import com.example.vamz_sp_fri_todo.mainFuncionality.rec_view_adapters.ToDoItemDCAdapter
import com.example.vamz_sp_fri_todo.mainFuncionality.view_model.MainFuncViewModel
import com.example.vamz_sp_fri_todo.mainFuncionality.view_model.MainFuncViewModelFactory
import com.example.vamz_sp_fri_todo.student.Student
import kotlinx.android.synthetic.main.fragment_items.view.*
import kotlinx.android.synthetic.main.to_do_item_dialog.view.*
import kotlinx.android.synthetic.main.toolbar.*
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ItemsFragment : Fragment() {

    private lateinit var viewModel: MainFuncViewModel
    private lateinit var student: Student

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_items, container, false)

        //ziskanie instanciew viewModelu
        val viewModelFactory = this.requireActivity().defaultViewModelProviderFactory
        viewModel = ViewModelProvider(this, viewModelFactory)[MainFuncViewModel::class.java]

        val listId = this.requireActivity().intent.getIntExtra("ListId", -1)
        viewModel.getItemsOfList(listId)

        val activity = this.requireActivity() as MainFuncionalityActivity
        activity.setActionBarTitle(viewModel.items?.value?.get(0)?.toDoList?.listName.toString())

        //prebratie prihlaseneho studenta a inicializacia ViewModelu
        student = this.activity?.intent?.getSerializableExtra("student") as Student

        //vytvorenie adaptera s onClickListenerom a jeho priradenie do RecyclerView pre itemy
        val adapter = createAdapter()
        view.list_of_items.adapter = adapter

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

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("InflateParams")
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






































