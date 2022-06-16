package com.example.vamz_sp_fri_todo.mainFuncionality.rec_view_adapters

import android.annotation.SuppressLint
import android.os.Build.VERSION_CODES.O
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.vamz_sp_fri_todo.R
import com.example.vamz_sp_fri_todo.database.data_classes.ToDoItemDC
import com.example.vamz_sp_fri_todo.mainFuncionality.view_model.MainFuncViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ToDoItemDCAdapter(private val clickListener: (ToDoItemDC?) -> Unit, private val viewModel: MainFuncViewModel) : RecyclerView.Adapter<ToDoItemDCAdapter.ItemViewHolder>(){

    var data = listOf<ToDoItemDC?>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.todo_item, parent, false)

        return ItemViewHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(O)
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = data[position]
        val res = holder.itemView.context.resources

        val date: LocalDate = Instant.ofEpochMilli(item?.deadline!!).atZone(ZoneId.systemDefault()).toLocalDate()
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

        holder.title.text = item.title
        holder.datum.text = date.format(formatter)

        if (item.status == 1) {
            holder.checkBox.isChecked = true
        }

        holder.itemView.setOnClickListener {
           clickListener(item)
        }

        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                1.also { item.status = it }
                viewModel.updateItem(item)
            } else  {
                0.also { item.status = it }
                viewModel.updateItem(item)
            }
        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.todo_title)
        val datum: TextView = itemView.findViewById(R.id.todo_date)
        val checkBox: CheckBox = itemView.findViewById(R.id.chb_finished)
    }
}