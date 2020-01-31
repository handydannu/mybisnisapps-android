package com.wardwar.mybisnisindoneisa.network.request

import com.google.gson.reflect.TypeToken
import com.wardwar.mybisnisindoneisa.network.BaseResponse
import com.wardwar.mybisnisindoneisa.utils.NetworkUtils

data class Order(val id: Int, val tokenUser: String, val email: String,var description: String ="") {
    init {
        NetworkUtils.setup(tokenUser, email)
    }
    val endpoint = "/product/$id/order"
    val request = NetworkUtils.bodyRequest(this)
    val response = NetworkUtils.parser<String>(object : TypeToken<BaseResponse<String>>() {}.type)
}