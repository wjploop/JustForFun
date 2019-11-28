package com.wjp.justforfun.service.model.news

data class Page<PageItem>(
    val page:Int,
    val limit:Int,
    val list:List<PageItem>
)