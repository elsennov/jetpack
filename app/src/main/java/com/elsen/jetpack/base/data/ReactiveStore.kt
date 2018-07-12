package com.elsen.jetpack.base.data

import android.arch.paging.DataSource
import io.reactivex.Observable

/**
 * Created by elsennovraditya on 28/06/18
 */
interface ReactiveStore<T> {

    fun getAll(): Observable<List<T>>
    fun getPaged(): DataSource.Factory<Int, T>
    fun getSingle(): Observable<T>
    fun storeAll(t: List<T>)
    fun storeSingle(t: T)
    fun replaceAll(t: List<T>)
    fun replaceSingle(t: T)

}