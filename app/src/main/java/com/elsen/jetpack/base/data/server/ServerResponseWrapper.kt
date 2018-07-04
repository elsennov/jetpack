package com.elsen.jetpack.base.data.server

/**
 * Created by elsennovraditya on 12/18/16.
 */

class ServerResponseWrapper<out T>(val status: String?,
                                   val message: String?,
                                   val data: T?)
