package com.wardwar.mybisnisindoneisa.network.request

import com.google.gson.reflect.TypeToken
import com.wardwar.mybisnisindoneisa.models.PhotoSlide
import com.wardwar.mybisnisindoneisa.network.BaseResponse
import com.wardwar.mybisnisindoneisa.utils.NetworkUtils

data class Iklan(val tokenUser: String, val email:String) {
    init {
        NetworkUtils.setup(tokenUser, email)
    }
    val endpoint = "/advert"
    val request = NetworkUtils.bodyRequest(this)
    val response = NetworkUtils.parser<MutableList<String>>(object : TypeToken<BaseResponse<MutableList<String>>>() {}.type)
}