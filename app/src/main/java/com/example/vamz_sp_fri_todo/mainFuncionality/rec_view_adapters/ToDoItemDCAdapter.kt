package com.example.vamz_sp_fri_todo.mainFuncionality.rec_view_adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vamz_sp_fri_todo.R
import com.example.vamz_sp_fri_todo.database.data_classes.ToDoItemDC
import java.sql.Date

class ToDoItemDCAdapter(/*private val clickListener: (ToDoItemDC?) -> Unit*/) : RecyclerView.Adapter<ToDoItemDCAdapter.ItemViewHolder>(){

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

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = data[position]
        val res = holder.itemView.context.resources

        holder.title.text = item?.title.toString()
        holder.datum.text = Date(item?.deadline!!).toString()

        //holder.itemView.setOnClickListener {
        //    clickListener(item)
        //}
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.todo_title)
        val datum: TextView = itemView.findViewById(R.id.todo_date)
        val button: RadioButton = itemView.findViewById(R.id.rb_finished)
    }
}