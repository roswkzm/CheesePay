package com.example.cheesepay.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("search_worker_glide")
fun bindGlideDefault(view : ImageView, url : String){
    if (!url.isNullOrEmpty()){
        Glide.with(view.context).load(url).circleCrop().into(view)
    }
}