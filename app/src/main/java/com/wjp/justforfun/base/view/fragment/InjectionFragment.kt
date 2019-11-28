package com.wjp.justforfun.base.view.fragment

import androidx.fragment.app.Fragment
import com.wjp.justforfun.base.view.IView
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinContext
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.kcontext

abstract class InjectionFragment: AutoDisposeFragment(),KodeinAware,IView{


    protected val parentKodein by closestKodein()

    override val kodeinContext = kcontext<Fragment>(this)
}