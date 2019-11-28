package com.wjp.justforfun.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uber.autodispose.autoDispose
import com.wjp.justforfun.base.ext.reactivex.copyMap
import com.wjp.justforfun.base.ext.toast
import com.wjp.justforfun.base.viewmodel.BaseViewModel
import com.wjp.justforfun.base.viewstate.Result
import com.wjp.justforfun.http.Error
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

class LoginViewModel(
    private val repo: LoginRepository
):BaseViewModel() {

    private val  mViewStateSubject:BehaviorSubject<LoginViewState> =
        BehaviorSubject.createDefault(LoginViewState.initial())

    fun observeViewState(): Observable<LoginViewState> {
        return mViewStateSubject.distinctUntilChanged()
    }

    fun login(username: String?, password: String?) {
        when (username.isNullOrBlank() || password.isNullOrBlank()) {
            true-> mViewStateSubject.copyMap {
                it.copy(
                    isLoading = false,
                    throwable =Error.EmptyInputError,
                    autoLoginEvent = null,
                    loginInfo = null
                )
            }

            false-> repo
                .login(username,password)
                .map {either->
                    either.fold(
                        {
                            Result.failure<UserInfo>(it)
                        },
                        {
                            Result.success(it)
                        }
                    )
                }
                .startWith(Result.loading())
                .startWith(Result.idle())
                .onErrorReturn { Result.failure(it) }
                .autoDispose(this)
                .subscribe {state->
                    when (state) {
                        is Result.Loading -> mViewStateSubject.copyMap {
                            it.copy(true, null, loginInfo = null)
                        }
                        is Result.Idle -> mViewStateSubject.copyMap {
                            it.copy(false, null, loginInfo = null)
                        }
                        is Result.Failure -> mViewStateSubject.copyMap {
                            it.copy(isLoading = false, throwable = state.erro, loginInfo = null)
                        }
                        is Result.Success -> mViewStateSubject.copyMap {
                            it.copy(isLoading = false, throwable = null, loginInfo = state.data)
                        }
                    }
                }
//                .subscribe(object:Subscriber<Result<UserInfo>>{
//                    override fun onError(t: Throwable?) {
//                        toast{ "onError"}
//                        t?.printStackTrace()
//                    }
//
//                    override fun onComplete() {
//                        toast{ "onComplete"}
//                    }
//
//                    override fun onSubscribe(s: Subscription?) {
//                        toast{ "onSubscribe"}
//                    }
//
//                    override fun onNext(t: Result<UserInfo>) {
//                        toast{ "onNext"}
//                        val state = t
//                        when (state) {
//                            is Result.Loading -> mViewStateSubject.copyMap {
//                                it.copy(true, null, loginInfo = null)
//                            }
//                            is Result.Idle -> mViewStateSubject.copyMap {
//                                it.copy(false, null, loginInfo = null)
//                            }
//                            is Result.Failure -> mViewStateSubject.copyMap {
//                                it.copy(isLoading = false, throwable = state.erro, loginInfo = null)
//                            }
//                            is Result.Success -> mViewStateSubject.copyMap {
//                                it.copy(isLoading = false, throwable = null, loginInfo = state.data)
//                            }
//                        }
//                    }
//
//
//
//                })




        }
    }



}

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(
    val repo:LoginRepository
):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(repo) as T
    }

}