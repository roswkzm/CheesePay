package com.example.cheesepay.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaskDTO(
    var date : String = "",     //  날짜
    var name : String = "",     // 이름
    var hourPay : Long = 0,      // 시급
    var workHour : Long = 0,     // 근무시간
    var extraPay : Long? = null,     // 추가금액
    var extraDescription : String? = null,      // 추가금액 지급 사유
//    var withHoldingTax : Int,     // 원천징수금액은 계산해서 구하자
    var totalPay : Long = 0         // 세후금액
) : Parcelable