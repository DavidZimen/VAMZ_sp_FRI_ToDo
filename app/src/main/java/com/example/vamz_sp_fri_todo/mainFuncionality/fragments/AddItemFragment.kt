package com.example.vamz_sp_fri_todo.mainFuncionality.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.vamz_sp_fri_todo.R
import com.example.vamz_sp_fri_todo.app_logging.LoginHelper
import com.example.vamz_sp_fri_todo.database.data_classes.ToDoItemDC
import com.example.vamz_sp_fri_todo.mainFuncionality.view_model.MainFuncViewModel
import kotlinx.android.synthetic.main.fragment_add_item.view.*

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

        view.item_date.setOnClickListener(helper.setDatumClickListener(view, false, this))

        view.addItemButton.setOnClickListener(View.OnClickListener {
            val item = ToDoItemDC(0)

            val listId = this.requireActivity().intent.getIntExtra("ListId", -1)

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
        })

        return view
    }

}
