package com.wjp.zhihupurify.service.model.news

data class News(
    val digest: String?,
    val imgList: List<String>?,
    val newsId: String,
    val postTime: String?,
    val source: String?,
    val title: String?,
    val videoList: Any?
)