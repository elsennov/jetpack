package com.elsen.jetpack.base

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import io.reactivex.Observable

/**
 * Convert [LiveData] to [Observable].
 */
fun <T> LiveData<T>.toObservable(lifecycleOwner: LifecycleOwner): Observable<T> {
    return Observable.fromPublisher(
        LiveDataReactiveStreams.toPublisher(lifecycleOwner, this)
    )
}