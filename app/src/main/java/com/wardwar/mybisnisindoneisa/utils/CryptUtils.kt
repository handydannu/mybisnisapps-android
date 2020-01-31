package com.wardwar.mybisnisindoneisa.utils

object CryptUtils {
    fun encrypt(plainText: String) = CryptLib().encryptPlainTextWithRandomIV(plainText, Constats.KEY)
    fun decrypt(encrypted: String) = CryptLib().decryptCipherTextWithRandomIV(encrypted, Constats.KEY)
}