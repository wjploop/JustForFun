package com.wjp.justforfun.ui.main.news.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wjp.justforfun.base.viewmodel.BaseViewModel
import com.wjp.zhihupurify.service.reposity.JustService

class NewsListViewModel(
    private val justService: JustService
) : BaseViewModel() {


    fun fetchData(typeId:Int,page:Int) =
        justService.newsList(typeId,page)

}

@Suppress("UNCHECKED_CAST")
class NewsListViewModelFactory(val justService: JustService):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsListViewModel(justService) as T
    }

}