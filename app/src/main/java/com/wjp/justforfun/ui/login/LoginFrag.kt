package com.wjp.justforfun.ui.login

import android.os.Bundle
import android.view.View
import com.uber.autodispose.autoDispose
import com.wjp.justforfun.R
import com.wjp.justforfun.base.ext.reactivex.clickThrottleFirst
import com.wjp.justforfun.base.ext.toast
import com.wjp.justforfun.base.view.fragment.BaseFragment
import com.wjp.justforfun.http.Error
import com.wjp.justforfun.util.RxSchedulers
import kotlinx.android.synthetic.main.frag_login.*
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import retrofit2.HttpException

class LoginFrag:BaseFragment() {
    override val layoutId: Int = R.layout.frag_login

    override val kodein: Kodein by Kodein.lazy {
        extend(parentKodein)
        import(loginModule)
    }

    private val viewModel: LoginViewModel by instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binds()
    }

    private fun binds() {
        btn_login.clickThrottleFirst()
            .autoDispose(scopeProvider)
            .subscribe{
                viewModel.login(tv_username.text.trim().toString(),tv_password.text.trim().toString())
            }

        viewModel.observeViewState().observeOn(RxSchedulers.ui)
            .autoDispose(scopeProvider)
            .subscribe{state->
                if (state.throwable != null) {
                    when (state.throwable) {
                        is Error.EmptyInputError -> "username or password cannot be null"
                        is HttpException ->
                            when (state.throwable.code()) {
                                401->"username or password fail"
                                else -> "network error"
                            }
                        else ->"failure"
                    }.let {
                        toast { it }
                    }
                }

                progressBar.visibility =if(state.isLoading) View.VISIBLE else View.GONE

                if (state.loginInfo != null) {
                    toast{state.loginInfo.name}
                }
            }
    }


}