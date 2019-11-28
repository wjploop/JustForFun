package com.wjp.justforfun.base.repository

open class BaseRepositoryBoth<Local:ILocalDataSource,Remote:IRemoteDataSource>(
    val localDataSource:Local,
    val remoteDataSource:Remote
):IRepository

open class BaseRepositoryLocal<Local:ILocalDataSource>(
    val localDataSource: Local
):IRepository

open class BaseRepositoryRemote<Remote:IRemoteDataSource>(
    val remoteDataSource: Remote
):IRepository