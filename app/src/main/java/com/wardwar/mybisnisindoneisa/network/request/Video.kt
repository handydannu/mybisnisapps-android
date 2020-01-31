package com.wardwar.mybisnisindoneisa.network.request

import com.google.gson.reflect.TypeToken
import com.wardwar.mybisnisindoneisa.network.BaseResponse
import com.wardwar.mybisnisindoneisa.network.response.ResVideo
import com.wardwar.mybisnisindoneisa.utils.NetworkUtils

data class Video(val id: Int, val tokenUser: String, val email: String) {
    init {
        NetworkUtils.setup(tokenUser, email)
    }
    val endpoint = "/product/video/$id"
    val request = NetworkUtils.bodyRequest(this)
    val response = NetworkUtils.parser<Array<ResVideo>>(object : TypeToken<BaseResponse<Array<ResVideo>>>() {}.type)
}