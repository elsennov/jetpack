package com.elsen.jetpack.userdetail.presentation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Intent

/**
 * Created by elsennovraditya on 21/06/18
 */
class UserViewModel(private val user: MutableLiveData<UserDisplayable>) : ViewModel() {

    fun setUser(user: UserDisplayable) {
        this.user.value = user
    }

    fun getUser(intent: Intent): MutableLiveData<UserDisplayable> {
        if (user.value == null) {
            loadUser(intent)
        }
        return user
    }

    private fun loadUser(intent: Intent) {
        val user = intent.getSerializableExtra(UserDetailActivity.USER) as? UserDisplayable
            ?: UserDisplayable.empty
        setUser(user)
    }

}