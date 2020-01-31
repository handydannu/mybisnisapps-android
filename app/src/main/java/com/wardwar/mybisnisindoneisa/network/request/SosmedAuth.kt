package com.wardwar.mybisnisindoneisa.network.request

import com.google.gson.reflect.TypeToken
import com.wardwar.mybisnisindoneisa.network.BaseResponse
import com.wardwar.mybisnisindoneisa.network.response.ResAuth
import com.wardwar.mybisnisindoneisa.utils.NetworkUtils


data class SosmedAuth(
        val name: String = "",
        val email: String = "",
        val picture: String = ""
){
    init {
        NetworkUtils.setup()
    }
    val endpoint = "/auth/login_sosmed"
    val request = NetworkUtils.bodyRequest(this)
    val response = NetworkUtils.parser<ResAuth>(object : TypeToken<BaseResponse<ResAuth>>() {}.type)
}

