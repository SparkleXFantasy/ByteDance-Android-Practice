package com.bytedance.jstu.homework

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecycleViewDemo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_view_demo)
        val data = (1..5).map { "Problem $it" }

        val adapter = SearchItemAdapter()
        adapter.updateData(data)
        val rv = findViewById<RecyclerView>(R.id.recycle_view)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
    }
}