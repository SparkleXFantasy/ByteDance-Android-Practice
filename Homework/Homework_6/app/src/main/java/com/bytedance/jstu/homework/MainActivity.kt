package com.bytedance.jstu.homework

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var btnAddItem : Button? = null
    private var btnDeleteSelect : Button? = null
    private var etItem : EditText? = null
    private var toDoList = mutableListOf<ToDoData>()
    private var toDoAdapter : TodoAdapter? = null
    private var dbHelper = DBOpenHelper(this, "to_do_base.db", 1)
    private var db: SQLiteDatabase? = null

    private fun bindView() {
        btnAddItem = findViewById(R.id.btn_add_item)
        btnDeleteSelect = findViewById(R.id.btn_delete_select)
        etItem = findViewById(R.id.et_todo_item)
    }

    private fun setClickListener() {
        btnAddItem?.setOnClickListener (this)
        btnDeleteSelect?.setOnClickListener (this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindView()
        db = dbHelper.writableDatabase
        toDoAdapter = TodoAdapter(toDoList)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        readDBList()
        toDoAdapter?.updateTaskList(toDoList)
        recyclerView.adapter = toDoAdapter
        setClickListener()
    }

    fun deleteTask(view: View){
        val parent = view.parent as View
        val taskTextView = parent.findViewById<TextView>(R.id.tv_todo_item)
        val task = taskTextView.text.toString()
        val values = ContentValues().apply {
            put("status", "done")
        }
        db?.update("todolist",values,"description = ?", arrayOf(task))
        readDBList()
    }

    private fun readDBList() {
        toDoList.clear()
        val cursor = (db?: dbHelper.writableDatabase).query(
            "todolist",
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )
        if (cursor.moveToFirst()) {
            do {
                val task = cursor.getString(cursor.getColumnIndexOrThrow("description"))
                val status = cursor.getString(cursor.getColumnIndexOrThrow("status"))
                toDoList.add(ToDoData(task, status))
            } while (cursor.moveToNext())
        }
        cursor.close()
    }

    override fun onClick(p0: View?) {
        when (p0) {
            btnAddItem -> {
                val values = ContentValues().apply {
                    put("description", "${etItem?.text}")
                    put("status", "do")//新加入的都带上未完成标签
                }
                db?.insert("todolist", null, values)
                readDBList()
                toDoAdapter?.updateTaskList(toDoList)
            }
            btnDeleteSelect -> {
                readDBList()
                db?.delete("todolist", "status = ?", arrayOf("done"))
                readDBList()
                toDoAdapter?.updateTaskList(toDoList)
            }
        }
    }
}