package com.elsen.jetpack.base.data.server

/**
 * Created by elsennovraditya on 12/13/16.
 */

open class ServerRequestError(protected val throwable: Throwable) : Throwable(throwable)
