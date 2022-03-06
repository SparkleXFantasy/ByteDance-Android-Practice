package com.bytedance.jstu.homework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ItemInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = this.intent
        val textId = intent.getStringExtra("textId").toString()
        Log.d("test", "Receive ID $textId")
        setContentView(R.layout.activity_item_info)
        val descriptionId = textId.toInt() - 1
        val descrption_array = resources.getStringArray(R.array.item_info_description_array)

        val item_des = findViewById<TextView>(R.id.item_info_description)
        item_des.text = descrption_array[descriptionId]
    }
}