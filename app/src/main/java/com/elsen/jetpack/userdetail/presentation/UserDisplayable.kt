package com.elsen.jetpack.userdetail.presentation

import java.io.Serializable

/**
 * Created by elsennovraditya on 21/06/18
 */
data class UserDisplayable(val name: String?,
                           val company: String?,
                           val position: String?) : Serializable {

    companion object {
        val empty = UserDisplayable(null, null, null)
    }

}