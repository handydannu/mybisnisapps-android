package com.wardwar.mybisnisindoneisa.network.response

data class ResOrder(
        val order_id: Int,
        val user_id: Int,
        val user_name: String,
        val product_id: Int,
        val product_name: String,
        val description: String,
        val follow_up: Int,
        val marketing_id:Int = 0,
        val marketing_name:String = ""
)