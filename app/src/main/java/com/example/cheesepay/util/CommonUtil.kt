package com.example.cheesepay.util

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.round

class CommonUtil @Inject constructor(

){

     fun getSelectDateTime(date: Date) : String {
        return SimpleDateFormat(dateFormat).format(date)
    }

    fun getWithHoldingTax(hourPay : Int, workHour : Int) : Int{
        return round((hourPay * workHour).div(100).times(3.3)).toInt()
    }

    companion object{
        const val PICK_IMAGE_FROM_ALBUM = 999
        const val dateFormat = "yyyy-MM-dd"
    }
}