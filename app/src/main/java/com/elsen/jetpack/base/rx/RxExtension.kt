package com.elsen.jetpack.base.rx

import com.elsen.jetpack.base.data.server.*
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Transform observable [Throwable] to the specific [Throwable] based on the error type. This will
 * help to determine the correct error handling in presentation layer.
 */
fun <T> Observable<T>.toSpecificErrorIfAny(): Observable<T> {
    return onErrorResumeNext(
        io.reactivex.functions.Function {
            when {
                it.isServerRequestErrorNoInternet() -> Observable.error<T>(NoInternetError(it))
                it.isServerRequestErrorNetwork() -> Observable.error<T>(NetworkError(it))
                else -> Observable.error<T>(UnknownError(it))
            }
        }
    )
}

/**
 * Transform single [Throwable] to the specific [Throwable] based on the error type. This will
 * help to determine the correct error handling in presentation layer.
 */
fun <T> Single<T>.toSpecificErrorIfAny(): Single<T> {
    return onErrorResumeNext {
        when {
            it.isServerRequestErrorNoInternet() -> Single.error<T>(NoInternetError(it))
            it.isServerRequestErrorNetwork() -> Single.error<T>(NetworkError(it))
            else -> Single.error<T>(UnknownError(it))
        }
    }
}

/**
 * Transform observable of [T] into observable error of [ResultEmptyError] if the item is
 * considered empty by the [emptyCheckingFunction].
 */
fun <T> Observable<T>.toResultEmptyErrorIfEmpty(emptyCheckingFunction: (obj: T) -> Boolean): Observable<T> {
    return flatMap {
        if (emptyCheckingFunction(it)) {
            Observable.error<T>(ResultEmptyError(Throwable()))
        } else {
            Observable.just<T>(it)
        }
    }
}

/**
 * Transform single of [T] into single error of [ResultEmptyError] if the item is
 * considered empty by the [emptyCheckingFunction].
 */
fun <T> Single<T>.toResultEmptyErrorIfEmpty(emptyCheckingFunction: (obj: T) -> Boolean): Single<T> {
    return flatMap {
        if (emptyCheckingFunction(it)) {
            Single.error<T>(ResultEmptyError(Throwable()))
        } else {
            Single.just<T>(it)
        }
    }
}