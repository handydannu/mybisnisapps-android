package com.wardwar.mybisnisindoneisa.network

import android.util.Log
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wardwar.mybisnisindoneisa.utils.CryptUtils
import java.lang.reflect.Type


class BaseResponse<T>(val statusCode: Int,
                      val message: String,
                      val data: T) {

    class Deserializer<K>(val type: Type) : ResponseDeserializable<BaseResponse<K>> {
        override fun deserialize(content: String): BaseResponse<K> {
            Log.d("JSON PARSER",CryptUtils.decrypt(content))
            return try {
                Gson().fromJson<BaseResponse<K>>(CryptUtils.decrypt(content), type)
            } catch (e: Exception) {
                Gson().fromJson<BaseResponse<K>>(CryptUtils.decrypt(content), object : TypeToken<BaseResponse<List<String>>>() {}.type)
            }
        }
    }
}