package com.wjp.justforfun.ui.main.gallery

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.interfaces.XPopupImageLoader
import com.uber.autodispose.autoDispose
import com.wjp.justforfun.R
import com.wjp.justforfun.base.view.fragment.BaseFragment
import com.wjp.justforfun.service.model.BaseObserver
import com.wjp.justforfun.service.model.gallery.Picture
import com.wjp.justforfun.service.model.news.Page
import com.wjp.justforfun.util.io2ui
import com.wjp.justforfun.util.setImageFromUrl
import com.wjp.zhihupurify.service.model.news.News
import com.wjp.zhihupurify.service.reposity.JustService
import kotlinx.android.synthetic.main.frag_news_list.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import java.io.File

class GalleryFrag:BaseFragment() {
    override val layoutId: Int= R.layout.frag_gallery
    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
    }

    var page=0
    val per_page=10
    var isRefresh=false

    lateinit var galleryAdapter:BaseQuickAdapter<Picture,BaseViewHolder>

    val justService:JustService by instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        galleryAdapter=object:BaseQuickAdapter<Picture,BaseViewHolder>(R.layout.item_gallery){
            override fun convert(helper: BaseViewHolder, item: Picture) {
                Glide.with(helper.itemView.context).load(item.imageUrl).apply(RequestOptions().override(Target.SIZE_ORIGINAL)).into(helper.getView<ImageView>(R.id.iv))
            }
        }.apply {
            setOnItemClickListener { adapter, view, position ->
                XPopup.Builder(context).asImageViewer(view as ImageView,position, galleryAdapter.data.map { it.imageUrl }.toMutableList<Any>(),{ popupView, position ->  },object:
                    XPopupImageLoader {
                    override fun loadImage(position: Int, uri: Any, imageView: ImageView) {
                        Glide.with(imageView.context).load(uri.toString()).apply(RequestOptions().override(Target.SIZE_ORIGINAL)).into(imageView)
                    }

                    override fun getImageFile(context: Context, uri: Any): File {
                        return Glide.with(context).downloadOnly().load(uri).submit().get()
                    }

                } ).show()
            }
        }
        with(rv) {
            adapter = galleryAdapter
            layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL).apply {
            }
        }
        with(refresh) {
            setOnRefreshListener {
                page = 0
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
        justService.girlList(page)
            .io2ui()
            .autoDispose(scopeProvider)
            .subscribe(object : BaseObserver<Page<Picture>>(context!!, false) {
                override fun onSuccess(data: Page<Picture>) {
                    val shouldLoadMore=data.list.size==per_page && data.list.isNotEmpty()
                    //若返回数据达不到预定的per_page, 即表明没有更多数据
                    refresh.setEnableLoadMore(shouldLoadMore)
                    if (page == 1) {
                        galleryAdapter.setNewData(data.list.toMutableList())
                    }else{
                        galleryAdapter.addData(data.list.toMutableList())
                    }

                    val isLoadDataSuccess=data.list.isNotEmpty()
                    if (isRefresh) {
                        refresh.finishRefresh(isLoadDataSuccess)
                    }else{
                        //设置延迟，使得adapter有时间加载数据
                        refresh.finishLoadMore(isLoadDataSuccess)
                    }
                }

            })
    }




}