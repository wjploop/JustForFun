package com.wjp.justforfun.ui.main.news.detail

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.wjp.justforfun.base.viewmodel.BaseViewModel
import com.wjp.justforfun.service.reposity.JustRepository
import com.wjp.zhihupurify.service.model.news.NewsDetails

class NewsDetailViewModel(
    repository: JustRepository,
    var newsId: String
):BaseViewModel() {


    val newsDetail: LiveData<NewsDetails> = repository.newsDetails(newsId)

}