package com.wardwar.mybisnisindoneisa.network.request

import com.google.gson.reflect.TypeToken
import com.wardwar.mybisnisindoneisa.network.BaseResponse
import com.wardwar.mybisnisindoneisa.network.response.ResAuth
import com.wardwar.mybisnisindoneisa.utils.NetworkUtils

data class Register (
        val name: String,
        val email: String,
        val address: String,
        val phone: String,
        val city: Int,
        val company: String,
        val password: String
) {
    init {
        NetworkUtils.setup()
    }
    val endpoint = "/auth/register"
    val request = NetworkUtils.bodyRequest(this)
    val response = NetworkUtils.parser<String>(object : TypeToken<BaseResponse<String>>() {}.type)
}