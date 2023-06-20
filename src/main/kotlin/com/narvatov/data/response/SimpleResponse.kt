package com.narvatov.data.response

data class SimpleResponse(
    val success: Boolean,
    val message: String,
)

fun OkResponse() = SimpleResponse(true, "Operation completed successfully")