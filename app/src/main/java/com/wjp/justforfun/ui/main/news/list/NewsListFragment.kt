package com.wjp.justforfun.ui.main.news.list

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.uber.autodispose.autoDispose
import com.wjp.justforfun.R
import com.wjp.justforfun.base.view.fragment.BaseFragment
import com.wjp.justforfun.service.model.BaseObserver
import com.wjp.justforfun.ui.main.MainFragmentDirections
import com.wjp.justforfun.ui.main.news.newsModule
import com.wjp.justforfun.util.io2ui
import com.wjp.zhihupurify.service.model.news.News
import kotlinx.android.synthetic.main.frag_news_list.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class NewsListFragment : BaseFragment() {
    override val layoutId: Int = R.layout.frag_news_list
    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(newsModule)
    }

    val newsAdapter = NewsAdapter()

    var page = 1
    val per_page = 10
     var typeId:Int =0

    var isRefresh = false

    private val viewModel: NewsListViewModel by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        typeId=arguments?.getInt("typeId",509)?:509
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsAdapter.apply {
            setOnItemClickListener { adapter, view, position ->
                val item=adapter.data[position] as News
//                childFragmentManager.beginTransaction().apply {
//                    add()
//                }
                val direction=MainFragmentDirections.actionMainToNewsDetail(item.newsId)
                findNavController().navigate(direction)
            }
        }
        with(rv) {
            adapter = newsAdapter
        }
        with(refresh) {
            setOnRefreshListener {
                page = 1
                isRefresh = true
                fetchData()
            }
            setOnLoadMoreListener {
                page++
                isRefresh = false
                fetchData()
            }
        }

        fetchData()
    }

    private fun fetchData() {
        viewModel.fetchData(typeId, page)
            .io2ui()
            .autoDispose(scopeProvider)
            .subscribe(object : BaseObserver<List<News>>(context!!, false) {
                override fun onSuccess(data: List<News>) {
                    //若返回数据达不到预定的per_page, 即表明没有更多数据
                    val shouldLoadMore=data.size==per_page && data.isNotEmpty()
                    refresh.setEnableLoadMore(shouldLoadMore)
                    if (page == 1) {
                        newsAdapter.setNewData(data)
                    }else{
                        newsAdapter.addData(data)
                    }

                    val isLoadDataSuccess=data.isNotEmpty()
                    if (isRefresh) {
                        refresh.finishRefresh(isLoadDataSuccess)
                    }else{
                        //设置延迟，使得adapter有时间加载数据
                        refresh.finishLoadMore(isLoadDataSuccess)
                    }
                }
            })
    }

    companion object{
        fun newInstance(typeId:Int)=
            NewsListFragment().apply {
                arguments=Bundle().apply {
                    putInt("typeId",typeId)
                }
            }

    }

}