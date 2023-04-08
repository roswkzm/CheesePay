package com.example.cheesepay.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.cheesepay.model.TaskDTO

@BindingAdapter("circleCrop_glide")
fun bindGlideDefault(view : ImageView, url : String){
    if (!url.isNullOrEmpty()){
        Glide.with(view.context).load(url).circleCrop().into(view)
    }
}

@BindingAdapter("before_tax_pay")
fun bindBeforeTaxPay(view : TextView, taskDTO: TaskDTO){
    var inputText : Int = (taskDTO.hourPay * taskDTO.workHour)
    taskDTO.extraPay.let { extraPay ->
        inputText += extraPay!!
    }
    view.text = inputText.toString()
}