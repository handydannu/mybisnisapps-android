package com.wardwar.mybisnisindoneisa.network.response

data class ResProfile(
        val id: String,
        val name: String,
        val email: String,
        val company: String,
        val address: String,
        val phone: String,
        val website: String,
        val picture: String,
        val agency_id: Int

)