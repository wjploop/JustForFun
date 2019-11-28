package com.wjp.justforfun.ui.main.news

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.wjp.justforfun.ui.main.news.list.NewsListViewModel
import com.wjp.justforfun.ui.main.news.list.NewsListViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val news_module_tag="news_module_tag"

val newsModule= Kodein.Module(news_module_tag){

    bind<NewsListViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        ViewModelProviders.of(context.activity!!,
            NewsListViewModelFactory(instance())
        )
            .get(NewsListViewModel::class.java)
    }
}