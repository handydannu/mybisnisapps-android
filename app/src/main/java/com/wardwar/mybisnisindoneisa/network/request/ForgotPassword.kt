package com.wardwar.mybisnisindoneisa.network.request

import com.wardwar.mybisnisindoneisa.network.response.MinimumResponse
import com.wardwar.mybisnisindoneisa.utils.NetworkUtils


data class ForgotPassword(
        val email: String = ""
) {
    init {
        NetworkUtils.setup()
    }

    val endpoint = "/auth/forgot_password"
    val request = NetworkUtils.bodyRequest(this)
    val response = MinimumResponse.Deserializer()
}

