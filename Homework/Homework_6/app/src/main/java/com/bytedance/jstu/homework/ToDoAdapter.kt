package com.bytedance.jstu.homework

import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(private val todoList: List<ToDoData>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private var todoTaskList = mutableListOf<ToDoData>()

    inner class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTodo = view.findViewById<TextView>(R.id.tv_todo_item)
        private val cbTodo = view.findViewById<CheckBox>(R.id.cb_todo_item)

        fun bindView(todoDataPack : ToDoData) {
            tvTodo.text = todoDataPack.description
            cbTodo.isChecked = todoDataPack.status != "do"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = View.inflate(parent.context, R.layout.activity_todo_item, null)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todoDataPack = todoTaskList[position]
        holder.bindView(todoDataPack)
    }

    override fun getItemCount(): Int = todoTaskList.size

    fun updateTaskList(list: List<ToDoData>) {
        todoTaskList.clear()
        todoTaskList.addAll(list)
        notifyDataSetChanged()
    }




}