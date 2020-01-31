package com.wardwar.mybisnisindoneisa.network.request

import com.google.gson.reflect.TypeToken
import com.wardwar.mybisnisindoneisa.network.BaseResponse
import com.wardwar.mybisnisindoneisa.network.response.ResAuth
import com.wardwar.mybisnisindoneisa.utils.NetworkUtils

data class EditProfile (
        val name: String,
        val email: String,
        val address: String,
        val phone: String,
        val agencyId: Int,
        val company: String,
        val token: String
) {
    init {
        NetworkUtils.setup()
    }
    val endpoint = "/user/update"
    val request = NetworkUtils.bodyRequest(this)
    val response = NetworkUtils.parser<String>(object : TypeToken<BaseResponse<String>>() {}.type)
}