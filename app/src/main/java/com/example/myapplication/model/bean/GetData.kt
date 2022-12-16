package com.example.myapplication.model.bean

import com.google.gson.annotations.SerializedName

class GetData {
    @SerializedName(value = "IsSuccess")
    var isSuccess: Boolean = false

    @SerializedName(value = "Message")
    var message: String = ""

    @SerializedName(value = "Data")
    var data: String = ""

    @SerializedName(value = "Extra")
    var extra: String = ""
}