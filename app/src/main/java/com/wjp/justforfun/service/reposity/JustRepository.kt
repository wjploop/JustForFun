package com.wjp.justforfun.service.reposity

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wjp.justforfun.service.model.BaseObserver
import com.wjp.zhihupurify.service.model.BaseModel
import com.wjp.zhihupurify.service.model.news.News
import com.wjp.zhihupurify.service.model.news.NewsDetails
import com.wjp.zhihupurify.service.reposity.JustService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class JustRepository() {


//    companion object {
//        var sInstance:JustRepository?=null
//
//        fun getInstance(context: Context)=
//            sInstance?: synchronized(this){
//                sInstance?:JustRepository(context).also { sInstance=it }
//            }
//    }

    val justService: JustService = Retrofit.Builder()
        .baseUrl(JustService.BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        )
        .build()
        .create(JustService::class.java)


    fun newsList(newsTypeId: Int, page: Int,context:Context,showProgress:Boolean=false): LiveData<List<News>> {
        val list = MutableLiveData<List<News>>()
        justService.newsList(newsTypeId, page).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object:BaseObserver<List<News>>(context,showProgress){
                override fun onSuccess(data: List<News>) {
                    list.value=data
                }
            })
        return list
    }

    fun newsDetails(newsId: String): LiveData<NewsDetails> {
        val data = MutableLiveData<NewsDetails>()
        justService.newsDetails(newsId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseModel<NewsDetails>?> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: BaseModel<NewsDetails>) {
                    if (t.data != null) {
                        data.value = t.data
                    }
                }

                override fun onError(e: Throwable) {

                }
            })
        return data
    }


}