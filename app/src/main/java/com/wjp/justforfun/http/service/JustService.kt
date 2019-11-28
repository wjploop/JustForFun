package com.wjp.zhihupurify.service.reposity

import com.wjp.justforfun.service.model.gallery.Picture
import com.wjp.justforfun.service.model.news.Page
import com.wjp.zhihupurify.service.model.BaseModel
import com.wjp.zhihupurify.service.model.news.News
import com.wjp.zhihupurify.service.model.news.NewsDetails
import com.wjp.zhihupurify.service.model.news.NewsType
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface JustService {

    companion object{
        const val BASE_URL="https://www.mxnzp.com/api/"
    }

    @GET("news/types")
    fun newsTypeList(): Observable<BaseModel<List<NewsType>>>

    @GET("news/list")
    fun newsList(@Query("typeId") typeId: Int = 1, @Query("page") page: Int = 1)
            : Observable<BaseModel<List<News>>>

    @GET("news/details")
    fun newsDetails(@Query("newsId") newsId: String)
            : Observable<BaseModel<NewsDetails>>


    @GET("image/girl/list")
    fun girlList(@Query("page") page:Int)
            :Observable<BaseModel<Page<Picture>>>


}