package com.elsen.jetpack.userdetail.data

import io.reactivex.functions.Function

/**
 * Created by elsennovraditya on 28/06/18
 */
class UserMapper : Function<UserRaw, User> {

    override fun apply(userRaw: UserRaw): User {
        return User(
            userRaw.name,
            userRaw.company,
            userRaw.position
        )
    }

}