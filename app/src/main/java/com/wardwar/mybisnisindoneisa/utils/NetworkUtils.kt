package com.wardwar.mybisnisindoneisa.utils

import com.github.kittinunf.fuel.core.FuelManager
import com.google.gson.Gson
import com.wardwar.mybisnisindoneisa.network.BaseResponse
import com.wardwar.mybisnisindoneisa.network.response.MinimumResponse
import com.wardwar.mybisnisindoneisa.network.response.ResAuth
import java.lang.reflect.Type

object NetworkUtils {
    fun setup() {
        FuelManager.instance.apply {
            basePath = Constats.BASE_URL
        }
    }

    fun setup(token: String,email:String) {
        FuelManager.instance.apply {
            basePath = Constats.BASE_URL
            baseHeaders = mapOf("x-access-token" to token,"x-email" to email)
        }
    }

    fun <T> parser(type: Type) = BaseResponse.Deserializer<T>(type)
    fun minimum() = MinimumResponse.Deserializer()
    fun serialize(model: Any) = Gson().toJson(model)
    fun crypted(model: Any) = CryptUtils.encrypt(serialize(model))
    fun bodyRequest(model: Any) = listOf("data" to crypted(model))

}