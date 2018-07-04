package com.elsen.jetpack.base.data

import io.reactivex.Observable

/**
 * Created by elsennovraditya on 28/06/18
 */
interface ReactiveStore<T> {

    fun getSingle(): Observable<T>
    fun storeSingle(t: T)
    fun getAll(): Observable<List<T>>
    fun storeAll(t: List<T>)
    fun replaceAll(t: List<T>)

}