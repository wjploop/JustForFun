package com.wjp.justforfun.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.wjp.justforfun.R
import com.wjp.justforfun.base.view.fragment.BaseFragment
import com.wjp.justforfun.ui.main.gallery.GalleryFrag
import com.wjp.justforfun.ui.main.home.HomeFrag
import com.wjp.justforfun.ui.main.news.NewsContainerFrag
import com.wjp.justforfun.ui.main.news.list.NewsListFragment
import kotlinx.android.synthetic.main.frag_main.*
import org.kodein.di.Kodein

class MainFragment() :BaseFragment() {

    override val layoutId: Int= R.layout.frag_main

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(view_pager){

            val fragmentList= listOf<Fragment>(

                NewsContainerFrag(),
                GalleryFrag(),
                HomeFrag.newInstance(2)
            )

            adapter=object:FragmentStateAdapter(childFragmentManager,lifecycle){
                override fun getItemCount(): Int {
                    return fragmentList.size
                }

                override fun createFragment(position: Int): Fragment {
                    return fragmentList[position]
                }
            }
            this.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    bottom_nav.menu.getItem(position).isChecked=true
                }
            })
            isUserInputEnabled=false
        }
        with(bottom_nav){
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.nav_home-> view_pager.currentItem=0
                    R.id.nav_repos-> view_pager.currentItem=1
                    R.id.nav_profile-> view_pager.currentItem=2
                }
                true
            }
        }
    }


}
