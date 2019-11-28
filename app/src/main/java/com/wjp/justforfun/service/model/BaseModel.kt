package com.wjp.zhihupurify.service.model

data class BaseModel<Data>(
    val code: Int,
    val msg: String,
    val data: Data?
)