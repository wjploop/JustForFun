package com.wjp.justforfun

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.wjp.justforfun.di.appModule
import com.wjp.justforfun.di.globalRepositoryModule
import com.wjp.justforfun.di.httpClientModule
import com.wjp.justforfun.di.serviceModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidCoreModule
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.header.ClassicsHeader



class MyApp() : Application(),KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        bind<Context>() with singleton { this@MyApp }
        import(androidCoreModule(this@MyApp))
        import(androidXModule(this@MyApp))

        importAll(serviceModule, httpClientModule, globalRepositoryModule)
    }


    override fun onCreate() {
        super.onCreate()
        INSTANCE=this
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MyApp)
            modules(
                appModule
            )
        }

        MultiDex.install(this);

        initSmartRefreshLayout()

    }

    private fun initSmartRefreshLayout() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(android.R.color.darker_gray, android.R.color.white)//全局设置主题颜色
            ClassicsHeader(context)//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            //指定为经典Footer，默认是 BallPulseFooter
            ClassicsFooter(context).setDrawableSize(20f)
        }
    }

    companion object {
        lateinit var INSTANCE:MyApp
    }


}