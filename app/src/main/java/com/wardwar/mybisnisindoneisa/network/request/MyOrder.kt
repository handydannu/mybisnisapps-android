package com.wardwar.mybisnisindoneisa.network.request

import com.google.gson.reflect.TypeToken
import com.wardwar.mybisnisindoneisa.network.BaseResponse
import com.wardwar.mybisnisindoneisa.network.response.ResAuth
import com.wardwar.mybisnisindoneisa.network.response.ResOrder
import com.wardwar.mybisnisindoneisa.utils.NetworkUtils

data class MyOrder(
        val tokenUser: String = "",
        val email: String = ""
){
    init {
        NetworkUtils.setup(tokenUser,email)
    }

    val endpoint = "/product/my_order"
    val request = NetworkUtils.bodyRequest(this)
    val response = NetworkUtils.parser<MutableList<ResOrder>>(object : TypeToken<BaseResponse<MutableList<ResOrder>>>() {}.type)
}
