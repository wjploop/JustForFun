package com.wjp.justforfun.ui.main.news

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.google.android.material.tabs.TabLayoutMediator
import com.uber.autodispose.autoDispose
import com.wjp.justforfun.R
import com.wjp.justforfun.base.view.fragment.BaseFragment
import com.wjp.justforfun.service.model.BaseObserver
import com.wjp.justforfun.ui.main.news.list.NewsListFragment
import com.wjp.justforfun.util.io2ui
import com.wjp.zhihupurify.service.model.news.NewsType
import com.wjp.zhihupurify.service.reposity.JustService
import kotlinx.android.synthetic.main.frag_news_container.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class NewsContainerFrag:BaseFragment() {
    override val layoutId: Int = R.layout.frag_news_container
    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
    }

    private val  justService: JustService by instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        justService.newsTypeList().io2ui().autoDispose(scopeProvider)
            .subscribe(object : BaseObserver<List<NewsType>>(context!!) {
                override fun onSuccess(data: List<NewsType>) {
                    initTab(data)
                }
            })
    }

    private fun initTab(data: List<NewsType>) {
        view_pager.apply {
            adapter=object: FragmentStateAdapter(requireFragmentManager(),lifecycle){
                override fun getItemCount(): Int {
                    return data.size
                }

                override fun createFragment(position: Int): Fragment {
                    return NewsListFragment.newInstance(data[position].typeId)
                }
            }
        }
        TabLayoutMediator(tab_layout,view_pager) { tab, position ->
            tab.setText(data[position].typeName)
        }.attach()
    }


}