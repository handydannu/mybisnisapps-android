package com.wardwar.mybisnisindoneisa.network.request

import com.google.gson.reflect.TypeToken
import com.wardwar.mybisnisindoneisa.network.BaseResponse
import com.wardwar.mybisnisindoneisa.utils.NetworkUtils

data class Product(val id: Int, val tokenUser: String, val email: String) {
    init {
        NetworkUtils.setup(tokenUser, email)
    }
    val endpoint = "/product/$id"
    val request = NetworkUtils.bodyRequest(this)
    val response = NetworkUtils.parser<String>(object : TypeToken<BaseResponse<String>>() {}.type)
}