package com.example.vamz_sp_fri_todo.mainFuncionality.fragments

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.vamz_sp_fri_todo.R
import com.example.vamz_sp_fri_todo.database.StudentDatabase
import com.example.vamz_sp_fri_todo.database.data_classes.ToDoListDC
import com.example.vamz_sp_fri_todo.mainFuncionality.MainFuncionalityActivity
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

        val activity = this.requireActivity() as MainFuncionalityActivity
        activity.setActionBarTitle("ToDo Zoznamy")

        //prebratie prihlaseneho studenta a inicializacia ViewModelu
        student = this.activity?.intent?.getSerializableExtra("student") as Student

        //ziskanie instanciew viewModelu
        val viewModelFactory = this.requireActivity().defaultViewModelProviderFactory
        viewModel = ViewModelProvider(this, viewModelFactory)[MainFuncViewModel::class.java]

        //vytvorenie adaptera s onClickListenerom a jeho priradenie do RecyclerView pre listy
        val adapter = ToDoListDCAdapter() {
            viewModel.getItemsOfList(it?.listId!!)
            requireActivity().intent.putExtra("ListId", it.listId)
            Navigation.findNavController(view).navigate(R.id.action_listFragment_to_itemsFragment)
        }
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

        val ith = getItemTouchHelper(view, this.requireContext())
        ith.attachToRecyclerView(view.list_of_lists)

        return view
    }


    private fun getItemTouchHelper(view: View, context: Context): ItemTouchHelper {
        val mIth = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: ViewHolder, target: ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                    val position = viewHolder.layoutPosition

                    when (direction) {
                        ItemTouchHelper.LEFT -> {

                            val dialogBuilder = AlertDialog.Builder(context)
                            dialogBuilder.setTitle("Odstrániť zoznam")
                            dialogBuilder.setMessage("Odstránením zoznamu sa odstánia aj ToDo vrámci zoznamu. Prejete si aj tak vymazať ?")

                            dialogBuilder.setPositiveButton("Napriek tomu vymazať") { dialog, which ->
                                viewModel.removeListAtPosition(position)
                                Toast.makeText(context,
                                    "Zoznam zmazaný.", Toast.LENGTH_SHORT).show()
                            }

                            dialogBuilder.setNegativeButton("Nevymazať") { dialog, which ->
                                dialog.cancel()
                            }

                            dialogBuilder.show()
                        }
                    }
                }
            })

        return mIth
    }
}
