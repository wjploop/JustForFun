package com.wjp.justforfun.di

import com.wjp.justforfun.service.reposity.JustRepository
import com.wjp.justforfun.ui.main.news.list.NewsListFragment
import com.wjp.justforfun.ui.main.news.list.NewsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule= module {
    single<JustRepository> {
        JustRepository()
    }

    viewModel {
        NewsListViewModel(get())
    }


    //每次都获取一个新的实例，get（）方法可以传入相应的依赖
    // Define a factory (create a new instance each time) for type Presenter (infered parameter in <>)
    // Resolve constructor dependency with get()
//    factory<Presenter> { MyPresenter(get()) }


    scope(named<NewsListFragment>()){
        scoped<NewsListViewModel> {
            NewsListViewModel(get())
        }
    }
}