package com.wjp.justforfun.base.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment : InjectionFragment(){

    protected var mRootView: View? =null

    abstract val layoutId:Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView=inflater.inflate(layoutId,container,false)
        return mRootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mRootView=null
    }

}