package com.wardwar.mybisnisindoneisa.network.response

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.wardwar.mybisnisindoneisa.utils.CryptUtils

class MinimumResponse(
        val statusCode: Int,
        val message: String) {

    class Deserializer : ResponseDeserializable<MinimumResponse> {
        override fun deserialize(content: String): MinimumResponse {
            return Gson().fromJson<MinimumResponse>(CryptUtils.decrypt(content), MinimumResponse::class.java)
        }
    }
}


