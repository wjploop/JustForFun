package com.wjp.justforfun.ui.login

import arrow.core.Either
import arrow.core.extensions.id.applicative.just
import com.wjp.justforfun.base.repository.BaseRepositoryBoth
import com.wjp.justforfun.base.repository.ILocalDataSource
import com.wjp.justforfun.base.repository.IRemoteDataSource
import com.wjp.justforfun.http.bean.LoginRequestModel
import com.wjp.justforfun.http.service.UserManager
import com.wjp.justforfun.http.service.UserService
import com.wjp.justforfun.service.reposity.UserInfoRepository
import com.wjp.justforfun.util.RxSchedulers
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.internal.operators.completable.CompletableCache

class LoginRepository(
        localDataSource: LoginLocalDataSource,
        remoteDataSource: LoginRemoteDataSource
):BaseRepositoryBoth<LoginLocalDataSource,LoginRemoteDataSource>(localDataSource,remoteDataSource) {

    fun login(username:String,passwordd: String):Flowable<Either<Error,UserInfo>> =
        localDataSource.savePrefsUser(username,passwordd)
            .andThen(remoteDataSource.login())
            .doOnNext{either->
                either.fold({
                    localDataSource.clearPrefsUser()
                    Unit
                },{
                    UserManager.userInfo=it
                }
                )
            }.doOnError {
                localDataSource.clearPrefsUser()
            }

    //是否自动登录
    fun fetchAutoLogin()=
        localDataSource.fetchAutoLogin()
}

class LoginRemoteDataSource(
    private val userService: UserService
):IRemoteDataSource{

    fun login():Flowable<Either<Error,UserInfo>>{

        val authObservable=userService.authorizations(LoginRequestModel.generate())

        val ownerInfoObservable=userService.fetchUserOwner()

        return authObservable
            .flatMap {
                ownerInfoObservable
            }.subscribeOn(RxSchedulers.io)
            .map {
                Either.right(it)
            }

    }
}


class LoginLocalDataSource(
    private val userRepo: UserInfoRepository
):ILocalDataSource{

    fun savePrefsUser(username: String, password: String):Completable {
        return CompletableCache.fromAction {
            userRepo.username=username
            userRepo.password=password
        }
    }

    fun clearPrefsUser()=
        Completable.fromAction {
            userRepo.username=""
            userRepo.password=""
        }

    fun fetchAutoLogin() :Flowable<AutoLoginEvent>{
        val username=userRepo.username
        val password=userRepo.password
        val isAutoLogin=userRepo.isAutoLogin
        return Flowable.just(
            when (username.isNotEmpty()&&password.isNotEmpty()&&isAutoLogin) {
                true-> AutoLoginEvent(true,username,password)
                false->AutoLoginEvent(false,"","")
            }
        )
    }

}

data class AutoLoginEvent(
    val autoLogin:Boolean,
    val username:String,
    val passwordd:String
)