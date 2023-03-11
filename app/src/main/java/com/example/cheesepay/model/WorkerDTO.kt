package com.example.cheesepay.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WorkerDTO(
    var name : String = "",
    var socialNumber : String = "",
    var phoneNumber : String = "",
    var workerType : String = "",
    var profileUrl : String = ""
) : Parcelable
