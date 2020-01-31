package com.wardwar.mybisnisindoneisa.network.request

import com.google.gson.reflect.TypeToken
import com.wardwar.mybisnisindoneisa.network.BaseResponse
import com.wardwar.mybisnisindoneisa.network.response.ResAuth
import com.wardwar.mybisnisindoneisa.utils.NetworkUtils


data class Auth(
        val email: String = "",
        val password: String = ""
){
    init {
        NetworkUtils.setup()
    }
    val endpoint = "/auth/login"
    val request = NetworkUtils.bodyRequest(this)
    val response = NetworkUtils.parser<ResAuth>(object : TypeToken<BaseResponse<ResAuth>>() {}.type)
}

