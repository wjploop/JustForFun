package com.wjp.justforfun.service.model

import android.content.Context
import com.kaopiz.kprogresshud.KProgressHUD
import com.wjp.justforfun.util.showToast
import com.wjp.zhihupurify.service.model.BaseModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

abstract class BaseObserver<Data>(val context:Context,val showProgress:Boolean=false) :Observer<BaseModel<Data>> {

    private val code_success=1  //根据后台来确定正确的返回码

    var progressHub:KProgressHUD?=null

    init {
        progressHub= KProgressHUD.create(context)
            .setStyle(KProgressHUD.Style.ANNULAR_DETERMINATE)
    }

    abstract fun onSuccess(data:Data)

    //在这里处理一些错误码，比如token失效重新登录
    open fun <Data> onCodeError(baseModel: BaseModel<Data>){
        when (baseModel.code) {
            0->context.showToast(baseModel.msg) //这里仅显示后台返回的错误信息
        }
    }
    open fun onFailure(e:Throwable,isNetworkError:Boolean){
        if (isNetworkError) {
            context.showToast("Network Error")
        }else{
            context.showToast("Unknown Error")
        }
    }


    override fun onComplete() {

    }

    override fun onSubscribe(d: Disposable) {
        if (showProgress) {
            progressHub?.apply {
                show()
            }
        }
    }

    override fun onNext(t: BaseModel<Data>) {
        if (showProgress) {
            progressHub?.apply {
                dismiss()
            }
        }
        if (t.code == code_success) {
            onSuccess(t.data!!)
        }else{
            onCodeError(t)
        }

    }

    override fun onError(e: Throwable) {
        if (showProgress) {
            progressHub?.apply {
                dismiss()
            }
        }

        val isNetworkError= e is ConnectException || e is TimeoutException ||  e is UnknownHostException

        onFailure(e,isNetworkError)
    }
}