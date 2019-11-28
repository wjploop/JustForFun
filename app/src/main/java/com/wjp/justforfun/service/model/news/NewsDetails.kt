package com.wjp.zhihupurify.service.model.news

data class NewsDetails(
    val content: String?,
    val cover: String?,
    val docid: String?,
    val images: List<Image>?,
    val ptime: String?,
    val source: String?,
    val title: String?
)

data class Image(
    val imgSrc: String,
    val position: String,
    val size: String?
)