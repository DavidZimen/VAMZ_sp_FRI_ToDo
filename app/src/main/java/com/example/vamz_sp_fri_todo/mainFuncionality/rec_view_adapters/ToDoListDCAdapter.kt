package com.example.vamz_sp_fri_todo.mainFuncionality.rec_view_adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vamz_sp_fri_todo.R
import com.example.vamz_sp_fri_todo.database.data_classes.ToDoItemDC
import com.example.vamz_sp_fri_todo.database.data_classes.ToDoListDC

/**
 * Adaptér pre RecyclerView na zobraznie listov.
 * Udržiava v sebe povinnosti z daného listu.
 * Určuje aký vzhľad bude mať daný list.
 */
class ToDoListDCAdapter(private val clickListener: (ToDoListDC?) -> Unit) : RecyclerView.Adapter<ToDoListDCAdapter.ListViewHolder>() {

    var data = listOf<ToDoListDC?>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item, parent, false)

        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = data[position]
        val res = holder.itemView.context.resources

        holder.listName.text = item?.listName

        holder.itemView.setOnClickListener {
            clickListener(item)
        }
    }

    class ListViewHolder(listView: View) : RecyclerView.ViewHolder(listView) {
        val listName: TextView = listView.findViewById(R.id.list_title)
    }
}