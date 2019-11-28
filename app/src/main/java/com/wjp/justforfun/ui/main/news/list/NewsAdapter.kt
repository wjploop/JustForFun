package com.wjp.justforfun.ui.main.news.list

import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wjp.justforfun.R
import com.wjp.justforfun.util.setImageFromUrl
import com.wjp.justforfun.util.setVisibleOrGone
import com.wjp.zhihupurify.service.model.news.News

class NewsAdapter: BaseQuickAdapter<News, BaseViewHolder>(R.layout.item_news) {
    override fun convert(helper: BaseViewHolder, item: News) {
        helper.setText(R.id.tv_title,item.title)
        helper.setText(R.id.tv_digest,item.digest)
        helper.setText(R.id.tv_source,item.source)
        helper.setText(R.id.tv_post_time,item.postTime)

        helper.setVisibleOrGone(R.id.iv_thumbnail,item.imgList?.isNotEmpty())
        helper.setImageFromUrl(R.id.iv_thumbnail,item.imgList?.firstOrNull())

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val helper=super.onCreateViewHolder(parent, viewType)
        return helper
    }

}
//class NewsAdapter : ListAdapter<News, NewsAdapter.ViewHolder>(
//    ItemNewsDiffCallback()
//) {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(
//            DataBindingUtil.inflate(
//                LayoutInflater.from(parent.context),
//                R.layout.item_news, parent, false
//            )
//        )
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(getItem(position))
//    }
//
//    class ViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
//
//        init {
//            binding.clickListener = View.OnClickListener {
//                val direction =
//                    NewsListFragmentDirections.actionNewsListFragmentToNewsDetailFragment(binding.viewModel?.newsId?:"998")
//                it.findNavController().navigate(direction)
//            }
//        }
//
//        fun bind(news: News) {
//            with(binding) {
//                viewModel = ItemNewsViewModel(news)
//                executePendingBindings()
//            }
//        }
//    }
//}
//
//private class ItemNewsDiffCallback : DiffUtil.ItemCallback<News>() {
//    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
//        return oldItem.newsId == oldItem.newsId
//    }
//
//    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
//        return oldItem == newItem
//    }
//
//}