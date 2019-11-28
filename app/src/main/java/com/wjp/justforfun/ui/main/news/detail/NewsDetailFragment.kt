package com.wjp.justforfun.ui.main.news.detail

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.BaseRequestOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.interfaces.XPopupImageLoader
import com.wjp.justforfun.R
import com.wjp.justforfun.base.ext.toast
import com.wjp.justforfun.base.view.fragment.BaseFragment
import com.wjp.justforfun.service.reposity.JustRepository
import com.wjp.justforfun.util.setImageFromUrl
import com.wjp.justforfun.util.showToast
import com.wjp.zhihupurify.service.model.news.NewsDetails
import kotlinx.android.synthetic.main.frag_news_detail.*
import org.kodein.di.Kodein
import java.io.File

class NewsDetailFragment : BaseFragment() {

    override val layoutId: Int= R.layout.frag_news_detail
    override val kodein: Kodein= Kodein.lazy {
        extend(parentKodein)
    }

    private val args: NewsDetailFragmentArgs by navArgs()

    lateinit var photoSeeListener: PhotoSeeListener

    @Suppress("UNCHECKED_CAST")
    private val viewModel: NewsDetailViewModel by viewModels {
        object : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return NewsDetailViewModel(
                    JustRepository(),
                    args.newsId
                )
                    .apply {
                } as T
            }
        }
    }



    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        web_view.apply {
            settings.apply {
                javaScriptEnabled = true
            }
            photoSeeListener =
                PhotoSeeListener(context, null)
            addJavascriptInterface(photoSeeListener, "PhotoSee")
        }


        viewModel.newsDetail.observe(viewLifecycleOwner) {
            photoSeeListener.setupImageUrls(it.images?.map { it.imgSrc }?.toTypedArray())
            web_view.loadData(processData(it), "text/html", "UTF-8")

        }
    }

    //这样替换有多少张图片，就要遍历多少次，而且还要重复创建该字符串,
    //todo
    private fun processData(detail: NewsDetails): String {
        var temp = detail.content
        detail.images?.forEach {
            temp = temp?.replace(
                it.position,
                """<img src="${it.imgSrc}" width="100%" height="auto" onClick="window.PhotoSee.open(this.src)" >"""
            )
        }

//        temp = Jsoup.parse(temp).apply {
//            getElementsByTag("img").forEach {
//                it.attr("width", "100%")
//                    .attr("height", "auto")
//
//
//            }
//        }.toString()
        return temp.toString().also {
            Log.d("wolf", "result:\n $it")
        }
    }


}


class PhotoSeeListener(private val context: Context, private var imageUrls: Array<String>?) {
    @JavascriptInterface
    fun open(imageUrl: String?) {
        val index = imageUrls?.indexOf(imageUrl)
        Log.d("wolf", "open :$imageUrl")
        context.showToast("index:$index")
        XPopup.Builder(context).asImageViewer(null,index!!, imageUrls!!.toMutableList<Any>(),{ popupView, position ->  },object: XPopupImageLoader{
            override fun loadImage(position: Int, uri: Any, imageView: ImageView) {
                Glide.with(context).load(uri.toString()).apply(RequestOptions().override(Target.SIZE_ORIGINAL)).into(imageView)
            }

            override fun getImageFile(context: Context, uri: Any): File {
                return Glide.with(context).downloadOnly().load(uri).submit().get()
            }

        } ).show()
    }

    fun setupImageUrls(imageUrls: Array<String>?) {
        this.imageUrls = imageUrls
    }
}
