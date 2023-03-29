package com.example.cheesepay.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaskDTO(
    var date : String,
    var name : String,
    var hourPay : Int,
    var workHour : Int,
    var extraPay : Int,
    var extraDescription : String,
//    var withHoldingTax : Int,     // 원천징수금액은 계산해서 구하자
    var totalPay : Int
) : Parcelable