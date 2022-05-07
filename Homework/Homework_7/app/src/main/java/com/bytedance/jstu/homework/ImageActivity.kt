package com.bytedance.jstu.homework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2

class ImageActivity : AppCompatActivity() {
    private lateinit var imageResourceList : List<String>
    private lateinit var imageViewAdapter : ImageViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        Log.d("Activity", "ImageActivity")
        bindImageResources()
        bindViewPagerAdapter()
    }

    private fun bindImageResources() {
        imageResourceList = listOf(
            "https://images.unsplash.com/photo-1536329583941-14287ec6fc4e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80",
            "https://images.unsplash.com/photo-1651858723178-0924dc62a082?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=765&q=80",
            "https://images.unsplash.com/photo-1651821486773-7f915d891685?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1887&q=80",
            "https://images.unsplash.com/photo-1651354081016-7f7f02b14f62?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80",
            "https://media0.giphy.com/media/OqFpgF7bet1sRoCmpb/giphy.gif?cid=ecf05e47tuwxisoxabbco0frc9efifc64d7taxsf7nz8k5ix&rid=giphy.gif&ct=g"
        )
    }

    private fun bindViewPagerAdapter() {
        imageViewAdapter = ImageViewPagerAdapter(imageResourceList, this)
        val viewPager = findViewById<ViewPager2>(R.id.image_viewpager)
        viewPager.adapter = imageViewAdapter
    }
}