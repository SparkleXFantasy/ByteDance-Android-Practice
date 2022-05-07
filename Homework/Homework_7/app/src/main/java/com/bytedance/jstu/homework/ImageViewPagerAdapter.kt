package com.bytedance.jstu.homework

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

class ImageViewPagerAdapter (private val images: List<String>, private val context: Context) : RecyclerView.Adapter<ImageViewPagerAdapter.ImageViewPagerViewHolder>() {
    inner class ImageViewPagerViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val iv = itemView.findViewById<ImageView>(R.id.iv_image)

        fun getView() : ImageView {
            return iv
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewPagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_item_view_pager, parent, false)
        return ImageViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewPagerViewHolder, position: Int) {
        Glide.with(context)
            .load(images[position])
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
            .placeholder(R.drawable.loading)
            .into(holder.getView())
    }

    override fun getItemCount(): Int {
        return images.size
    }
}