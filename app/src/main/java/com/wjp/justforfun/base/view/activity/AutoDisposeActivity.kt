package com.wjp.justforfun.base.view.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

open class AutoDisposeActivity:AppCompatActivity() {

    protected val scopeProvider:AndroidLifecycleScopeProvider by lazy {
        AndroidLifecycleScopeProvider.from(this,Lifecycle.Event.ON_DESTROY)
    }
}