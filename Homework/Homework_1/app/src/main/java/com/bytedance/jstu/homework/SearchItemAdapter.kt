package com.bytedance.jstu.homework

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class SearchItemAdapter : RecyclerView.Adapter<SearchItemAdapter.SearchItemViewHolder>() {
    private val itemList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.search_item_layout, parent, false)
        return SearchItemViewHolder(v)
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        holder.bind(itemList[position])
        holder.getTextView().setOnClickListener(View.OnClickListener {
            val textId = holder.getTextView().text.split(" ")[1]
            Log.d("test", "Click On $textId")
            val intent = Intent()
            intent.setClass(it.context, ItemInfo::class.java)
            intent.putExtra("textId", textId)
            it.context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int = itemList.size

    fun updateData(list: List<String>) {
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    class SearchItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tv = view.findViewById<TextView>(R.id.search_item_tv)

        fun bind(text: String) {
            tv.text = text
        }

        fun getTextView() : TextView {
            return tv
        }
    }
}