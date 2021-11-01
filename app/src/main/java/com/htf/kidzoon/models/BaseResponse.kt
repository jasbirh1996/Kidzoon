package com.htf.kidzoon.models

data class BaseResponse<T>(
    var `data`: T,
    var message: String
)