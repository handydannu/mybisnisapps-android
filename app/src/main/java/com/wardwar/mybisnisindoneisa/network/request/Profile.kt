package com.wardwar.mybisnisindoneisa.network.request

import com.google.gson.reflect.TypeToken
import com.wardwar.mybisnisindoneisa.network.BaseResponse
import com.wardwar.mybisnisindoneisa.network.response.ResProfile
import com.wardwar.mybisnisindoneisa.utils.NetworkUtils

data class Profile(val tokenUser: String, val email: String) {
    init {
        NetworkUtils.setup(tokenUser, email)
    }
    val endpoint = "/user/me"
    val request = NetworkUtils.bodyRequest(this)
    val response = NetworkUtils.parser<ResProfile>(object : TypeToken<BaseResponse<ResProfile>>() {}.type)
}