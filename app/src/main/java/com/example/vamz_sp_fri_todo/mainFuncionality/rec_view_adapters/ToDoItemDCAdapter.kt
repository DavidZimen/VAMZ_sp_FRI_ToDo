package com.example.vamz_sp_fri_todo.mainFuncionality.rec_view_adapters

import android.annotation.SuppressLint
import android.os.Build.VERSION_CODES.O
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.vamz_sp_fri_todo.R
import com.example.vamz_sp_fri_todo.database.data_classes.ToDoItemDC
import kotlinx.android.synthetic.main.todo_item.view.*
import java.sql.Date
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ToDoItemDCAdapter(private val clickListener: (ToDoItemDC?) -> Unit) : RecyclerView.Adapter<ToDoItemDCAdapter.ItemViewHolder>(){

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

        holder.title.text = item.title.toString()
        holder.datum.text = date.format(formatter)

        holder.itemView.setOnClickListener {
           clickListener(item)
        }

        holder.checkBox.setOnCheckedChangeListener {buttonView, isChecked ->
            if (isChecked) {
                1.also { item.status = it }
                holder.title.setTextColor(R.color.green)
            } else  {
                0.also { item.status = it }
                holder.title.setTextColor(R.color.black)
            }
        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.todo_title)
        val datum: TextView = itemView.findViewById(R.id.todo_date)
        val checkBox: CheckBox = itemView.findViewById(R.id.chb_finished)
    }
}