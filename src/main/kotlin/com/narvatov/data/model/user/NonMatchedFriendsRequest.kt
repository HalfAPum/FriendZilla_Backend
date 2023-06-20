package com.narvatov.data.model.user

data class NonMatchedFriendsRequest(
    val userId: String,
    val limit: Int,
)