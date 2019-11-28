package com.wjp.justforfun.ui.login

import android.os.Bundle
import com.wjp.justforfun.R
import com.wjp.justforfun.base.view.activity.BaseActivity

class LoginActivity() :BaseActivity() {

    override val layoutId= R.layout.act_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFragment()
    }

    private fun initFragment() {
        with(supportFragmentManager) {
            findFragmentByTag(TAG)?:beginTransaction()
                .add(R.id.flContainer,LoginFrag(), TAG)
                .commit()
        }
    }

    companion object{
        private const val TAG="login_fragment_tag"
    }

}