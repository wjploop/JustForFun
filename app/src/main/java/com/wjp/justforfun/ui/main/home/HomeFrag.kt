package com.wjp.justforfun.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.wjp.justforfun.R
import com.wjp.justforfun.base.view.fragment.BaseFragment
import kotlinx.android.synthetic.main.frag_home.*
import org.kodein.di.Kodein

class HomeFrag:BaseFragment() {
    override val layoutId = R.layout.frag_home

    override val kodein: Kodein = Kodein.lazy {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv.text=arguments?.getInt("pos").toString()
    }

    companion object {
        fun newInstance(pos:Int):Fragment{
            return HomeFrag().apply {
                arguments= Bundle().apply {
                    putInt("pos",pos)
                }
            }
        }
    }

}