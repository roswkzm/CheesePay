package com.example.cheesepay.util

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.round

class CommonUtil @Inject constructor(

){

     fun getSelectDateTime(date: Date) : String {
        return SimpleDateFormat(YearMonthDayDateFormat).format(date)
    }

    fun getYearMonth(stringDate : String) : String {
        val beforeDate = SimpleDateFormat(YearMonthDayDateFormat).parse(stringDate)
        return SimpleDateFormat(YearMonthDateFormat).format(beforeDate)
    }

    fun getWithHoldingTax(hourPay : Int, workHour : Int) : Int{
        return round((hourPay * workHour).div(100).times(3.3)).toInt()
    }

    companion object{
        const val PICK_IMAGE_FROM_ALBUM = 999
        const val YearMonthDayDateFormat = "yyyy-MM-dd"
        const val YearMonthDateFormat = "yyyy-MM"
    }
}