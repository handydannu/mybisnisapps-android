package com.wardwar.mybisnisindoneisa.network.request

import com.google.gson.reflect.TypeToken
import com.wardwar.mybisnisindoneisa.network.BaseResponse
import com.wardwar.mybisnisindoneisa.network.response.MinimumResponse
import com.wardwar.mybisnisindoneisa.utils.NetworkUtils

data class ChangePicture(val tokenUser: String, val email: String, var picture: String) {
    init {
        NetworkUtils.setup(tokenUser, email)
    }
    val endpoint = "/user/picture"
    val request = NetworkUtils.bodyRequest(this)
    val response = MinimumResponse.Deserializer()
}