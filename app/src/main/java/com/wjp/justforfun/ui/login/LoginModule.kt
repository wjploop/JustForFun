package com.wjp.justforfun.ui.login

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton
import kotlin.math.sin

const val login_module_tag="login_module_tag"

val loginModule= Kodein.Module(login_module_tag){

    bind<LoginViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        ViewModelProviders
            .of(context.activity!!,LoginViewModelFactory(instance()))
            .get(LoginViewModel::class.java)
    }

    bind<LoginRepository>() with singleton {
        LoginRepository(instance(),instance())
    }

    bind<LoginLocalDataSource>() with singleton {
        LoginLocalDataSource(instance())
    }
    bind<LoginRemoteDataSource>() with singleton{
        LoginRemoteDataSource(instance())
    }
}