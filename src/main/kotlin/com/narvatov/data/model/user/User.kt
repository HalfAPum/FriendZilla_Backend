package com.narvatov.data.model.user

data class User(
    val id: String,
)

data class AdditionalUserInfo(
    val id: String,
    val latitude: Double?,
    val longitude: Double?,
)
