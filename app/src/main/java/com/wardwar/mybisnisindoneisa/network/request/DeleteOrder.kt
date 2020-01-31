package com.wardwar.mybisnisindoneisa.network.request

import com.google.gson.reflect.TypeToken
import com.wardwar.mybisnisindoneisa.network.BaseResponse
import com.wardwar.mybisnisindoneisa.network.response.MinimumResponse
import com.wardwar.mybisnisindoneisa.utils.NetworkUtils

data class DeleteOrder(
        val tokenUser: String,
        val email: String,
        val order_id: Int
) {
    init {
        NetworkUtils.setup(tokenUser, email)
    }
    val endpoint = "/product/ordered/delete"
    val request = NetworkUtils.bodyRequest(this)
    val response = MinimumResponse.Deserializer()
}