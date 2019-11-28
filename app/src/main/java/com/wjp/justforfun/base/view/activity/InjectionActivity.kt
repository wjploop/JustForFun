package com.wjp.justforfun.base.view.activity

import com.wjp.justforfun.base.view.IView
import org.kodein.di.Copy
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein

open class InjectionActivity:AutoDisposeActivity(),KodeinAware,IView {


    protected val parentKodein by closestKodein()

    override val kodein: Kodein by retainedKodein {
        extend(parentKodein,true,copy = Copy.All)
    }
}