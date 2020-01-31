package com.wardwar.mybisnisindoneisa.network.request

import com.google.gson.reflect.TypeToken
import com.wardwar.mybisnisindoneisa.network.BaseResponse
import com.wardwar.mybisnisindoneisa.utils.NetworkUtils

data class ChangePassword(val password: String, val retypePassword: String, val tokenUser: String, val email: String) {
    init {
        NetworkUtils.setup(tokenUser, email)
    }
    val endpoint = "/user/password/"
    val request = NetworkUtils.bodyRequest(this)
    val response = NetworkUtils.parser<String>(object : TypeToken<BaseResponse<String>>() {}.type)
}