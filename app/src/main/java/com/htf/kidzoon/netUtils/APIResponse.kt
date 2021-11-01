package com.htf.kidzoon.netUtils

interface APIResponse<T> {
    abstract fun onResponse(response: T?)
}